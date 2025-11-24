package com.qy.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * object扩展
 */
public class ObjectUtils {

    /**
     * map转object
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object fromMap(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        List<Field> fields = getFields(obj);
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * object转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> toMap(Object obj) throws RuntimeException {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            List<Field> fields = getFields(obj);
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * T object转驼峰key的map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, String> toSnakeMap(Object obj) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        Map<String, String> dstMap = new HashMap<>();
        List<Field> fields = getFields(obj);
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }

            String dstName = StringUtils.camelToUnderline(field.getName());
            PropertyDescriptor pd;
            pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method method = pd.getReadMethod();
            Object dstValue = method.invoke(obj);

            if (dstValue == null) {
                continue;
            } else if (dstValue instanceof Date) {
                dstValue = DateUtils.DateToStr((Date) dstValue);
            } else if (dstValue instanceof ArrayList) {
                continue;
            }

            dstMap.put(dstName, dstValue.toString());
        }

        return dstMap;
    }

    /**
     * T object转驼峰的 List<NameValuePair>
     *
     * @param obj
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     */
    public static List<NameValuePair> toSnakeNVPair(Object obj) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        List<NameValuePair> dstList = new ArrayList<>();
        List<Field> fields = getFields(obj);
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }

            String dstName = StringUtils.camelToUnderline(field.getName());
            PropertyDescriptor pd;
            pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method method = pd.getReadMethod();
            Object dstValue = method.invoke(obj);

            if (dstValue == null) {
                continue;
            } else if (dstValue instanceof Date) {
                dstValue = DateUtils.DateToStr((Date) dstValue);
            } else if (dstValue instanceof ArrayList) {
                continue;
            }
            dstList.add(new BasicNameValuePair(dstName, dstValue.toString()));
        }

        return dstList;
    }

    /**
     * 递归获取对象及父类的字段
     *
     * @param clazz
     * @param fieldList
     */
    private static void getFields(Class<?> clazz, List<Field> fieldList) {
        if (clazz.getName().equals("java.lang.Object"))
            return;

        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(m -> fieldList.add(m));

        getFields(clazz.getSuperclass(), fieldList);
    }

    /**
     * 查询字段列表
     *
     * @param target
     */
    public static List<Field> getFields(Object target) {
        List<Field> fields = new ArrayList<>();
        getFields(target.getClass(), fields);

        return fields;
    }

    /**
     * 获取对象的指定方法
     *
     * @param obj
     * @param fieldName
     * @param parameterTypes
     * @param <T>
     * @return
     */
    public static <T> Method getMethod(T obj, String fieldName, Class<?>... parameterTypes) {
        try {
            return obj.getClass().getMethod(fieldName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 是否null或空字符
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(String input) {
        return input == null || input.isEmpty();
    }

    /**
     * 是否为null或空(0即空)
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(Integer input) {
        return input == null || input.intValue() == 0;
    }

    /**
     * 是否为null或空(0即空)
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(Byte input) {
        return input == null;
    }

    /**
     * 是否为null或空(0即空)
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(BigDecimal input) {
        return input == null || input.doubleValue() == 0;
    }


    /**
     * 是否为null或空(0即空)
     *
     * @param input
     * @return
     */
    public static boolean isNullOfEmpty(Long input) {
        return input == null || input.longValue() == 0;
    }
}
