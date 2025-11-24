package com.qy.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 */
public class TreeUtils {
    /**
     * 列表转化为tree
     *
     * @param tree
     * @return
     */
    public static List toTree(List tree) {
        try {
            return TreeUtils.toTree(tree, 0L, "id", "parentId", "children");
        }
        catch (Exception e) {
            throw new RuntimeException("树形结构转换出错：" + e.getMessage());
        }
    }

    /**
     * 列表转化为tree
     *
     * @param tree
     * @param topParentId
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static List toTree(List tree, Long topParentId, String idAttr, String parentIdAttr, String childrenAttr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (tree != null) {
            List t_list = new ArrayList();
            Map map = new LinkedHashMap();
            for (Object o : tree) {
                Class clazz = o.getClass();
                Method method = clazz.getMethod("get" + capitalize(idAttr));
                String id = method.invoke(o).toString();
                map.put(id, o);
            }

            for (Object o : map.keySet()) {
                String id = o.toString();
                Object obj = map.get(id);
                Class clazz = obj.getClass();
                Method method = clazz.getMethod("get" + capitalize(parentIdAttr));
                String parentId = method.invoke(obj).toString();
                if (parentId.equals(topParentId.toString())) {
                    //顶层
                    t_list.add(obj);
                } else {
                    //当不是顶层时，获取其父级
                    Object object = map.get(parentId);
                    if (object == null) {
                        System.out.print(obj);
                        continue;
                    }
                    Class parentClazz = object.getClass();
                    Method getChildren = parentClazz.getMethod("get" + capitalize(childrenAttr));
                    //获取其父级的子级，及自己的兄弟（平级）
                    List list = (List) getChildren.invoke(object);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(obj);
                    Method setChildren = parentClazz.getMethod("set" + capitalize(childrenAttr), List.class);
                    setChildren.invoke(object, list);
                }
            }
            return t_list;
        }
        return null;
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
