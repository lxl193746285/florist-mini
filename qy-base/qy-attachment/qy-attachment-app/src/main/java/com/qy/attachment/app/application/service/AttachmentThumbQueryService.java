package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.dto.AttachmentThumbBasicDTO;

import java.util.List;

/**
 * 附件缩略图查询服务
 *
 * @author legendjw
 */
public interface AttachmentThumbQueryService {
    /**
     * 获取指定附件的缩略图
     *
     * @param attachmentId
     * @return
     */
    List<AttachmentThumbBasicDTO> getAttachmentThumbsByAttachmentId(Long attachmentId);
}
