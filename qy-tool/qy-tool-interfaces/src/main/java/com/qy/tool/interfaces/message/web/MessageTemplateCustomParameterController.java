package com.qy.tool.interfaces.message.web;

import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateCustomParameterService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameter;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息模版自定义参数
 */
@RestController
@RequestMapping("/v4/message/message-template-customer-parameters")
public class MessageTemplateCustomParameterController {
    @Autowired
    private MessageTemplateCustomParameterService messageTemplateParameterService;
    @Autowired
    private OrganizationSessionContext sessionContext;


    /**
     * 获取消息模版参数
     *
     * @param response
     * @param templateId 模版id
     * @param page
     * @param perPage
     * @param keyMode    参数取值模式
     * @return
     */
    @GetMapping
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<List<MessageTemplateCustomParameter>> getMessageTemplateCustomParameters(
            HttpServletResponse response,
            @RequestParam(value = "template_id") Integer templateId,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) Integer perPage,
            @RequestParam(value = "key_mode", required = false) Byte keyMode) {
        ResultBean<MessageTemplateCustomParameter> resultBean = messageTemplateParameterService
                .getMessageTemplateCustomParameters(page, perPage, keyMode, templateId, null);

        SimplePageImpl simplePage = new SimplePageImpl(page, perPage, resultBean.getPageCount(), resultBean.getTotalCount(), 0, resultBean.getData());
        return ResponseUtils.ok(simplePage).body(resultBean.getData());
    }


    /**
     * 根据id获取消息模版参数
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public MessageTemplateCustomParameter getMessageTemplateCustomParametersById(
            @PathVariable(value = "id") Integer id, HttpServletResponse response) {
        MessageTemplateCustomParameter messageTemplateParameter =
                messageTemplateParameterService.getMessageTemplateCustomParametersById(id);
        if (null != messageTemplateParameter) {
            return messageTemplateParameter;
        } else {
            return null;
        }
    }

    /**
     * 消息模版参数添加
     *
     * @param messageTemplateParameter
     * @param response
     * @return
     */
    @PostMapping
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<MessageTemplateCustomParameter> addMessageTemplateCustomParameter(
            @RequestBody MessageTemplateCustomParameter messageTemplateParameter,
            HttpServletResponse response) {
        messageTemplateParameter =
                messageTemplateParameterService.createMessageTemplateCustomParameter(messageTemplateParameter);
        if (messageTemplateParameter != null) {
            return ResponseUtils.ok("新增成功").body(messageTemplateParameter);
        } else {
            return null;
        }
    }

    /**
     * 修改消息模版参数
     *
     * @param id
     * @param messageTemplateParameter
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<MessageTemplateCustomParameter> updateMessageTemplateCustomParameter(
            @PathVariable(value = "id") Integer id,
            @RequestBody MessageTemplateCustomParameter messageTemplateParameter,
            HttpServletResponse response) {
        messageTemplateParameter.setId(id);
        messageTemplateParameter =
                messageTemplateParameterService.updateMessageTemplateCustomParameter(messageTemplateParameter);
        if (null != messageTemplateParameter) {
            return ResponseUtils.ok("修改成功").body(messageTemplateParameter);
        } else {
            return ResponseUtils.badRequest("修改失败").body(messageTemplateParameter);
        }
    }

    /**
     * 删除消息模版参数
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    //@RequirePermission("backend/backend/departmentxxx/index")
    public ResponseEntity<Object> deleteMessageTemplateCustomParameter(@PathVariable(value = "id") Integer id,
                                                     HttpServletResponse response) {
        Integer result =
                messageTemplateParameterService.deleteMessageTemplateCustomParameter(id);

        return ResponseUtils.noContent("删除成功").build();
    }
}
