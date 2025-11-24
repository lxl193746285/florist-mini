package com.qy.attachment.app.infrastructure.persistence.mybatis;

import com.qy.attachment.app.domain.entity.FileAttachment;
import com.qy.attachment.app.domain.entity.ImageAttachment;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.infrastructure.persistence.AttachmentDomainRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.converter.AttachmentConverter;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.mapper.AttachmentMapper;
import com.qy.attachment.app.infrastructure.persistence.mybatis.mapper.AttachmentRelationMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件领域资源库实现`
 *
 * @author legendjw
 */
@Repository
public class AttachmentDomainRepositoryImpl implements AttachmentDomainRepository {
    private AttachmentMapper attachmentMapper;
    private AttachmentRelationMapper attachmentRelationMapper;
    private AttachmentConverter attachmentConverter;

    public AttachmentDomainRepositoryImpl(AttachmentMapper attachmentMapper, AttachmentRelationMapper attachmentRelationMapper, AttachmentConverter attachmentConverter) {
        this.attachmentMapper = attachmentMapper;
        this.attachmentRelationMapper = attachmentRelationMapper;
        this.attachmentConverter = attachmentConverter;
    }

    @Override
    public AttachmentDO findById(AttachmentId id) {
        return attachmentMapper.selectById(id.getId());
    }

    @Override
    public List<AttachmentDO> findByIds(List<AttachmentId> ids) {
        return attachmentMapper.selectBatchIds(ids.stream().map(AttachmentId::getId).collect(Collectors.toList()));
    }

    @Override
    public AttachmentId saveFileAttachment(FileAttachment attachment) {
        AttachmentDO attachmentDO = attachmentConverter.toDO(attachment);
        attachmentDO.setIsImage((byte) 0);
        if (attachment.getId() == null) {
            attachmentDO.setCreateTime(LocalDateTime.now());
            attachmentMapper.insert(attachmentDO);
        }
        else {
            attachmentDO.setUpdateTime(LocalDateTime.now());
            attachmentMapper.updateById(attachmentDO);
        }
        return new AttachmentId(attachmentDO.getId());
    }

    @Override
    public AttachmentId saveImageAttachment(ImageAttachment attachment) {
        AttachmentDO attachmentDO = attachmentConverter.toDO(attachment);
        attachmentDO.setIsImage((byte) 1);
        if (attachment.getId() == null) {
            attachmentDO.setCreateTime(LocalDateTime.now());
            attachmentMapper.insert(attachmentDO);
        }
        else {
            attachmentDO.setUpdateTime(LocalDateTime.now());
            attachmentMapper.updateById(attachmentDO);
        }
        return new AttachmentId(attachmentDO.getId());
    }
}
