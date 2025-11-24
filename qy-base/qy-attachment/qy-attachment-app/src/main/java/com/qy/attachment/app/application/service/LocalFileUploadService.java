package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.LocalUploadFilesCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.domain.valueobject.AttachmentId;

import java.util.List;

/**
 * 本地文件上传命令服务
 *
 * @author legendjw
 */
public interface LocalFileUploadService {
    /**
     * 上传单个文件
     *
     * @param command
     * @return
     */
    AttachmentId uploadFile(LocalUploadFileCommand command);

    /**
     * 上传多个文件
     *
     * @param command
     * @return
     */
    List<AttachmentId> uploadFiles(LocalUploadFilesCommand command);

    AttachmentBasicDTO createAttachment(AttachmentDTO attachmentDTO);
}
