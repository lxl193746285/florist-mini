package com.qy.member.app.domain.password;

/**
 * 密码验证器
 * 主要用来验证指定密码是否符合指定的要求
 *
 * @author legendjw
 */
public interface PasswordValidator {
    /**
     * 获取密码要求提示
     *
     * @return
     */
    String getRequireHint();

    /**
     * 验证密码是否符合要求
     *
     * @param password
     * @return
     */
    boolean validate(String password);
}