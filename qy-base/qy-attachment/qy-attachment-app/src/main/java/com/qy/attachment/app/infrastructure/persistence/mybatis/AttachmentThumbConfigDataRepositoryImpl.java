package com.qy.attachment.app.infrastructure.persistence.mybatis;

import com.qy.attachment.app.application.query.ThumbConfigQuery;
import com.qy.attachment.app.domain.enums.ThumbConfigType;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbConfigDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.mapper.AttachmentThumbConfigMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 附件缩略图配置资源库实现
 *
 * @author legendjw
 */
@Repository
public class AttachmentThumbConfigDataRepositoryImpl implements AttachmentThumbConfigDataRepository {
    private AttachmentThumbConfigMapper attachmentThumbConfigMapper;

    public AttachmentThumbConfigDataRepositoryImpl(AttachmentThumbConfigMapper attachmentThumbConfigMapper) {
        this.attachmentThumbConfigMapper = attachmentThumbConfigMapper;
    }

    @Override
    public List<AttachmentThumbConfigDO> findByModule(String moduleId) {
        LambdaQueryWrapper<AttachmentThumbConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(AttachmentThumbConfigDO::getId);
        if (StringUtils.isBlank(moduleId)) {
            queryWrapper.eq(AttachmentThumbConfigDO::getTypeId, ThumbConfigType.SYSTEM.getId());
        }
        else {
            queryWrapper.and(i -> i
                    .and(j -> j.eq(AttachmentThumbConfigDO::getTypeId, ThumbConfigType.MODULE.getId()).eq(AttachmentThumbConfigDO::getModuleId, moduleId))
                    .or().eq(AttachmentThumbConfigDO::getTypeId, ThumbConfigType.SYSTEM.getId())
            );
        }
        return attachmentThumbConfigMapper.selectList(queryWrapper);
    }

    @Override
    public AttachmentThumbConfigDO findByModuleAndScenario(String moduleId, String scenario) {
        LambdaQueryWrapper<AttachmentThumbConfigDO> systemQueryWrapper = new LambdaQueryWrapper<>();
        systemQueryWrapper
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(AttachmentThumbConfigDO::getTypeId, ThumbConfigType.SYSTEM.getId())
                .last("limit 1");
        AttachmentThumbConfigDO systemThumbConfigDO = attachmentThumbConfigMapper.selectOne(systemQueryWrapper);

        LambdaQueryWrapper<AttachmentThumbConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(AttachmentThumbConfigDO::getModuleId, moduleId)
                .last("limit 1");
        if (StringUtils.isNotBlank(scenario)) {
            queryWrapper.eq(AttachmentThumbConfigDO::getScenario, scenario);
        }
        AttachmentThumbConfigDO thumbConfigDO = attachmentThumbConfigMapper.selectOne(queryWrapper);

        return thumbConfigDO == null ? systemThumbConfigDO : thumbConfigDO;
    }

    @Override
    public Page<AttachmentThumbConfigDO> findByQuery(ThumbConfigQuery query) {
        LambdaQueryWrapper<AttachmentThumbConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(AttachmentThumbConfigDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(AttachmentThumbConfigDO::getName, query.getKeywords()));
        }

        IPage<AttachmentThumbConfigDO> iPage = attachmentThumbConfigMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<AttachmentThumbConfigDO> findByIds(List<Long> ids) {
        return attachmentThumbConfigMapper.selectBatchIds(ids);
    }

    @Override
    public AttachmentThumbConfigDO findById(Long id) {
        LambdaQueryWrapper<AttachmentThumbConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbConfigDO::getId, id)
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return attachmentThumbConfigMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(AttachmentThumbConfigDO attachmentThumbConfigDO) {
        if (attachmentThumbConfigDO.getId() == null) {
            attachmentThumbConfigMapper.insert(attachmentThumbConfigDO);
        }
        else {
            attachmentThumbConfigMapper.updateById(attachmentThumbConfigDO);
        }
        return attachmentThumbConfigDO.getId();
    }

    @Override
    public void remove(Long id, Identity identity) {
        AttachmentThumbConfigDO attachmentThumbConfigDO = findById(id);
        attachmentThumbConfigDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        attachmentThumbConfigDO.setDeletorId(identity.getId());
        attachmentThumbConfigDO.setDeletorName(identity.getName());
        attachmentThumbConfigDO.setDeleteTime(LocalDateTime.now());
        attachmentThumbConfigMapper.updateById(attachmentThumbConfigDO);
    }

    @Override
    public int countByTypeIdAndModuleIdAndScenario(Integer typeId, String moduleId, String scenario, Long excludeId) {
        LambdaQueryWrapper<AttachmentThumbConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(AttachmentThumbConfigDO::getTypeId, typeId)
                .eq(AttachmentThumbConfigDO::getModuleId, moduleId == null ? "" : moduleId)
                .eq(AttachmentThumbConfigDO::getScenario, scenario == null ? "" : scenario)
                .eq(AttachmentThumbConfigDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(AttachmentThumbConfigDO::getId, excludeId);
        }
        return attachmentThumbConfigMapper.selectCount(queryWrapper);
    }
}
