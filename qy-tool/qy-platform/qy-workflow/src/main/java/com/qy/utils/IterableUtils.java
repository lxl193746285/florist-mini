package com.qy.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Iterable迭代器接口 扩展foreach
 */
public class IterableUtils {

	public IterableUtils(Object obj) {
		elements = obj;
	}

	private Object elements;

	/**
	 * foreach扩展 index,value
	 * @param action
	 * @param <E>
	 */
	public <E> void forEach(
			BiConsumer<Integer, ? super E> action
	) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : (Iterable<? extends E>) elements) {
            action.accept(index++, element);
        }
    }

	public static IterableUtils stream(String[] input) {
		IterableUtils output = new IterableUtils(Arrays.asList(input));
		return output;
	}

}
