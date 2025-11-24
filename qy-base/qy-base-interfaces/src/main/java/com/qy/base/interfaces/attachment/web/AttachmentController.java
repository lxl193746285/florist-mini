package com.qy.base.interfaces.attachment.web;

import com.qy.attachment.app.application.dto.ConfigDTO;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.rest.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 附件
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/attachment")
public class AttachmentController {
    private AttachmentQueryService attachmentQueryService;

    public AttachmentController(AttachmentQueryService attachmentQueryService) {
        this.attachmentQueryService = attachmentQueryService;
    }

    /**
     * 获取附件设置
     *
     * @param moduleId 模块
     * @param type 类型：image: 图片, file: 文件, video: 视频
     * @param scenario 场景
     * @return
     */
    @GetMapping("/configs")
    public ResponseEntity<ConfigDTO> getConfig(
            @RequestParam(value = "module_id", required = false, defaultValue = "") String moduleId,
            @RequestParam(value = "type", required = false, defaultValue = "file") String type,
            @RequestParam(value = "scenario", required = false, defaultValue = "default") String scenario
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getConfigs(moduleId, type, scenario));
    }
}