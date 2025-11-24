package com.qy.member.app.application.service;

import com.qy.member.app.application.command.CreateMemberSystemCommand;
import com.qy.member.app.application.command.UpdateMemberSystemCommand;
import com.qy.security.session.EmployeeIdentity;

/**
 * 会员系统命令服务
 *
 * @author legendjw
 */
public interface MemberSystemCommandService {
    /**
     * 创建会员系统命令
     *
     * @param command
     * @param identity
     * @return
     */
    Long createMemberSystem(CreateMemberSystemCommand command, EmployeeIdentity identity);

    /**
     * 编辑会员系统命令
     *
     * @param command
     * @param identity
     */
    void updateMemberSystem(UpdateMemberSystemCommand command, EmployeeIdentity identity);

    /**
     * 删除会员系统命令
     *
     * @param id
     * @param identity
     */
    void deleteMemberSystem(Long id, EmployeeIdentity identity);
}
