package com.qy.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;


/**
 * 按拼音首字母表排序
 * 例如:首字母A开头的放在A下面，B开头的放到B下面
 * @author jzz
 *
 */
public class SortByLetterAscUtils {

    /**
     *
     * @param object 一个集合
     * @param clazz 集合存放的类
     * @param field 要排序的类的字段,字段的getXyz() X要大写
     * @return
     * @throws Exception
     */
    public static Map<String,ArrayList<Object>> sortByLetterAsc(Object object,Class clazz,String field) throws Exception {
        if(object instanceof List){
            List<Object> list = (List<Object>) object;
            Class<?> c = Class.forName(clazz.getName());
            Object o = c.newInstance();
            Map<String,Object> map = new HashMap<String,Object>();
            //按拼音首字母表排序
            Map<String,ArrayList<Object>> letterMap = new TreeMap<String,ArrayList<Object>>(new Comparator<Object>() {
                public int compare(Object obj1, Object obj2) {
                    String v1 = (String) obj1;
                    String v2 = (String) obj2;
                    if(v1.equals("#")){
                        int n = v2.compareTo(v1);
                        return n;
                    }else{
                        int n = v1.compareTo(v2);
                        return n;
                    }
                }
            });
            if(!list.isEmpty()) {
                for (Object t : list) {
                    o = t;
                    String pinYinFirstLetter = getFieldValue(field, o);
                    if(!letterMap.containsKey(pinYinFirstLetter) && pinYinFirstLetter.matches("[A-Z]")){
                        letterMap.put(pinYinFirstLetter, new ArrayList<Object>());
                    }
                }

                Iterator<Entry<String, ArrayList<Object>>> entries  = letterMap.entrySet().iterator();
                while(entries.hasNext()){
                    Entry<String, ArrayList<Object>> next = entries.next();
                    ArrayList<Object> listTemp = new ArrayList<Object>();
                    for (Object t : list) {
                        o = t;
                        String pinYinFirstLetter = getFieldValue(field, o);
                        if(!StringUtils.isEmpty(pinYinFirstLetter) && next.getKey().equals(pinYinFirstLetter)){
                            listTemp.add(t);
                        }
                    }
                    next.getValue().addAll(listTemp);
                }
                //log.debug("对letterMap按照升序排序,#放到最后");
            }

            ArrayList<Object> listTemp2 = new ArrayList<Object>();
            if(!list.isEmpty()){
                for (Object t : list) {
                    o = t;
                    String pinYinFirstLetter = getFieldValue(field, o);
                    if (!pinYinFirstLetter.matches("[A-Z]")) {
                        listTemp2.add(t);
                    }
                }

                if(!listTemp2.isEmpty()){
                    letterMap.put("#", listTemp2);
                }
            }

            //保证map排序后的顺序不
            /*JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("key", letterMap);
            map.put("data",jsonObject);*/
            return letterMap;
        }else {
            //log.info("转化为list失败");
            return null;
        }

    }

    /**
     * 获取传递字段的属性值
     * @param field
     * @param o
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static String getFieldValue(String field, Object o)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        String name = field.substring(0, 1).toUpperCase() + field.substring(1);
        Method method = o.getClass().getMethod("get"+name);
        String value = (String) method.invoke(o);
        String pinYinFirstLetter = Cn2Spell.converterToFirstSpell(value).substring(0,1).toUpperCase();
        return pinYinFirstLetter;
    }
}