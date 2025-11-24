package com.qy.member.app.application.service;

import com.qy.member.app.application.command.CreateMemberSystemWeixinAppCommand;
import com.qy.member.app.application.command.UpdateMemberSystemWeixinAppCommand;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;

/**
 * 会员系统微信应用命令服务
 *
 * @author legendjw
 */
public interface MemberSystemWeixinAppCommandService {
    /**
     * 创建会员系统微信应用命令
     *
     * @param command
     * @param identity
     * @return
     */
    Long createMemberSystemWeixinApp(CreateMemberSystemWeixinAppCommand command, EmployeeIdentity identity);

    /**
     * 编辑会员系统微信应用命令
     *
     * @param command
     * @param identity
     */
    void updateMemberSystemWeixinApp(UpdateMemberSystemWeixinAppCommand command, EmployeeIdentity identity);

    /**
     * 删除会员系统微信应用命令
     *
     * @param id
     * @param identity
     */
    void deleteMemberSystemWeixinApp(Long id, EmployeeIdentity identity);

    /**
     * 批量删除会员系统微信应用命令
     *
     * @param id
     * @param identity
     */
    void batchDeleteMemberSystemWeixinApp(List<Long> ids, EmployeeIdentity identity);
}
