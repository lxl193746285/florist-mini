package com.qy.message.app.application.parse.impl;

import com.qy.message.app.application.parse.GlobalParse;
import com.qy.rest.constant.DateTimeFormatConstant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Sysdate implements GlobalParse {

	@Override
	public Object parse() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT));
	}

}
