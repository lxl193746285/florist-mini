package com.qy.base.interfaces.attachment.web;

import com.qy.attachment.app.application.command.CreateThumbConfigCommand;
import com.qy.attachment.app.application.command.DeleteThumbConfigCommand;
import com.qy.attachment.app.application.command.UpdateThumbConfigCommand;
import com.qy.attachment.app.application.dto.AttachmentThumbConfigDTO;
import com.qy.attachment.app.application.query.ThumbConfigQuery;
import com.qy.attachment.app.application.service.ThumbConfigCommandService;
import com.qy.attachment.app.application.service.ThumbConfigQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 缩略图配置
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/attachment/thumb-configs")
public class AttachmentThumbConfigController {
    private OrganizationSessionContext sessionContext;
    private ThumbConfigCommandService attachmentThumbConfigCommandService;
    private ThumbConfigQueryService attachmentThumbConfigQueryService;

    public AttachmentThumbConfigController(OrganizationSessionContext sessionContext, ThumbConfigCommandService attachmentThumbConfigCommandService, ThumbConfigQueryService attachmentThumbConfigQueryService) {
        this.sessionContext = sessionContext;
        this.attachmentThumbConfigCommandService = attachmentThumbConfigCommandService;
        this.attachmentThumbConfigQueryService = attachmentThumbConfigQueryService;
    }

    /**
     * 获取缩略图配置列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<AttachmentThumbConfigDTO>> getAttachmentThumbConfigs(ThumbConfigQuery query) {
        Page<AttachmentThumbConfigDTO> page = attachmentThumbConfigQueryService.getAttachmentThumbConfigs(query);

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个缩略图配置
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttachmentThumbConfigDTO> getAttachmentThumbConfig(
        @PathVariable(value = "id") Long id
    ) {
        AttachmentThumbConfigDTO attachmentThumbConfigDTO = attachmentThumbConfigQueryService.getAttachmentThumbConfigById(id);
        if (attachmentThumbConfigDTO == null) {
            throw new NotFoundException("未找到指定的缩略图配置");
        }
        return ResponseUtils.ok().body(attachmentThumbConfigDTO);
    }

    /**
     * 创建单个缩略图配置
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createAttachmentThumbConfig(
            @Valid @RequestBody CreateThumbConfigCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getUser());
        attachmentThumbConfigCommandService.createAttachmentThumbConfig(command);

        return ResponseUtils.ok("缩略图配置创建成功").build();
    }

    /**
     * 修改单个缩略图配置
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateAttachmentThumbConfig(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateThumbConfigCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AttachmentThumbConfigDTO attachmentThumbConfigDTO = getAttachmentThumbConfigById(id);

        command.setId(id);
        command.setIdentity(sessionContext.getUser());
        attachmentThumbConfigCommandService.updateAttachmentThumbConfig(command);

        return ResponseUtils.ok("缩略图配置修改成功").build();
    }

    /**
     * 删除单个缩略图配置
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAttachmentThumbConfig(
        @PathVariable(value = "id") Long id
    ) {
        AttachmentThumbConfigDTO attachmentThumbConfigDTO = getAttachmentThumbConfigById(id);
        DeleteThumbConfigCommand command = new DeleteThumbConfigCommand();
        command.setId(id);
        command.setIdentity(sessionContext.getUser());
        attachmentThumbConfigCommandService.deleteAttachmentThumbConfig(command);

        return ResponseUtils.noContent("删除缩略图配置成功").build();
    }

    /**
     * 根据id获取缩略图配置
     *
     * @param id
     * @return
     */
    private AttachmentThumbConfigDTO getAttachmentThumbConfigById(Long id) {
        AttachmentThumbConfigDTO attachmentThumbConfigDTO = attachmentThumbConfigQueryService.getAttachmentThumbConfigById(id);
        if (attachmentThumbConfigDTO == null) {
            throw new NotFoundException("未找到指定的缩略图配置");
        }
        return attachmentThumbConfigDTO;
    }
}

