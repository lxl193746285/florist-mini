package com.qy.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListUtils {

    /**
     * 获取两个List的不同元素
     *
     * @param source
     * @param target
     * @return
     */
    public static List<Integer> getDiffrentValue(List<Integer> source, List<Integer> target) {
        Map<String, Integer> map = new HashMap<String, Integer>(source.size() + target.size());
        List<Integer> diff = new ArrayList<Integer>();
        for (Integer integer : source) {
            map.put(integer.toString(), integer);
        }
        for (Integer integer : target) {
            Integer cc = map.get(integer.toString());
            if (cc != null) {
                map.put(integer.toString(), ++cc);
                continue;
            }
            map.put(integer.toString(), 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(Integer.parseInt(entry.getKey()));
            }
        }
        return diff;

    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
