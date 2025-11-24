package com.qy.base.interfaces.attachment.web;

import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.LocalUploadFilesCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.LocalFileUploadService;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 会员系统本地附件
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/attachment/local/member-system")
public class MemberSystemLocalFileController {
    private MemberSystemSessionContext sessionContext;
    private AttachmentQueryService attachmentQueryService;
    private LocalFileUploadService localFileUploadService;

    public MemberSystemLocalFileController(MemberSystemSessionContext sessionContext, AttachmentQueryService attachmentQueryService, LocalFileUploadService localFileUploadService) {
        this.sessionContext = sessionContext;
        this.attachmentQueryService = attachmentQueryService;
        this.localFileUploadService = localFileUploadService;
    }

    /**
     * 会员系统本地上传单个文件
     *
     * @param file 文件
     * @param moduleId 模块id
     * @return
     */
    @PostMapping(value = "/upload-file")
    public ResponseEntity<AttachmentBasicDTO> localUploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "module_id", required = false) String moduleId
    ) {
        MemberIdentity identity = sessionContext.getMember();
        LocalUploadFileCommand command = new LocalUploadFileCommand();
        command.setFile(file);
        command.setOrganizationId(identity.getOrganizationId());
        command.setModuleId(moduleId);
        command.setIdentityId(identity.getAccountId());
        command.setIdentityName(identity.getAccountName());
        AttachmentId attachmentId = localFileUploadService.uploadFile(command);
        return ResponseUtils.ok("上传文件成功").body(attachmentQueryService.getBasicAttachmentById(attachmentId));
    }

    /**
     * 会员系统本地上传多个文件
     *
     * @param files 文件
     * @param moduleId 模块id
     * @return
     */
    @PostMapping("/upload-files")
    public ResponseEntity<List<AttachmentBasicDTO>> localUploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "module_id", required = false) String moduleId
    ) {
        MemberIdentity identity = sessionContext.getMember();
        LocalUploadFilesCommand command = new LocalUploadFilesCommand();
        command.setFiles(files);
        command.setOrganizationId(identity.getOrganizationId());
        command.setModuleId(moduleId);
        command.setIdentityId(identity.getAccountId());
        command.setIdentityName(identity.getAccountName());
        List<AttachmentId> attachmentIds = localFileUploadService.uploadFiles(command);
        return ResponseUtils.ok("上传文件成功").body(attachmentQueryService.getBasicAttachmentByIds(attachmentIds));
    }
}