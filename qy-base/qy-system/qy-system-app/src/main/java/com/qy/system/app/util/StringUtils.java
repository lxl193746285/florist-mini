package com.qy.system.app.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串辅助类
 */
public class StringUtils {

    private static final char UNDERLINE = '_';

    /**
     * 不够位数的在前面补0
     *
     * @param number
     * @param length
     * @return
     */
    public static String autoGenericNumber(Integer number, int length) {
        // 例：%04d
        // 0 代表前面补充0
        // 4 代表长度4位
        // d 代表参数为正数型
        String result = String.format("%0" + length + "d", number);

        return result;
    }

    /**
     * 根据ids字符串 返回 idList集合
     *
     * @param input
     * @return
     */
    public static List<Integer> idList(String input) {
        List<Integer> idList = new ArrayList<>();
        if (!"".equals(input) && input != null) {
            String[] ids = input.split(",");
            for (String id : ids) {
                if (!"".equals(id)) {
                    idList.add(new Integer(id));
                }
            }
        }

        return idList;
    }


    public static Collection<Integer> idCollection(String input) {
        Collection<Integer> idList = new ArrayList<>();
        if (!"".equals(input) && input != null) {
            String[] ids = input.split(",");
            for (String id : ids) {
                if (!"".equals(id)) {
                    idList.add(new Integer(id));
                }
            }
        }

        return idList;
    }

