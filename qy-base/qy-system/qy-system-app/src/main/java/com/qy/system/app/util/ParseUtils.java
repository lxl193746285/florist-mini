package com.qy.system.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {
    public static String getDataSys(String data, Map<String, Object> map) {

        return getData("?\\{", "}", data, map);

    }

    public static String getDataCustom(String data, Map<String, Object> map) {

        return getData("%\\{", "}", data, map);

    }

    public static String getFunction(String data, Map<String, Object> map) {
        //系统
        String value = getDataSys(data, map);
        //菜单
        value = getDataMenu(value, map);
        //行变量
        value = getDataCustom(value, map);
        //条件
        value = getDataCondition(value, map);
        return getData("*\\{", "}", value, map);

    }

    public static String getDataMenu(String data, Map<String, Object> map) {

        return getData("#\\{", "}", data, map);

    }

    public static String getDataCondition(String data, Map<String, Object> map) {

        return getData("$\\{", "}", data, map);

    }

    public static String getData(String startStr, String endStr, String data, Map<String, Object> map) {

        // String skh = "\\{[^{}]+\\}";// 用于匹配{}里面的文本
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
                // System.err.println("key+++"+key);
                // data= data.replaceAll("\\{" + key + "\\}", map.get(key).toString());
                // System.err.println(data);
                Global global = Global.getClass(key);
                if (global != null) {
                    if (global.getFlag() == 2) {
                        try {
                            data = data.replaceAll("\\{" + key + "\\}", toJson(map.get(key)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //GlobalParse globalParse = (GlobalParse) ApplicationContextHolder
                        //        .getBeanByClass(StringUtils.class.getClassLoader().loadClass(Global.getName(key)));
                        Object str = "";//globalParse.parse();
                        // data = data.replaceAll("\\{" + key + "\\}", str.toString());
                        try {
                            data = data.replaceAll("\\" + startStr + key + "\\" + endStr, toJson(str));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        data = data.replaceAll("\\" + startStr + key + "\\" + endStr, toJson(map.get(key)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        data.replaceAll("\\" + startStr, "").replaceAll("\\" + endStr, "");
        //去除html标签
        //data = HtmlUtils.Html2Text(data);
        return data;
    }

    /**
     * 转化为json
     *
     * @param object
     * @return
     */
    private static String toJson(Object object) {
        String result = "";
        if (object == null)
            return result;
        ObjectMapper objectMapper = new ObjectMapper();
        if (object instanceof String) {
            return object.toString();
        }
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
