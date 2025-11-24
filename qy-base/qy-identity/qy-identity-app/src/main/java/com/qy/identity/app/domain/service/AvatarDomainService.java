package com.qy.identity.app.domain.service;

import com.qy.attachment.api.dto.AttachmentDTO;
import com.qy.identity.app.domain.valueobject.Avatar;

/**
 * 头像领域服务
 *
 * @author legendjw
 */
public interface AvatarDomainService {
    /**
     * 使用昵称生成头像
     *
     * @param name
     * @param fileName
     * @return
     */
    Avatar generateAvatarByName(String name, String fileName);

    /**
     * 使用附件生成指定账号的头像
     *
     * @param attachment
     * @param fileName
     * @return
     */
    Avatar generateAvatarByAttachment(AttachmentDTO attachment, String fileName);
}
