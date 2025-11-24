package com.qy.attachment.app.infrastructure.persistence.mybatis;

import com.qy.attachment.app.infrastructure.persistence.AttachmentRelationDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentRelationDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.mapper.AttachmentRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 附件关联数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AttachmentRelationDataRepositoryImpl implements AttachmentRelationDataRepository {
    private AttachmentRelationMapper attachmentRelationMapper;

    public AttachmentRelationDataRepositoryImpl(AttachmentRelationMapper attachmentRelationMapper) {
        this.attachmentRelationMapper = attachmentRelationMapper;
    }

    @Override
    public AttachmentRelationDO getAttachmentRelation(String moduleId, Long dataId, String type) {
        LambdaQueryWrapper<AttachmentRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentRelationDO::getModuleId, moduleId)
                .eq(AttachmentRelationDO::getDataId, dataId)
                .eq(AttachmentRelationDO::getType, type)
                .last("limit 1");
        return attachmentRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<AttachmentRelationDO> getAttachmentRelations(String moduleId, Long dataId, String type) {
        LambdaQueryWrapper<AttachmentRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentRelationDO::getModuleId, moduleId)
                .eq(AttachmentRelationDO::getDataId, dataId)
                .eq(AttachmentRelationDO::getType, type);
        return attachmentRelationMapper.selectList(queryWrapper);
    }

    @Override
    public void createAttachmentRelation(List<AttachmentRelationDO> attachmentRelationDOS) {
        LocalDateTime now = LocalDateTime.now();
        int i = 0;
        for (AttachmentRelationDO attachmentRelationDO : attachmentRelationDOS) {
            attachmentRelationDO.setSort(i);
            attachmentRelationDO.setCreateTime(now);
            attachmentRelationMapper.insert(attachmentRelationDO);
            i++;
        }
    }

    @Override
    public void deleteAttachmentRelation(String moduleId, Long dataId, String type) {
        LambdaQueryWrapper<AttachmentRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentRelationDO::getModuleId, moduleId)
                .eq(AttachmentRelationDO::getDataId, dataId)
                .eq(AttachmentRelationDO::getType, type);
        attachmentRelationMapper.delete(queryWrapper);
    }
}
