package com.qy.tool.interfaces.message.web;

import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateRuleService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRule;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息模版规则
 */
@RestController
@RequestMapping("/v4/message/message-template-rule")
public class MessageTemplateRuleController {
    @Autowired
    private MessageTemplateRuleService messageTemplateRuleService;
    @Autowired
    private OrganizationSessionContext sessionContext;

    /**
     * 获取消息模版规则
     *
     * @param response
     * @param templateId 模版id
     * @param page
     * @param perPage
     * @return
     */
    @GetMapping
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<List<MessageTemplateRule>> getMessageTemplateRules(
            HttpServletResponse response,
            @RequestParam(value = "template_id") Integer templateId,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) Integer perPage) {
        ResultBean<MessageTemplateRule> resultBean =
                messageTemplateRuleService.getMessageTemplateRules(page, perPage, templateId, null);

        SimplePageImpl simplePage = new SimplePageImpl(page, perPage, resultBean.getPageCount(), resultBean.getTotalCount(), 0, resultBean.getData());
        return ResponseUtils.ok(simplePage).body(resultBean.getData());
    }


    /**
     * 根据id获取消息模版规则
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public MessageTemplateRule getMessageTemplateRulesById(@PathVariable(value = "id") Integer id,
                                                           HttpServletResponse response) {
        MessageTemplateRule messageTemplateRule =
                messageTemplateRuleService.getMessageTemplateRulesById(id);
        if (null != messageTemplateRule) {
            return messageTemplateRule;
        } else {
            return null;
        }
    }


    /**
     * 消息模版规则添加
     *
     * @param messageTemplateRule
     * @param response
     * @return
     */
    @PostMapping
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<MessageTemplateRule> addMessageTemplateRule(
            @RequestBody MessageTemplateRule messageTemplateRule, HttpServletResponse response) {
        messageTemplateRule =
                messageTemplateRuleService.createMessageTemplateRule(messageTemplateRule);
        if (messageTemplateRule != null) {
            return ResponseUtils.ok("新增成功").body(messageTemplateRule);
        } else {
            return null;
        }
    }

    /**
     * 修改消息模版规则
     *
     * @param id
     * @param messageTemplateRule
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<MessageTemplateRule> updateMessageTemplateRule(@PathVariable(value = "id") Integer id,
                                                         @RequestBody MessageTemplateRule messageTemplateRule, HttpServletResponse response) {
        messageTemplateRule.setId(id);
        messageTemplateRule =
                messageTemplateRuleService.updateMessageTemplateRule(messageTemplateRule);
        if (null != messageTemplateRule) {
            return ResponseUtils.ok("修改成功").body(messageTemplateRule);
        } else {
            return ResponseUtils.badRequest("修改失败").body(messageTemplateRule);
        }
    }

    /**
     * 删除消息模版规则
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<Object> deleteMessageTemplateRule(@PathVariable(value = "id") Integer id,
                                          HttpServletResponse response) {
        Integer result = messageTemplateRuleService.deleteMessageTemplateRule(id);

        return ResponseUtils.noContent("删除成功").build();
    }
}
