package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.command.CreateThumbConfigCommand;
import com.qy.attachment.app.application.command.DeleteThumbConfigCommand;
import com.qy.attachment.app.application.command.UpdateThumbConfigCommand;

/**
 * 缩略图配置命令服务
 *
 * @author legendjw
 */
public interface ThumbConfigCommandService {
    /**
     * 创建缩略图配置命令
     *
     * @param command
     * @return
     */
    Long createAttachmentThumbConfig(CreateThumbConfigCommand command);

    /**
     * 编辑缩略图配置命令
     *
     * @param command
     */
    void updateAttachmentThumbConfig(UpdateThumbConfigCommand command);

    /**
     * 删除缩略图配置命令
     *
     * @param command
     */
    void deleteAttachmentThumbConfig(DeleteThumbConfigCommand command);
}
