package com.qy.identity.app.application.service;

import com.qy.identity.app.application.command.*;
import com.qy.identity.app.domain.valueobject.UserId;
import com.qy.identity.app.application.command.*;

/**
 * 用户命令服务
 *
 * @author legendjw
 */
public interface UserCommandService {
    /**
     * 创建账号
     *
     * @param command
     * @return
     */
    UserId createAccount(CreateAccountCommand command);

    /**
     * 修改账号
     *
     * @param command
     */
    void modifyAccount(ModifyAccountCommand command);

    /**
     * 修改用户信息
     *
     * @param command
     */
    void modifyUserInfo(ModifyUserInfoCommand command);

    /**
     * 用户更换手机号
     *
     * @param command
     */
    void changeUserPhone(ChangePhoneCommand command);

    /**
     * 用户更换用户名
     *
     * @param command
     */
    void changeUsername(ChangeUsernameCommand command);

    /**
     * 通过手机号修改用户密码
     *
     * @param command
     */
    void modifyUserPasswordByPhone(ModifyPasswordByPhoneCommand command);

    /**
     * 通过旧密码修改用户密码
     *
     * @param command
     */
    void modifyUserPasswordByOldPassword(ModifyPasswordByOldPasswordCommand command);

    /**
     * 直接修改用户密码
     *
     * @param command
     */
    void modifyUserPassword(ModifyPasswordCommand command);
}
