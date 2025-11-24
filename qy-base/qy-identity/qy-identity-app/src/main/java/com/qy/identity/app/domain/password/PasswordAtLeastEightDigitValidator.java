package com.qy.identity.app.domain.password;

import org.apache.commons.lang3.StringUtils;

/**
 * 密码至少8位验证器
 *
 * @author legendjw
 */
public class PasswordAtLeastEightDigitValidator implements PasswordValidator {
    @Override
    public String getRequireHint() {
        return "密码至少8位";
    }

    @Override
    public boolean validate(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        return password.length() >= 8;
    }
}
