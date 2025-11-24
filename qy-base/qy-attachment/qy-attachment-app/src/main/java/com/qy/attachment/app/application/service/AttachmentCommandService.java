package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.command.RelateAttachmentCommand;
import com.qy.attachment.app.application.command.RelateAttachmentsCommand;

/**
 * 附件命令服务
 *
 * @author legendjw
 */
public interface AttachmentCommandService {
    /**
     * 关联单附件
     *
     * @param command
     */
    void relateAttachment(RelateAttachmentCommand command);

    /**
     * 关联多附件
     *
     * @param command
     */
    void relateAttachments(RelateAttachmentsCommand command);
}
