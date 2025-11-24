package com.qy.utils;

import java.util.Random;
import java.util.UUID;

public class UuidUtils {
	
	/**
	 * 获取uuid 包含 - 长度36
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().toLowerCase();
	}

	/**
	 * 获取uuid 除去 - 长度32
	 * @return
	 */
	public static String getUuidStr() {
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}


	/**
	 * 获取随机字母数字组合
	 *
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符串
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
				str += (char) (65 + random.nextInt(26));// 取得大写字母
			} else { // 数字
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}



	/**
	 * 生成指定位数的随机数
	 * @param length
	 * @return
	 */
	public static String getRandom(int length){
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}


	/**
	 * 获取自定义开头加指定位数的随机数
	 * @param header 自定义开头
	 * @param length 位数
	 * @return
	 */
	public static String getRandomForCustomHeader(String header, int length){
		return header + getRandom(length);
	}

}
