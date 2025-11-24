package com.qy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionUtils {
	/** 
	 * 获取详细的异常链信息--精准定位异常位置 
	 *  
	 * @param aThrowable 
	 * @return 
	 */  
	public static String getStackTrace(Throwable aThrowable) {  
	    final Writer result = new StringWriter();  
	    final PrintWriter printWriter = new PrintWriter(result);  
	    aThrowable.printStackTrace(printWriter);  
	    return result.toString();  
	}  
}
