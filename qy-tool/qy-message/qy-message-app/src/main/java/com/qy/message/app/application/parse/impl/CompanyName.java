package com.qy.message.app.application.parse.impl;

import com.qy.message.app.application.parse.GlobalParse;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.stereotype.Component;

@Component
public class CompanyName implements GlobalParse {
	private OrganizationSessionContext sessionContext;

	@Override
	public Object parse() {
		return "";
	}
}
