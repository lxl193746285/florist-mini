package com.qy.member.app.domain.service;

import com.qy.attachment.api.dto.AttachmentDTO;
import com.qy.member.app.domain.valueobject.Avatar;

/**
 * 头像领域服务
 *
 * @author legendjw
 */
public interface AvatarService {
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

    /**
     * 使用网络图片生成指定账号的头像
     *
     * @param imageUrl
     * @param fileName
     * @return
     */
    Avatar generateAvatarByImageUrl(String imageUrl, String fileName);
}
