package com.qy.attachment.app.infrastructure.persistence;

import com.qy.attachment.app.domain.entity.FileAttachment;
import com.qy.attachment.app.domain.entity.ImageAttachment;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;

import java.util.List;

/**
 * 附件领域资源库
 *
 * @author legendjw
 */
public interface AttachmentDomainRepository {
    /**
     * 根据id获取附件信息
     *
     * @param id
     * @return
     */
    AttachmentDO findById(AttachmentId id);

    /**
     * 根据id集合获取附件
     *
     * @param ids
     * @return
     */
    List<AttachmentDO> findByIds(List<AttachmentId> ids);

    /**
     * 保存文件附件
     *
     * @param attachment
     * @return
     */
    AttachmentId saveFileAttachment(FileAttachment attachment);

    /**
     * 保存图片附件
     *
     * @param attachment
     * @return
     */
    AttachmentId saveImageAttachment(ImageAttachment attachment);
}
