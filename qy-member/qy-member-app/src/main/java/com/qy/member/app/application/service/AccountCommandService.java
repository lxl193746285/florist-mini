package com.qy.member.app.application.service;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.dto.MemberOrganizationBasicDTO;
import com.qy.member.app.application.dto.MemberSystemBasicDTO;
import com.qy.member.app.application.dto.WeixinLoginDTO;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;

import java.util.List;

/**
 * 账号命令服务
 *
 * @author legendjw
 */
public interface AccountCommandService {
    /**
     * 密码登录
     *
     * @param command
     * @return
     */
    AccessTokenDTO loginByPassword(LoginByPasswordCommand command);

    /**
     * 手机验证码登录
     *
     * @param command
     * @return
     */
    AccessTokenDTO loginByPhone(LoginByPhoneCommand command);

    /**
     * 微信登录
     *
     * @param command
     * @return
     */
    WeixinLoginDTO loginByWeixin(LoginByWeixinCommand command);

    /**
     * 切换会员系统
     *
     * @param command
     * @return
     */
    AccessTokenDTO switchMemberSystem(SwitchMemberSystemCommand command);

    /**
     * 刷新令牌
     *
     * @param command
     * @return
     */
    AccessTokenDTO refreshToken(RefreshTokenCommand command);

    /**
     * 密码获取已有的会员系统
     *
     * @param command
     * @return
     */
    List<MemberSystemBasicDTO> getSystemsByPassword(LoginByPasswordCommand command);

    /**
     * 获取已有的组织
     *
     * @return
     */
    List<MemberOrganizationBasicDTO> getOrganizations(MemberIdentity identity);

    /**
     * 手机验证码获取已有的会员系统
     *
     * @param command
     * @return
     */
    List<MemberSystemBasicDTO> getSystemsByPhone(LoginByPhoneCommand command);

    /**
     * 通过手机号找回密码
     *
     * @param command
     */
    void retrievePasswordByPhone(RetrievePasswordByPhoneCommand command);

    /**
     * 修改账号信息
     *
     * @param command
     */
    void modifyAccountInfo(ModifyAccountInfoCommand command, MemberIdentity identity);

    /**
     * 修改账号手机id
     *
     * @param command
     */
    void modifyAccountMobileId(ModifyAccountMobileIdCommand command, MemberIdentity identity);

    /**
     * 账号更换手机号
     *
     * @param command
     */
    void changeAccountPhone(ChangePhoneCommand command, MemberIdentity identity);

    /**
     * 通过手机号修改账号密码
     *
     * @param command
     */
    void modifyAccountPasswordByPhone(ModifyPasswordCommand command, MemberIdentity identity);

    /**
     * 修改账号状态
     *
     * @param command
     * @param identity
     */
    void modifyAccountStatus(ModifyAccountStatusCommand command, EmployeeIdentity identity);

    /**
     * 授权权限组给账号
     *
     * @param command
     * @param identity
     */
    void assignRoleToAccount(AssignRoleToAccountCommand command, EmployeeIdentity identity);

    /**
     * 创建子账号
     *
     * @param command
     * @param identity
     */
    void createChildAccount(CreateChildAccountCommand command, EmployeeIdentity identity);


    /**
     * 账号更换手机号
     *
     * @param command
     */
    void updateAccountPhone(UpdatePhoneCommand command,Long accountId);

    /**
     * 重置密码
     *
     * @param command
     */
    void modifyAccountPassword(ModifyPasswordCommand command, Long accountId);
    /**
     * 退出登录
     *
     * @param command
     */
    void logout(LogoutCommand command);

    /**
     * 登录设备验证
     *
     * @param command
     * @param identity
     */
    void verifyDevice(VerifyDeviceCommand command, MemberIdentity identity);

    void modifyPasswordByPassword(ModifyPasswordCommand command, MemberIdentity identity);

    void modifyPasswordByPhoneAndVerificationCode(com.qy.member.api.command.ModifyPasswordCommand command);

    /**
     * 验证账号密码
     * @param username
     * @param password
     * @param systemId
     * @param organizationId
     * @return
     */
    MemberBasicDTO validAccount(String username, String password, Long systemId, Long organizationId);
}