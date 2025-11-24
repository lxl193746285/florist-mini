package com.qy.attachment.api.client;

import com.qy.attachment.api.command.RelateAttachmentCommand;
import com.qy.attachment.api.command.RelateAttachmentsCommand;
import com.qy.attachment.api.command.RepairThumbImageCommand;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.attachment.api.dto.AttachmentDTO;
import com.qy.attachment.api.dto.AttachmentThumbUrlRelationDTO;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 联系人管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-attachment", configuration = FeignTokenRequestInterceptor.class)
public interface AttachmentClient {
    /**
     * 获取单个附件信息
     *
     * @return
     */
    @GetMapping("/v4/api/attachment/{id}")
    AttachmentDTO getAttachment(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取单个附件基本信息
     *
     * @return
     */
    @GetMapping("/v4/api/attachment/basic-attachments/{id}")
    AttachmentBasicDTO getBasicAttachment(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取多个附件基本信息
     *
     * @return
     */
    @GetMapping("/v4/api/attachment/basic-attachments")
    List<AttachmentBasicDTO> getBasicAttachment(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 获取关联的单附件基本信息
     *
     * @return
     */
    @GetMapping("/v4/api/attachment/related-basic-attachment")
    AttachmentBasicDTO getRelatedBasicAttachment(
            @RequestParam("module_id") String moduleId,
            @RequestParam("data_id") Long dataId,
            @RequestParam(value = "type", required = false) String type
    );

    /**
     * 获取关联的多附件基本信息
     *
     * @return
     */
    @GetMapping("/v4/api/attachment/related-basic-attachments")
    List<AttachmentBasicDTO> getRelatedBasicAttachments(
            @RequestParam("module_id") String moduleId,
            @RequestParam("data_id") Long dataId,
            @RequestParam(value = "type", required = false) String type
    );

    /**
     * 关联单附件
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/attachment/relate-attachment")
    void relateAttachment(
            @Valid @RequestBody RelateAttachmentCommand command
    );

    /**
     * 关联多附件
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/attachment/relate-attachments")
    void relateAttachments(
            @Valid @RequestBody RelateAttachmentsCommand command
    );

    /**
     * 修复图片压缩
     * @param repairThumbImageCommand
     */
    @PostMapping("/v4/api/attachment/repair-thumb-image")
    List<AttachmentThumbUrlRelationDTO> repairThumbImage(
            @RequestBody RepairThumbImageCommand repairThumbImageCommand
    );

    /**
     * 创建附件
     * @param attachmentDTO
     */
    @PostMapping("/v4/api/attachment/basic-attachments")
    AttachmentBasicDTO createBasicAttachment(
            @RequestBody AttachmentDTO attachmentDTO
    );

    @PostMapping(value = "/v4/api/attachment/local/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<AttachmentBasicDTO> publicLocalUploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "module_id", required = false) String moduleId,
            @RequestParam(value = "uid", required = false) String uid
    );

    @PostMapping(value = "/v4/api/attachment/oss/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String publicOssUploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "save_path") String savePath,
            @RequestParam(value = "file_type") String fileType
    );
}
