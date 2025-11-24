package com.qy.tool.interfaces.message.web;

import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateParameterService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameter;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息模版参数
 */
@RestController
@RequestMapping("/v4/message/message-template-parameters")
public class MessageTemplateParameterController {
    @Autowired
    private MessageTemplateParameterService messageTemplateParameterService;
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
    public ResponseEntity<List<MessageTemplateParameter>> getMessageTemplateParameters(
            HttpServletResponse response,
            @RequestParam(value = "template_id") Integer templateId,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) Integer perPage,
            @RequestParam(value = "key_mode", required = false) Byte keyMode,
            @RequestParam(value = "parameter_type", required = false) Byte parameterType) {
            ResultBean<MessageTemplateParameter> resultBean = messageTemplateParameterService
                    .getMessageTemplateParameters(page, perPage, keyMode, templateId, null, parameterType);

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
    public MessageTemplateParameter getMessageTemplateParametersById(
            @PathVariable(value = "id") Integer id, HttpServletResponse response) {
        MessageTemplateParameter messageTemplateParameter =
                messageTemplateParameterService.getMessageTemplateParametersById(id);
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
    public ResponseEntity<MessageTemplateParameter> addMessageTemplateParameter(
            @RequestBody MessageTemplateParameter messageTemplateParameter,
            HttpServletResponse response) {
        messageTemplateParameter =
                messageTemplateParameterService.createMessageTemplateParameter(messageTemplateParameter);
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
    public ResponseEntity<MessageTemplateParameter> updateMessageTemplateParameter(
            @PathVariable(value = "id") Integer id,
            @RequestBody MessageTemplateParameter messageTemplateParameter,
            HttpServletResponse response) {
        messageTemplateParameter.setId(id);
        messageTemplateParameter =
                messageTemplateParameterService.updateMessageTemplateParameter(messageTemplateParameter);
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
    public ResponseEntity<Object> deleteMessageTemplateParameter(@PathVariable(value = "id") Integer id,
                                               HttpServletResponse response) {
            Integer result =
                    messageTemplateParameterService.deleteMessageTemplateParameter(id);

        return ResponseUtils.noContent("删除成功").build();
    }
}
