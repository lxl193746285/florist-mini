package com.qy.base.interfaces.attachment.api;

import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.LocalUploadFilesCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.LocalFileUploadService;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 本地附件
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/attachment/local")
public class LocalFileApiController {
    private OrganizationSessionContext sessionContext;
    private AttachmentQueryService attachmentQueryService;
    private LocalFileUploadService localFileUploadService;

    public LocalFileApiController(OrganizationSessionContext sessionContext, AttachmentQueryService attachmentQueryService, LocalFileUploadService localFileUploadService) {
        this.sessionContext = sessionContext;
        this.attachmentQueryService = attachmentQueryService;
        this.localFileUploadService = localFileUploadService;
    }

    /**
     * 本地上传单个文件
     *
     * @param file 文件
     * @param organizationId 组织id
     * @param moduleId 模块id
     * @return
     */
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentBasicDTO> localUploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "module_id", required = false) String moduleId,
            @RequestParam(value = "uid", required = false) String uid
    ) {
        LocalUploadFileCommand command = new LocalUploadFileCommand();
        command.setFile(file);
        command.setOrganizationId(organizationId);
        command.setModuleId(moduleId);
        AttachmentId attachmentId = localFileUploadService.uploadFile(command);
        AttachmentBasicDTO attachmentBasicDTO = attachmentQueryService.getBasicAttachmentById(attachmentId);
        attachmentBasicDTO.setUid(uid);
        return ResponseUtils.ok("上传文件成功").body(attachmentBasicDTO);
    }

}
