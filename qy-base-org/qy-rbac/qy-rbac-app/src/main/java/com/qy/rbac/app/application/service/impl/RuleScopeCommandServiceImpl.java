package com.qy.rbac.app.application.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.rbac.app.application.command.CreateRuleScopeCommand;
import com.qy.rbac.app.application.command.DeleteRuleScopeCommand;
import com.qy.rbac.app.application.command.UpdateRuleScopeCommand;
import com.qy.rbac.app.application.service.RuleScopeCommandService;
import com.qy.rbac.app.infrastructure.persistence.RuleScopeDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 规则范围命令实现
 *
 * @author legendjw
 */
@Service
public class RuleScopeCommandServiceImpl implements RuleScopeCommandService {
    private RuleScopeDataRepository ruleScopeDataRepository;
    private CodeTableClient codeTableClient;

    public RuleScopeCommandServiceImpl(RuleScopeDataRepository ruleScopeDataRepository, CodeTableClient codeTableClient) {
        this.ruleScopeDataRepository = ruleScopeDataRepository;
        this.codeTableClient = codeTableClient;
    }

    @Override
    @Transactional
    public String createRuleScope(CreateRuleScopeCommand command) {
        if (ruleScopeDataRepository.countById(command.getId(), null) > 0) {
            throw new ValidationException("指定规则范围id已经存在，请更换新的id");
        }

        RuleScopeDO ruleScopeDO = new RuleScopeDO();
        BeanUtils.copyProperties(command, ruleScopeDO, "identity");
        ruleScopeDO.setCreateTime(LocalDateTime.now());
        fillFormRelatedData(ruleScopeDO);
        ruleScopeDataRepository.insert(ruleScopeDO);

        return ruleScopeDO.getId();
    }

    @Override
    @Transactional
    public void updateRuleScope(UpdateRuleScopeCommand command) {
        RuleScopeDO ruleScopeDO = ruleScopeDataRepository.findById(command.getUpdateId());
        if (ruleScopeDO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        if (ruleScopeDataRepository.countById(command.getId(), ruleScopeDO.getId()) > 0) {
            throw new ValidationException("指定规则范围id已经存在，请更换新的id");
        }

        BeanUtils.copyProperties(command, ruleScopeDO, "identity");
        fillFormRelatedData(ruleScopeDO);
        ruleScopeDataRepository.update(command.getUpdateId(), ruleScopeDO);
    }

    @Override
    public void deleteRuleScope(DeleteRuleScopeCommand command) {
        ruleScopeDataRepository.remove(command.getId());
    }

    /**
     * 填充表单关联数据
     *
     * @param ruleScopeDO
     */
    private void fillFormRelatedData(RuleScopeDO ruleScopeDO) {

    }
}