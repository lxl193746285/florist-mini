package com.qy.attachment.app.infrastructure.persistence.mybatis;

import com.qy.attachment.app.domain.entity.ImageThumbAttachment;
import com.qy.attachment.app.domain.valueobject.ThumbAttachmentId;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.converter.AttachmentThumbConverter;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.mapper.AttachmentThumbMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 附件缩略图资源库实现
 *
 * @author legendjw
 */
@Repository
public class AttachmentThumbDataRepositoryImpl implements AttachmentThumbDataRepository {
    private AttachmentThumbConverter attachmentThumbConverter;
    private AttachmentThumbMapper attachmentThumbMapper;

    public AttachmentThumbDataRepositoryImpl(AttachmentThumbConverter attachmentThumbConverter, AttachmentThumbMapper attachmentThumbMapper) {
        this.attachmentThumbConverter = attachmentThumbConverter;
        this.attachmentThumbMapper = attachmentThumbMapper;
    }

    @Override
    public List<AttachmentThumbDO> findByAttachmentId(Long attachmentId) {
        LambdaQueryWrapper<AttachmentThumbDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(AttachmentThumbDO::getAttachmentId, attachmentId)
                .orderByAsc(AttachmentThumbDO::getId);
        return attachmentThumbMapper.selectList(queryWrapper);
    }

    @Override
    public ThumbAttachmentId save(ImageThumbAttachment thumbAttachment) {
        AttachmentThumbDO attachmentThumbDO = attachmentThumbConverter.toDO(thumbAttachment);
        if (thumbAttachment.getId() == null) {
            attachmentThumbDO.setCreateTime(LocalDateTime.now());
            attachmentThumbMapper.insert(attachmentThumbDO);
        }
        else {
            attachmentThumbDO.setUpdateTime(LocalDateTime.now());
            attachmentThumbMapper.updateById(attachmentThumbDO);
        }
        return new ThumbAttachmentId(attachmentThumbDO.getId());
    }
}
