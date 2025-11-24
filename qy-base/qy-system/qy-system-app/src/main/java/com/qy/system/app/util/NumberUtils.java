package com.qy.system.app.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * 数字对象辅助类
 */
public class NumberUtils {

	/**
	 * 是否为null或空(0即空)
	 *
	 * @param input
	 * @return
	 */
	public static boolean isNullOfEmpty(Integer input) {
		return input == null || input.intValue() == 0;
	}
	public static boolean isNullOfEmpty(Long input) {
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
	 * 获取大写数字
	 * @param number
	 * @return
	 */
	public static String getUpperByNumber(int number){
		String[] weekDays = { "一", "二", "三", "四", "五", "六","七", "八", "九", "十", "十一", "十二"};
		return weekDays[number];
	}

	/**
     * 获取可读的字节数量
	 *
	 * @param bytes
	 * @return
	 */
	public static String getHumanReadableByteCountBin(long bytes) {
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024) {
			return bytes + " B";
		}
		long value = absB;
		CharacterIterator ci = new StringCharacterIterator("KMGTPE");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.2f %ciB", value / 1024.0, ci.current());
	}
}
