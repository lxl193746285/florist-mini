package com.qy.tool.interfaces.message.web;


import com.qy.message.app.application.dto.MessageTemplateVo;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateService;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息模版
 */
@RestController
@RequestMapping("/v4/message/template")
public class MessageTemplateController {
    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 查询模板列表
     *
     * @param response
     * @param page
     * @param perPage
     * @param title
     * @param isEnable
     * @param titleMode
     * @param contentMode
     * @param type        发送消息类型
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MessageTemplateVo>> getMessageTemplates(
            HttpServletResponse response,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "10") Integer perPage,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "is_enable", required = false) Integer isEnable,
            @RequestParam(value = "title_mode", required = false) Integer titleMode,
            @RequestParam(value = "content_mode", required = false) Integer contentMode,
            @RequestParam(value = "model_id", required = false) String modelId,
            @RequestParam(value = "function_id", required = false) String functionId,
            @RequestParam(value = "type", required = false) Integer type) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("isEnable", isEnable);
        map.put("titleMode", titleMode);
        map.put("contentMode", contentMode);
        map.put("type", type);
        map.put("modelId", modelId);
        map.put("functionId", functionId);
        ResultBean<MessageTemplateVo> messageTemplates = null;
        messageTemplates = messageTemplateService.selectTemplateList(page, perPage, map);

        SimplePageImpl simplePage = new SimplePageImpl(page, perPage, messageTemplates.getPageCount(), messageTemplates.getTotalCount(), 0, messageTemplates.getData());
        return ResponseUtils.ok(simplePage).body(messageTemplates.getData());
    }

    /**
     * 获取单个模板详细信息
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    public MessageTemplateVo selectOne(@PathVariable(value = "id") Integer id, HttpServletResponse response) {
        MessageTemplateVo messageTemplateVo = messageTemplateService.selectOne(id);
        if (null != messageTemplateVo) {
            return messageTemplateVo;
        }
        return null;
    }


    /**
     * 新增模板
     *
     * @param messageTemplateVo
     * @param response
     * @return
     */
    @PostMapping
    public ResponseEntity<MessageTemplateVo> createMessageTemplate(@RequestBody MessageTemplateVo messageTemplateVo, HttpServletResponse response) {

        MessageTemplateVo messageTemplate = new MessageTemplateVo();
        messageTemplate = messageTemplateService.createMessageTemplateVo(messageTemplateVo);
        if (null != messageTemplate && null != messageTemplate.getId()) {
            return ResponseUtils.ok("新增成功").body(messageTemplate);
        } else {
            return ResponseUtils.badRequest("新增失败").body(messageTemplate);
        }
    }

    /**
     * 修改模板
     *
     * @param messageTemplateVo
     * @param id
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageTemplateVo> updateMessageTemplate(@RequestBody MessageTemplateVo messageTemplateVo,
                                                                   @PathVariable(value = "id") Integer id, HttpServletResponse response) {

        MessageTemplateVo messageTemplate = new MessageTemplateVo();
        messageTemplateVo.setId(id);
        messageTemplate = messageTemplateService.updateMessageTemplateVo(messageTemplateVo);
        if (null != messageTemplate && null != messageTemplate.getId()) {
            return ResponseUtils.ok("修改成功").body(messageTemplate);
        } else {
            return ResponseUtils.badRequest("修改失败").body(messageTemplate);
        }
    }


    /**
     * 删除模板
     *
     * @param id
     * @param response
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMessageTemplate(@PathVariable(value = "id") Integer id, HttpServletResponse response) {
        int success = messageTemplateService.deleteMessageTemplateVo(id);

        return ResponseUtils.noContent("删除成功").build();
    }
}
