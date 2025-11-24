package com.qy.organization.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 组织设置
 */
@Component
public class OrganizationConfig {
	/**
	 * 组织模式：SINGLE（单公司） MULTI(多公司)
	 */
	@Value("${qy.organization.mode}")
	public String organizationMode;

	/**
	 * 是否开启员工维护时账号验证
	 */
	@Value("${qy.organization.enable-employee-account-validate}")
	public boolean enableEmployeeAccountValidate;
}