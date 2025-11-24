package com.qy.member.app.application.service;

import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.MemberIdDTO;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;

/**
 * 会员命令服务
 *
 * @author legendjw
 */
public interface MemberCommandService {
    /**
     * 创建会员
     *
     * @param command
     * @return
     */
    MemberIdDTO createMember(CreateMemberCommand command);

    /**
     * 更新会员
     *
     * @param command
     * @return
     */
    void updateMember(UpdateMemberCommand command);

    /**
     * 注册会员
     *
     * @param command
     * @return
     */
    MemberIdDTO registerMember(RegisterMemberCommand command);

    /**
     * 账号注册会员
     *
     * @param command
     * @return
     */
    MemberIdDTO accountRegisterMember(RegisterMemberCommand command);

    /**
     * 微信注册会员
     *
     * @param command
     * @return
     */
    MemberIdDTO weixinRegisterMember(RegisterMemberCommand command);

    /**
     * 提交会员信息审核
     *
     * @param command
     */
    void submitMemberInfoAudit(SubmitMemberInfoAuditCommand command);

    /**
     * 开通会员
     *
     * @param command
     * @return
     */
    MemberIdDTO openMember(OpenMemberCommand command);

    /**
     * 修改开通会员
     *
     * @param command
     */
    void updateOpenMember(UpdateOpenMemberCommand command);

    /**
     * 绑定会员开户组织
     *
     * @param command
     * @return
     */
    void bindMemberOpenAccount(BindMemberOpenAccountCommand command);

    /**
     * 修改会员信息
     *
     * @param command
     */
    void modifyMemberInfo(ModifyMemberInfoCommand command, MemberIdentity identity);

    /**
     * 修改会员状态
     *
     * @param command
     * @param identity
     */
    void modifyMemberStatus(ModifyMemberStatusCommand command, EmployeeIdentity identity);

    /**
     * 修改会员等级
     *
     * @param command
     * @param identity
     */
    void modifyMemberLevel(ModifyMemberLevelCommand command, EmployeeIdentity identity);

    /**
     * 授权权限组给会员
     *
     * @param command
     */
    void assignRoleToMember(AssignRoleToMemberCommand command);

    /**
     * 删除会员
     *
     * @param memberId
     */
    void deleteMember(Long memberId);

    /**
     * 重置密码
     *
     * @param memberId
     */
    void resetMemberPassword(Long memberId);

    /**
     * 解除会员微信绑定关系
     *
     * @param memberId
     */
    void unbindMemberWeixin(Long memberId);

    /**
     * 门店审核会员
     *
     * @param command
     */
    void storeAuditMember(AuditMemberCommand command);

    /**
     * 平台审核会员
     *
     * @param command
     */
    void platformAuditMember(AuditMemberCommand command);

    /**
     * 已创建会员开户
     *
     * @param command
     */
    void memberOpenAccount(OpenMemberCommand command);

    MemberIdDTO openMemberWithoutAccount(OpenMemberCommand command);

}