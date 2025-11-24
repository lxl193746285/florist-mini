package com.qy.base.interfaces.attachment.api;

import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.RelateAttachmentCommand;
import com.qy.attachment.app.application.command.RelateAttachmentsCommand;
import com.qy.attachment.app.application.command.RepairThumbImageCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.application.dto.AttachmentThumbUrlRelationDTO;
import com.qy.attachment.app.application.service.AliyunOssService;
import com.qy.attachment.app.application.service.AttachmentCommandService;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.LocalFileUploadService;
import com.qy.attachment.app.application.service.impl.LocalFileUploadServiceImpl;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/attachment")
public class AttachmentApiController {
    private AttachmentQueryService attachmentQueryService;
    private AttachmentCommandService attachmentCommandService;
    private AliyunOssService aliyunOssService;
    private LocalFileUploadService localFileUploadService;

    public AttachmentApiController(AttachmentQueryService attachmentQueryService,
                                   AttachmentCommandService attachmentCommandService,
                                   AliyunOssService aliyunOssService,
                                   LocalFileUploadService localFileUploadService) {
        this.attachmentQueryService = attachmentQueryService;
        this.attachmentCommandService = attachmentCommandService;
        this.aliyunOssService = aliyunOssService;
        this.localFileUploadService = localFileUploadService;
    }

    /**
     * 获取单个附件信息
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDTO> getAttachment(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getAttachmentById(new AttachmentId(id)));
    }

    /**
     * 获取单个附件信息
     *
     * @return
     */
    @GetMapping("/basic-attachments/{id}")
    public ResponseEntity<AttachmentBasicDTO> getBasicAttachment(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getBasicAttachmentById(new AttachmentId(id)));
    }

    /**
     * 获取多个附件信息
     *
     * @return
     */
    @GetMapping("/basic-attachments")
    public ResponseEntity<List<AttachmentBasicDTO>> getBasicAttachment(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getBasicAttachmentByIds(ids.stream().map(id -> new AttachmentId(id)).collect(Collectors.toList())));
    }

    /**
     * 获取关联的单附件信息
     *
     * @return
     */
    @GetMapping("/related-basic-attachment")
    public ResponseEntity<AttachmentBasicDTO> getRelatedBasicAttachment(
            @RequestParam("module_id") String moduleId,
            @RequestParam("data_id") Long dataId,
            @RequestParam(value = "type", required = false) String type
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getRelatedBasicAttachment(moduleId, dataId, type));
    }

    /**
     * 获取关联的多附件信息
     *
     * @return
     */
    @GetMapping("/related-basic-attachments")
    public ResponseEntity<List<AttachmentBasicDTO>> getRelatedBasicAttachments(
            @RequestParam("module_id") String moduleId,
            @RequestParam("data_id") Long dataId,
            @RequestParam(value = "type", required = false) String type
    ) {
        return ResponseUtils.ok().body(attachmentQueryService.getRelatedBasicAttachments(moduleId, dataId, type));
    }

    /**
     * 关联单附件
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/relate-attachment")
    public ResponseEntity<Object> relateAttachment(
            @Valid @RequestBody RelateAttachmentCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        attachmentCommandService.relateAttachment(command);

        return ResponseUtils.ok("关联单附件成功").build();
    }

    /**
     * 关联多附件
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/relate-attachments")
    public ResponseEntity<Object> relateAttachments(
            @Valid @RequestBody RelateAttachmentsCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        attachmentCommandService.relateAttachments(command);

        return ResponseUtils.ok("关联多附件成功").build();
    }

    /**
     * 修复图片压缩
     * @param repairThumbImageCommand
     */
    @PostMapping("repair-thumb-image")
    public List<AttachmentThumbUrlRelationDTO> repairThumbImage(
            @RequestBody RepairThumbImageCommand repairThumbImageCommand
    ) {
        return aliyunOssService.repairThumbImage(repairThumbImageCommand.getModuleId(), repairThumbImageCommand.getAttachmentIds());
    }



    /**
     * 获取单个附件信息
     *
     * @return
     */
    @PostMapping("/basic-attachments")
    public AttachmentBasicDTO getBasicAttachment(
            @RequestBody AttachmentDTO attachmentDTO
    ) {
        return localFileUploadService.createAttachment(attachmentDTO);
    }

    /**
     * 本地上传单个文件
     *
     * @param file 文件
     * @return
     */
    @PostMapping(value = "/oss/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String localUploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "save_path") String savePath,
            @RequestParam(value = "file_type") String fileType
    ) {
        return aliyunOssService.uploadOss(file, savePath, fileType);
    }
}