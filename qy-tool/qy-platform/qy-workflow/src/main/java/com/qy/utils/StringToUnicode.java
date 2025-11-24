package com.qy.utils;

public class StringToUnicode {
  /**
   * 字符串转换unicode
   */
  public static String stringToUnicode(String string) {
  	if(string == null)
  		return "";

    // StringBuffer unicode = new StringBuffer();
    String unicode = "";
    /*
     * for (int i = 0; i < string.length(); i++) {
     * 
     * // 取出每一个字符 char c = string.charAt(i);
     * 
     * // 转换为unicode unicode.append("\\u" + Integer.toHexString(c)); }
     */
    for (int i = 0; i < string.length(); i++) {
      int chr1 = (char) string.charAt(i);
      if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
        unicode += "\\u" + Integer.toHexString(chr1);
      } else {
        unicode += string.charAt(i);
      }
    }

    return unicode.toString();
  }
}
