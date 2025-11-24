package com.qy.utils;

import javax.xml.bind.annotation.XmlElement;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对比两个对象的差值
 * Created by Administrator on 2018/7/9.
 */
public class CompareObject<T> {

    private T original;

    private T current;

    /**
     * @param cls
     * @return
     */
    public String contrastObj(Class<T> cls) {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                //1、获取属性上的指定类型的注解 
                Annotation annotation = field.getAnnotation(XmlElement.class);
                //有该类型的注解存在 
                if (annotation != null) {
                    //强制转化为相应的注解 
                    XmlElement xmlElement = (XmlElement) annotation;
                    if (xmlElement.name().equals("##TableField")) {
                        continue;
                    }
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                Method getMethod = pd.getReadMethod();
                String type = field.getType().getName();
                if (!"java.util.Set".equals(type)) {
                    Object o1 = getMethod.invoke(this.original);
                    Object o2 = getMethod.invoke(this.current);
                    if (null != o2) {
                        String s1 = o1 == null ? "" : o1.toString();
                        String s2 = o2 == null ? "" : o2.toString();
                        if (!s1.equals(s2)) {
                            //System.out.println("不一样的属性：" + field.getName() + " 属性值：[" + s1 + "," + s2 + "]");
                            sb.append(field.getName() + ":" + "[" + s1 + "," + s2 + "];");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public T getOriginal() {
        return original;
    }

    public void setOriginal(T original) {
        this.original = original;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }
}
