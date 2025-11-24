package com.qy.utils;

import java.util.Random;

/**
 *
 * @author qiyun
 *
 */
public class CharacterUtils {

	/**
	 * 生成随机字符串
	 * @param length 生成的位数
	 * @return
	 */
	  public static String getRandomString(int length){
	    //产生随机数
	    Random random=new Random();
	    StringBuffer sb=new StringBuffer();
	    //循环length次
	    for(int i=0; i<length; i++){
	      //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
	      int number=random.nextInt(3);
	      long result=0;
	      switch(number){
	      //如果number产生的是数字0；
	      case 0:
	        //产生A-Z的ASCII码
	        result=Math.round(Math.random()*25+65);
	        //将ASCII码转换成字符
	        sb.append(String.valueOf((char)result));
	        break;
	        case 1:
	          //产生a-z的ASCII码
	        result=Math.round(Math.random()*25+97);
	          sb.append(String.valueOf((char)result));
	        break;
	        case 2:
	          //产生0-9的数字
	                   sb.append(String.valueOf
	                          (new Random().nextInt(10)));
	        break;
	      }
	    }
	    return sb.toString();
	  }


	  /**
	   * 数字转周
	   * @param cycle
	   * @return
	   */
	  public static String transformWeek(String cycle) {
		if ("1".equals(cycle)) {
			return "周一";
		} else if ("2".equals(cycle)) {
			return "周二";
		} else if ("3".equals(cycle)) {
			return "周三";
		} else if ("4".equals(cycle)) {
			return "周四";
		} else if ("5".equals(cycle)) {
			return "周五";
		} else if ("6".equals(cycle)) {
			return "周六";
		} else if ("7".equals(cycle)) {
			return "周日";
		} else {
			return "";
		}
	}

}
