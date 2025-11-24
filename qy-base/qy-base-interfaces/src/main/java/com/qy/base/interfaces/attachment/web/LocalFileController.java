package com.qy.base.interfaces.attachment.web;

import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.LocalUploadFilesCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.LocalFileUploadService;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.organization.app.application.service.AvatarService;
import com.qy.organization.app.domain.valueobject.Avatar;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * 本地附件
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/attachment/local")
public class LocalFileController {
    private OrganizationSessionContext sessionContext;
    private AttachmentQueryService attachmentQueryService;
    private LocalFileUploadService localFileUploadService;
    private AvatarService avatarService;

    public LocalFileController(OrganizationSessionContext sessionContext, AttachmentQueryService attachmentQueryService,
                               LocalFileUploadService localFileUploadService,AvatarService avatarService) {
        this.sessionContext = sessionContext;
        this.attachmentQueryService = attachmentQueryService;
        this.localFileUploadService = localFileUploadService;
        this.avatarService = avatarService;
    }

    /**
     * 本地上传单个文件
     *
     * @param file 文件
     * @param organizationId 组织id
     * @param moduleId 模块id
     * @return
     */
    @PostMapping(value = "/upload-file")
    public ResponseEntity<AttachmentBasicDTO> localUploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "module_id", required = false) String moduleId,
            @RequestParam(value = "uid", required = false) String uid
    ) {
        LocalUploadFileCommand command = new LocalUploadFileCommand();
        command.setFile(file);
        command.setOrganizationId(organizationId);
        command.setModuleId(moduleId);
        command.setIdentityId(sessionContext.getUser().getId());
        command.setIdentityName(sessionContext.getUser().getName());
        AttachmentId attachmentId = localFileUploadService.uploadFile(command);
        AttachmentBasicDTO attachmentBasicDTO = attachmentQueryService.getBasicAttachmentById(attachmentId);
        attachmentBasicDTO.setUid(uid);
        return ResponseUtils.ok("上传文件成功").body(attachmentBasicDTO);
    }

    /**
     * 本地上传多个文件
     *
     * @param files 文件
     * @param organizationId 组织id
     * @param moduleId 模块id
     * @return
     */
    @PostMapping("/upload-files")
    public ResponseEntity<List<AttachmentBasicDTO>> localUploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "module_id", required = false) String moduleId
    ) {
        LocalUploadFilesCommand command = new LocalUploadFilesCommand();
        command.setFiles(files);
        command.setOrganizationId(organizationId);
        command.setModuleId(moduleId);
        command.setIdentityId(sessionContext.getUser().getId());
        command.setIdentityName(sessionContext.getUser().getName());
        List<AttachmentId> attachmentIds = localFileUploadService.uploadFiles(command);
        return ResponseUtils.ok("上传文件成功").body(attachmentQueryService.getBasicAttachmentByIds(attachmentIds));
    }

    /**
     * 公开本地上传单个文件（不做登录验证）
     *
     * @param file 文件
     * @param organizationId 组织id
     * @param moduleId 模块id
     * @return
     */
    @PostMapping(value = "/public/upload-file")
    public ResponseEntity<AttachmentBasicDTO> publicLocalUploadFile(
            @RequestParam("file") MultipartFile file,
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

    /**
     * 公开本地上传多个文件(不做登录验证)
     *
     * @param files 文件
     * @param organizationId 组织id
     * @param moduleId 模块id
     * @return
     */
    @PostMapping("/public/upload-files")
    public ResponseEntity<List<AttachmentBasicDTO>> publicLocalUploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "organization_id", required = false) Long organizationId,
            @RequestParam(value = "module_id", required = false) String moduleId
    ) {
        LocalUploadFilesCommand command = new LocalUploadFilesCommand();
        command.setFiles(files);
        command.setOrganizationId(organizationId);
        command.setModuleId(moduleId);
        List<AttachmentId> attachmentIds = localFileUploadService.uploadFiles(command);
        return ResponseUtils.ok("上传文件成功").body(attachmentQueryService.getBasicAttachmentByIds(attachmentIds));
    }

    @GetMapping("/avatar")
    public ResponseEntity<Avatar> getAvatar(
            @RequestParam(value = "name") String name
    ) {
        System.out.println("name:" + name);
        return ResponseUtils.ok("获取头像成功").body(avatarService.generateAvatarByName(name, UUID.randomUUID().toString()));
    }
}
