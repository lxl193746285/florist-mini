package com.qy.rbac.app.application.service;

import com.qy.rbac.app.application.command.CreateRuleScopeCommand;
import com.qy.rbac.app.application.command.DeleteRuleScopeCommand;
import com.qy.rbac.app.application.command.UpdateRuleScopeCommand;

/**
 * 规则范围命令服务
 *
 * @author legendjw
 */
public interface RuleScopeCommandService {
    /**
     * 创建规则范围命令
     *
     * @param command
     * @return
     */
    String createRuleScope(CreateRuleScopeCommand command);

    /**
     * 编辑规则范围命令
     *
     * @param command
     */
    void updateRuleScope(UpdateRuleScopeCommand command);

    /**
     * 删除规则范围命令
     *
     * @param command
     */
    void deleteRuleScope(DeleteRuleScopeCommand command);
}
