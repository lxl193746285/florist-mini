package com.qy.message.app.application.parse.impl;

import com.qy.message.app.application.parse.GlobalParse;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyId implements GlobalParse {
	@Autowired
	private OrganizationSessionContext sessionService;

	@Override
	public Object parse() {
		EmployeeIdentity identity = sessionService.getEmployee();
		if (identity != null)
			return identity.getOrganizationId();
		else
			return 0;
	}

}