    public static int stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return Integer.parseInt(sbu.toString());
    }

    /**
     * 验证字符串最后一位是否为逗号
     *
     * @param value
     * @return
     */
    public static boolean stringIncloud(String value) {
        if (",".equals(value.substring(value.length() - 1))) {
            return true;
        }
        return false;
    }

    /**
     * 掐头去尾
     *
     * @param input
     * @return
     */
    public static String subBoth(String input) {
        return input.substring(1, input.length() - 1);
    }

    /**
     * 掐头去尾再转idList
     *
     * @param input
     * @return
     */
    public static List<Integer> bothToIdList(String input) {
        input = subBoth(input);
        return idList(input);
    }

    /**
     * id集合转ids字符串
     *
     * @param idList
     * @return
     */
    public static String idListToStr(List<Integer> idList) {
        String ids = "";
        for (int i = 0; i < idList.size(); i++) {
            if (i > 0)
                ids += ",";

            ids += idList.get(i);
        }

        return ids;
    }

    /**
     * 是否null或空字符
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(String input) {
        if (input == null || "".equals(org.apache.commons.lang3.StringUtils.trim(input)))
            return true;

        return input.isEmpty();
    }

    public static boolean areNotEmpty(CharSequence str1, CharSequence str2) {
        return isNotEmpty(str1) && isNotEmpty(str2);
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * <p>
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     * <p>
     * <pre>
     * StringUtil.isNotEmpty(null)      = false
     * StringUtil.isNotEmpty("")        = false
     * StringUtil.isNotEmpty(" ")       = true
     * StringUtil.isNotEmpty("bob")     = true
     * StringUtil.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 置换参数
     *
     * @param str
     * @param map
     * @return
     */
    public static String replaceStr(String str, Map<String, Object> map) {
        Set<String> setkey = map.keySet();
        for (String key : setkey) {
            if (str.contains(key)) {
                str = str.replaceAll("{" + key + "}", (String) map.get(key));
            }

        }
        return str;
    }


    public static List<String> getMentionMessage(String data) {
        List<String> names = new ArrayList<>();
        //String skh = "@(\\S){1,}\\s";
        String skh = "(@[^\\x00-\\xff]{1,}([A-Za-z0-9]{0,})\\s)";
        Pattern pattern = Pattern.compile(skh);
        Matcher matcher = pattern.matcher(data+" ");
        while (matcher.find()) {
            String key = matcher.group();
            names.add(key.substring(1, key.length() - 1));
        }
        return names;
    }

    public static List<String> getVariableCodes(String contn) {
        List<String> list = new ArrayList<String>();
        list = getVariableCodes("?\\{", "}", contn, list);
        list = getVariableCodes("#\\{", "}", contn, list);
        list = getVariableCodes("$\\{", "}", contn, list);
        return list;
    }

    public static List<String> getVariableCodes(String startStr, String endStr, String data, List<String> list) {
        String skh = "\\" + startStr + "[^" + startStr + endStr + "]" + "+\\" + endStr;
        Pattern pattern = Pattern.compile(skh);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String key1 = matcher.group();
            System.err.println(key1);
            // Pattern p = Pattern.compile("\\{(.*?)\\}");
            Pattern p = Pattern.compile("\\" + startStr + "(.*?)" + "\\" + endStr);
            Matcher m = p.matcher(key1);
            while (m.find()) {
                String key = m.group(1);
                list.add(key);
            }
        }
        return list;
    }

    // public static void main(String[] args)
    // throws ClassNotFoundException, InstantiationException, IllegalAccessException
    // {
    // System.out.println(Global.getName("USER_ID"));
    // System.err.println(
    // (GlobalParse)
    // (StringUtils.class.getClassLoader().loadClass(Global.getName(key)).newInstance()));
    // }

    /**
     * 是否包含中文字符
     *
     * @param strName
     * @return
     */
    public static final boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含中文字符
     *
     * @param c
     * @return
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 去掉文本中的换行符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = StringEscapeUtils.unescapeHtml4(dest);
        }
        return dest;
    }

    /**
     * 驼峰转下划线
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param input
     * @return
     */
    public static String underlineToCamel2(String input) {
        input = input.toLowerCase();
        final StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_(\\w)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);

        return sb.toString();
    }

    /**
     * 首字母转大写
     *
     * @param input
     * @return
     */
    public static String initialChupperCase(String input) {
        if (ObjectUtils.isNullOfEmpty(input))
            return "";

        String str = input.substring(0, 1).toUpperCase().concat(input.substring(1));

        return str;
    }


    /**
     * 根据字符串与符号返回string数组
     *
     * @param input  需要处理的字符串
     * @param symbol 符号
     * @return
     */
    public static List<String> strList(String input, String symbol) {
        List<String> strList = new ArrayList<>();
        if (!"".equals(input) && input != null) {
            String[] strs = input.split(symbol);
            for (String str : strs) {
                if (!"".equals(str)) {
                    strList.add(str);
                }
            }
        }
        return strList;
    }


    /**
     * 根据字符串与符号返回string数组
     * @param input  需要处理的字符串
     * @param symbol 符号
     * @param isReplaceHtml 是否替换html标签
     * @return
     */
    public static List<String> strListAndReplaceHtml(String input, String symbol, boolean isReplaceHtml) {
        List<String> strList = new ArrayList<>();
        if (!"".equals(input) && input != null) {
            String[] strs = input.split(symbol);
            for (String str : strs) {
                if (!"".equals(str)) {
                    if(isReplaceHtml){
                        str = HtmlUtils.Html2Text(str);
                    }
                    strList.add(str);
                }
            }
        }
        return strList;
    }


    /**
     * 根据数组与符号返回符号分隔的字符串
     *
     * @param inputs 需要处理的数组
     * @param symbol 符号
     * @return
     */
    public static String listToStr(List<Integer> inputs, String symbol) {
        String resultStr = "";
        if (!CollectionUtils.isEmpty(inputs)) {
            for (Integer id : inputs) {
                resultStr += id + symbol;
            }
            if (!StringUtils.isNullOfEmpty(resultStr)) {
                resultStr = resultStr.substring(0, resultStr.length() - 1);
            }
        }
        return resultStr;
    }

    /**
     * url参数转map 无解码
     *
     * @param input
     * @return
     */
    public static Map<String, String> paramToMap(String input) {
        String[] param = input.split("&");
        Map<String, String> map = Arrays.stream(param)
                .map(keyValue -> keyValue.split("="))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1], (a, b) -> b));

        return map;
    }

    /**
     * url参数转map 有解码
     *
     * @param input
     * @return
     */
    public static Map<String, String> urlParamToMap(String input) {
        if (input.contains("?")) {
            int startIndex = input.indexOf('?');
            input = input.substring(startIndex, input.length() - startIndex - 1);
        }
        String[] param = input.split("&");
        Map<String, String> map = Arrays.stream(param)
                .map(keyValue -> keyValue.split("="))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1], (a, b) -> {
                    try {
                        return URLDecoder.decode(b, HttpUtils.CHARSET_UTF8);
                    } catch (UnsupportedEncodingException e) {
                        return "";
                    }
                }));

        return map;
    }

    public static List<String> getDataKey(String startStr, String endStr, String data)
            throws ClassNotFoundException {
        List<String> keyList = new ArrayList<>();
        // String skh = "\\{[^{}]+\\}";// 用于匹配{}里面的文本
        String skh = "\\" + startStr + "[^" + startStr + endStr + "]" + "+\\" + endStr;
        Pattern pattern = Pattern.compile(skh);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String key1 = matcher.group();
            System.err.println(key1);
            Pattern p = Pattern.compile("\\" + startStr + "(.*?)" + "\\" + endStr);
            Matcher m = p.matcher(key1);
            while (m.find()) {
                String key = m.group(1);
                keyList.add(key);
            }
        }
        return keyList;
    }


    /**
     * 去掉StringBuffer最后一位字符
     *
     * @return
     */
    public static Boolean lastStringBufferIsSymbol(StringBuffer stringBuffer, String symbol) {
        String str = "";
        if (stringBuffer.length() > 0) {
            if (symbol.equals(stringBuffer.substring(stringBuffer.length() - 1, stringBuffer.length()))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    /**
     * 去掉StringBuffer最后一位字符
     *
     * @return
     */
    public static String getStringBufferRemoveSymbol(StringBuffer stringBuffer, String symbol) {
        String str = "";
        if (stringBuffer.length() > 0) {
            if (symbol.equals(stringBuffer.substring(stringBuffer.length() - 1, stringBuffer.length()))) {
                str = stringBuffer.substring(0, stringBuffer.length() - 1);
            } else {
                return stringBuffer.toString();
            }
        }
        return str;
    }

    /**
     * 去掉StringBuffer最后一位字符,并转为字符串类型的list
     *
     * @return
     */
    public static List<String> getStringBufferRemoveSymbolAndToList(StringBuffer stringBuffer, String symbol) {
        List<String> list = new ArrayList<>();
        if (symbol.equals(stringBuffer.substring(stringBuffer.length() - 1, stringBuffer.length()))) {
            String keyStr = stringBuffer.substring(0, stringBuffer.length() - 1);
            list = StringUtils.strList(keyStr, ",");
        }
        return list;
    }

    /**
     * 数字转换成星期
     *
     * @param weekday
     * @return
     */
    public static String getWeekInfoByNumber(String weekday) {
        if (StringUtils.isNullOfEmpty(weekday)) {
            return "";
        }
        String[] weekDays = {"周一", "周二", "周三", "周四", "周五", "周六", "周日", "节假日"};
        List<Integer> days = StringUtils.idList(weekday);
        days = days.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        StringBuffer stringBuffer = new StringBuffer();
        for (Integer day : days) {
            stringBuffer.append(weekDays[day - 1]);
        }
        return stringBuffer.toString();
    }


    /**
     * 处理天显示名称
     * @param monthday
     * @param dayName
     * @return
     */
    public static String getMonthInfoByNumber(String monthday, String dayName) {
        if (StringUtils.isNullOfEmpty(monthday)) {
            return "";
        }
        List<Integer> days = StringUtils.idList(monthday);
        days = days.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        StringBuffer stringBuffer = new StringBuffer();
        for (Integer day : days) {
            stringBuffer.append(day + dayName +",");
        }
        if(stringBuffer.substring(stringBuffer.length()-1,stringBuffer.length()).equals(",")){
            return stringBuffer.substring(0,stringBuffer.length()-1);
        }
        return stringBuffer.toString();
    }

}
