package com.qy.attachment.app.infrastructure.persistence;

import com.qy.attachment.app.domain.entity.ImageThumbAttachment;
import com.qy.attachment.app.domain.valueobject.ThumbAttachmentId;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbDO;

import java.util.List;

/**
 * 附件缩略图资源库
 *
 * @author legendjw
 */
public interface AttachmentThumbDataRepository {
    /**
     * 根据附件id获取缩略图
     *
     * @param attachmentId
     * @return
     */
    List<AttachmentThumbDO> findByAttachmentId(Long attachmentId);

    /**
     * 保存缩略图附件
     *
     * @param thumbAttachment
     * @return
     */
    ThumbAttachmentId save(ImageThumbAttachment thumbAttachment);
}
