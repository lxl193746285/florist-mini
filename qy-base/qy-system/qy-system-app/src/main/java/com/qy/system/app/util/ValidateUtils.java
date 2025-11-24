package com.qy.system.app.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证辅助类
 */
public enum ValidateUtils {

    /**
     * 用户名
     */
    USERNAME("用户名", 1, "[a-zA-Z]\\w", "{5,20}"),
    /**
     * 密码
     */
    PASSWORD("密码", 2, ".", "{8,}"),

    /**
     * 手机号
     */
    PHONE("手机号", 3, "1[1-9]{1}\\d{9}", null),
    /**
     * 邮箱
     */
    EMAIL("邮箱", 4, "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}", null),
    /**
     * 汉字
     */
    CHINESE("汉字", 5, "[\\u4e00-\\u9fa5]{0,}", null),
    /**
     * 身份证
     */
    ID_CARD("身份证", 6, "(\\d{18})|(\\d{15})", null),
    /**
     * URL
     */
    URL("URL", 7, "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", null),
    /**
     * ip地址
     */
    IP_ADDRESS("ip地址", 8, "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", null),
    /**
     * 6位短信验证码
     */
    SMS_LEN6("6位短信验证码", 9, "[0-9]", "{6}"),
    /**
     * 5位短信验证码
     */
    SMS_LEN5("5位短信验证码", 10, "[0-9]", "{5}"),
    /**
     * 文本内容
     */
    CONTENT("内容", 11, "[\\u4e00-\\u9fa5_a-zA-Z0-9_]", null),
    /**
     * 正则表达式数字区间
     */
    REG_EX_SCOPE("正则表达式数字区间", 12, "(\\{[0-9]{1,}\\})|(\\{[0-9]{1,},\\})|(\\{[0-9]{1,},[0-9]{1,}\\})", null),
    /**
     * 日期格式
     */
    DATE("日期", 13, "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)", null),
    /**
     * 日期时间格式
     */
    DATE_TIME("日期时间", 14, "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])", null),
    /**
     * 时间格式
     */
    TIME("时间", 15, "[0-9]{2}:[0-9]{2}", null),
    /**
     * 主键id
     */
    ID("id", 16, "[0-9]{11}", null),
    /**
     * uuid
     */
    UUID("uuid", 17, "[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}", null),
    ;

    // 成员变量
    /**
     * 说明
     */
    private String name;
    /**
     * id
     */
    private int id;
    /**
     * 正则
     */
    private String regEx;
    /**
     * 正则长度
     */
    private String regExLen;

    /**
     * 构造方法
     *
     * @param name
     * @param id
     */
    ValidateUtils(String name, int id, String regEx, String regExLen) {
        this.name = name;
        this.id = id;
        this.regEx = regEx;
        this.regExLen = regExLen;
    }

    public String getName() {
        return name;
    }

    public Byte getByte() {
        return (byte) id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public String getRegExLen() {
        return regExLen;
    }

    public void setRegExLen(String regExLen) {
        this.regExLen = regExLen;
    }

    /**
     * 根据值获取名称
     *
     * @param id
     * @return
     */
    public static String getName(int id) {
        for (ValidateUtils m : ValidateUtils.values()) {
            if (m.getId() == id) {
                return m.name;
            }
        }

        return null;
    }


    /**
     * 正则检查
     *
     * @param input 验证内容
     * @param regEx 验证格式(正则)
     * @return
     */
    public static boolean check(String input, String regEx) {
        // 编译正则表达式
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        sb.append(regEx);
        sb.append("$");

        Pattern pattern = Pattern.compile(sb.toString());
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input == null ? "" : input);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    /**
     * 正则检查
     *
     * @param input 验证内容
     * @param regEx 验证枚举
     * @return
     */
    public static void check(String input, ValidateUtils regEx) throws RuntimeException {
        String strPattern = regEx.getRegExLen() == null
                ? regEx.getRegEx()
                : regEx.getRegEx() + regEx.getRegExLen();

        if (!check(input, strPattern)) {
            String message = MessageFormat.format("{0} 格式错误！", regEx.getName());
            throw new RuntimeException(message);
        }
    }

    /**
     * 正则检查
     *
     * @param input 验证的内容
     * @param regEx 正则枚举
     * @param scope 验证规则 对应的数字长度
     * @param msg   提示消息
     * @throws Exception
     */
    public static void check(String input, ValidateUtils regEx, String scope, String msg) throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        sb.append(regEx.getRegEx());
        if (!ObjectUtils.isNullOfEmpty(scope)) {
            if (!check(scope, ValidateUtils.REG_EX_SCOPE.getRegEx()))
                throw new RuntimeException("参数scope格式错误！");
            sb.append(scope);
        }

        if (!check(input, sb.toString())) {
            String message = MessageFormat.format("{0} 格式错误！", msg == null ? regEx.getName() : msg);
            throw new RuntimeException(message);
        }
    }

    /**
     * 正则检查
     *
     * @param input
     * @param regEx
     * @param scope
     * @throws Exception
     */
    public static void check(String input, ValidateUtils regEx, String scope) throws RuntimeException {
        check(input, regEx, scope, null);
    }

    /**
     * 获取验证结果第一个字段错误（顺序按照实体字段顺序）
     *
     * @param result
     * @return
     */
    public static FieldError getFirstFieldError(BindingResult result) {
        if (result.hasErrors()) {
            for (Field declaredField : result.getTarget().getClass().getDeclaredFields()) {
                FieldError fieldError = result.getFieldError(declaredField.getName());
                if (fieldError != null) {
                    return fieldError;
                }
            }
        }
        return null;
    }
}
