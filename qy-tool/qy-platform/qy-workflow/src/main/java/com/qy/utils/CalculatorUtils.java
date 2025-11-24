package com.qy.utils;//package com.qy.utils;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static com.twelvemonkeys.lang.StringUtil.isNumber;
//
///**
// * 加减乘除表达式求值算法
// *
// * @version 1.0
// * @date 2018年7月7日
// */
//public class CalculatorUtils {
//
//    // 表达式字符合法性校验正则模式，静态常量化可以降低每次使用都要编译地消耗
//    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[0-9\\.+-/*()= ]+");
//
//    // 运算符优先级map
//    private static final Map<String, Integer> OPT_PRIORITY_MAP = new HashMap<String, Integer>() {
//        private static final long serialVersionUID = 6968472606692771458L;
//
//        {
//            put("(", 0);
//            put("+", 2);
//            put("-", 2);
//            put("*", 3);
//            put("/", 3);
//            put(")", 7);
//            put("=", 20);
//        }
//    };
//
//
//    /**
//     * 输入加减乘除表达式字符串，返回计算结果
//     *
//     * @param expression 表达式字符串
//     * @return 返回计算结果
//     */
//    public static double executeExpression(String expression) {
//        // 非空校验
//        if (null == expression || "".equals(expression.trim())) {
//            throw new IllegalArgumentException("表达式不能为空！");
//        }
//
//        // 表达式字符合法性校验
//        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
//        if (!matcher.matches()) {
//            throw new IllegalArgumentException("表达式含有非法字符！");
//        }
//
//        Stack<String> optStack = new Stack<>(); // 运算符栈
//        Stack<BigDecimal> numStack = new Stack<>(); // 数值栈，数值以BigDecimal存储计算，避免精度计算问题
//        StringBuilder curNumBuilder = new StringBuilder(16); // 当前正在读取中的数值字符追加器
//
//        // 逐个读取字符，并根据运算符判断参与何种计算
//        for (int i = 0; i < expression.length(); i++) {
//            char c = expression.charAt(i);
//            if (c != ' ') { // 空白字符直接丢弃掉
//                if ((c >= '0' && c <= '9') || c == '.') {
//                    curNumBuilder.append(c); // 持续读取一个数值的各个字符
//                } else {
//                    if (curNumBuilder.length() > 0) {
//                        // 如果追加器有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
//                        numStack.push(new BigDecimal(curNumBuilder.toString()));
//                        curNumBuilder.delete(0, curNumBuilder.length());
//                    }
//
//                    String curOpt = String.valueOf(c);
//                    if (optStack.empty()) {
//                        // 运算符栈栈顶为空则直接入栈
//                        optStack.push(curOpt);
//                    } else {
//                        if (curOpt.equals("(")) {
//                            // 当前运算符为左括号，直接入运算符栈
//                            optStack.push(curOpt);
//                        } else if (curOpt.equals(")")) {
//                            // 当前运算符为右括号，触发括号内的字表达式进行计算
//                            directCalc(optStack, numStack, true);
//                        } else if (curOpt.equals("=")) {
//                            // 当前运算符为等号，触发整个表达式剩余计算，并返回总的计算结果
//                            directCalc(optStack, numStack, false);
//                            return numStack.pop().doubleValue();
//                        } else {
//                            // 当前运算符为加减乘除之一，要与栈顶运算符比较，判断是否要进行一次二元计算
//                            compareAndCalc(optStack, numStack, curOpt);
//                        }
//                    }
//                }
//            }
//        }
//
//        // 表达式不是以等号结尾的场景
//        if (curNumBuilder.length() > 0) {
//            // 如果追加器有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
//            numStack.push(new BigDecimal(curNumBuilder.toString()));
//        }
//        directCalc(optStack, numStack, false);
//        return numStack.pop().doubleValue();
//    }
//
//    /**
//     * 拿当前运算符和栈顶运算符对比，如果栈顶运算符优先级高于或同级于当前运算符，
//     * 则执行一次二元运算（递归比较并计算），否则当前运算符入栈
//     *
//     * @param optStack 运算符栈
//     * @param numStack 数值栈
//     * @param curOpt   当前运算符
//     */
//    public static void compareAndCalc(Stack<String> optStack, Stack<BigDecimal> numStack,
//                                      String curOpt) {
//        // 比较当前运算符和栈顶运算符的优先级
//        String peekOpt = optStack.peek();
//        int priority = getPriority(peekOpt, curOpt);
//        if (priority == -1 || priority == 0) {
//            // 栈顶运算符优先级大或同级，触发一次二元运算
//            String opt = optStack.pop(); // 当前参与计算运算符
//            BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
//            BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
//            BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);
//
//            // 计算结果当做操作数入栈
//            numStack.push(bigDecimal);
//
//            // 运算完栈顶还有运算符，则还需要再次触发一次比较判断是否需要再次二元计算
//            if (optStack.empty()) {
//                optStack.push(curOpt);
//            } else {
//                compareAndCalc(optStack, numStack, curOpt);
//            }
//        } else {
//            // 当前运算符优先级高，则直接入栈
//            optStack.push(curOpt);
//        }
//    }
//
//    /**
//     * 遇到右括号和等号执行的连续计算操作（递归计算）
//     *
//     * @param optStack  运算符栈
//     * @param numStack  数值栈
//     * @param isBracket true表示为括号类型计算
//     */
//    public static void directCalc(Stack<String> optStack, Stack<BigDecimal> numStack,
//                                  boolean isBracket) {
//        String opt = optStack.pop(); // 当前参与计算运算符
//        BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
//        BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
//        BigDecimal bigDecimal = floatingPointCalc(opt, num1, num2);
//
//        // 计算结果当做操作数入栈
//        numStack.push(bigDecimal);
//
//        if (isBracket) {
//            if ("(".equals(optStack.peek())) {
//                // 括号类型则遇左括号停止计算，同时将左括号从栈中移除
//                optStack.pop();
//            } else {
//                directCalc(optStack, numStack, isBracket);
//            }
//        } else {
//            if (!optStack.empty()) {
//                // 等号类型只要栈中还有运算符就继续计算
//                directCalc(optStack, numStack, isBracket);
//            }
//        }
//    }
//
//    /**
//     * 不丢失精度的二元运算，支持高精度计算
//     *
//     * @param opt
//     * @param bigDecimal1
//     * @param bigDecimal2
//     * @return
//     */
//    public static BigDecimal floatingPointCalc(String opt, BigDecimal bigDecimal1,
//                                               BigDecimal bigDecimal2) {
//        BigDecimal resultBigDecimal = new BigDecimal(0);
//        switch (opt) {
//            case "+":
//                resultBigDecimal = bigDecimal1.add(bigDecimal2);
//                break;
//            case "-":
//                resultBigDecimal = bigDecimal1.subtract(bigDecimal2);
//                break;
//            case "*":
//                resultBigDecimal = bigDecimal1.multiply(bigDecimal2);
//                break;
//            case "/":
//                resultBigDecimal = bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_DOWN); // 注意此处用法
//                break;
//            default:
//                break;
//        }
//        return resultBigDecimal;
//    }
//
//    /**
//     * priority = 0  表示两个运算符同级别
//     * priority = 1  第二个运算符级别高，负数则相反
//     *
//     * @param opt1
//     * @param opt2
//     * @return
//     */
//    public static int getPriority(String opt1, String opt2) {
//        int priority = OPT_PRIORITY_MAP.get(opt2) - OPT_PRIORITY_MAP.get(opt1);
//        return priority;
//    }
//
//    /**
//     * 浮点数相等比较函数
//     *
//     * @param value1
//     * @param value2
//     * @return
//     */
//    private static boolean isDoubleEquals(double value1, double value2) {
//        System.out.println("正确结果=" + value1 + ", 实际计算结果=" + value2);
//        return Math.abs(value1 - value2) <= 0.0001;
//    }
//
//    /*public static void main(String[] args) {
//        // 几个测试数据
//        System.out.println(isDoubleEquals(-39.5, executeExpression(" 2 + 3/2 * 3 - 4 *(2 + 5 -2*4/2+9) + 3 + (2*1)-3= ")));
//        System.out.println(isDoubleEquals(60.3666, executeExpression("9*2+1/3*2-4+(3*2/5+3+3*6)+3*5-1+3+(4+5*1/2)=")));
//        System.out.println(isDoubleEquals(372.8, executeExpression(" 9.2 *(20-1)-1+199 = ")));
//        System.out.println(isDoubleEquals(372.8, executeExpression(" 9.2 *(20-1)-1+199  ")));
//        System.out.println(isDoubleEquals(372.8, executeExpression(" 9.2 *(20-1)-1+199")));
//        System.out.println(isDoubleEquals(-29, executeExpression(" 9 *(20-1)-(1+199) ")));
//        System.out.println(isDoubleEquals(1.0E24, executeExpression("1000000000000*1000000000000 = ")));
//    }*/
//
//
//
//
//    /**
//     * 检查表达式是否合法，合法返回true
//     * @param expression
//     * @return
//     */
//    private boolean checkExpression(String expression) {
//        //去除空格
//        expression = expression.replaceAll(" ","");
//
//        Set<Character> operate_set = new HashSet<>();
//        operate_set.add('-');
//        operate_set.add('+');
//        operate_set.add('*');
//        operate_set.add('/');
//
//        //拆分字符串
//        char[] arr = expression.toCharArray();
//        int len = arr.length;
//
//        //前后括号计数，用来判断括号是否合法
//        int checkNum=0;
//
//        //数字集合
//        List<Character> digit_list = new ArrayList<>();
//        StringBuffer stringBuffer = new StringBuffer();
//        //循环判断
//        for (int i = 0; i < len; i++) {
//            if(Character.isDigit(arr[i])|| arr[i] == '.'){ //数字和小数点判断
//                //把数字和小数点加入到集合中，为了下一步判断数字是否合法
//                digit_list.add(arr[i]);
//            }else { //非数字和小数点
//                //如果集合中有值，取出来判断这个数字整体是否合法
//                if(digit_list.size()>0) {
//                    //判断字符串是否合法
//                    boolean result = getNumberStringFromList(digit_list);
//                    if(result){
//                        //如果合法，清空集合，为了判断下一个
//                        digit_list.clear();
//                    }else{
//                        //不合法，返回false
//                        return false;
//                    }
//                }
//
//                if (arr[i] == '+' || arr[i] == '*' || arr[i] == '/') {
//                    //判断规则(1.不能位于首位 2.不能位于末尾 3.后面不能有其他运算符 4.后面不能有后括号)
//                    if (i == 0 || i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
//                        System.out.println("error type : '+' or '*' or '/' ->"+ arr[i]);
//                        return false;
//                    }
//                } else if (arr[i] == '-') {
//                    //减号判断规则(1.不能位于末尾 2.后面不能有其他运算符 3.后面不能有后括号)
//                    if (i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
//                        System.out.println("error type : '-' ->"+ arr[i]);
//                        return false;
//                    }
//
//                } else if (arr[i] == '(') {
//                    checkNum++;
//                    //判断规则(1.不能位于末尾 2.后面不能有+，*，/运算符和后括号 3.前面不能为数字)
//                    if (i == (len - 1) || arr[i + 1] == '+' || arr[i + 1] == '*' || arr[i + 1] == '/' || arr[i + 1] == ')'||(i != 0 && Character.isDigit(arr[i-1]))) {
//                        System.out.println("error type : '(' ->"+ arr[i]);
//                        return false;
//                    }
//                } else if (arr[i] == ')') {
//                    checkNum--;
//                    //判定规则(1.不能位于首位 2.后面不能是前括号 3.括号计数不能小于0，小于0说明前面少了前括号)
//                    if (i == 0 || (i < (len - 1) && arr[i + 1] == '(') || checkNum < 0) {
//                        System.out.println("error type : ')' ->"+ arr[i]);
//                        return false;
//                    }
//                }else{
//                    //非数字和运算符
//                    System.out.println("error type : not number and operator ->"+ arr[i]);
//                    return false;
//                }
//            }
//        }
//
//        //如果集合中有值，取出来判断这个数字整体是否合法
//        if(digit_list.size()>0) {
//            //判断字符串是否合法
//            boolean result = getNumberStringFromList(digit_list);
//            if(result){
//                //如果合法，清空集合，为了判断下一个
//                digit_list.clear();
//            }else{
//                //不合法，返回false
//                return false;
//            }
//        }
//
//        //不为0,说明括号不对等，可能多前括号
//        if(checkNum!=0){
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 把集合中的字符，拼接成字符串，并校验字符串是否是数值
//     * @param list
//     * @return
//     */
//    private boolean getNumberStringFromList(List<Character> list){
//        //如果集合中有值，取出来判断这个数字整体是否合法
//        if(list != null){
//            StringBuffer stringBuffer = new StringBuffer();
//            for(Character character : list){
//                stringBuffer.append(character);
//            }
//            //正则判断数字是否合法
//            boolean result = isNumber(stringBuffer.toString());
//
//            if(!result){
//                System.out.println("error type: digit ->"+stringBuffer.toString());
//            }
//            return result;
//        }
//        return false;
//    }
//
//    private CalculatorUtils() {
//    }
//
//    ;
//
//}