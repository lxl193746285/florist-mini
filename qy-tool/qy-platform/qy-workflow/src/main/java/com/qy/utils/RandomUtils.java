package com.qy.utils;

public class RandomUtils {
	
	/**
	 * 生成6位验证码
	 * @return
	 */
	public static int getRandomCode() {
		return (int)((Math.random()*9+1)*100000);
	}

}
