package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.domain.entity.Organization;
import com.qy.organization.app.domain.valueobject.OrganizationId;
import com.qy.organization.app.infrastructure.persistence.OrganizationDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.converter.OrganizationConverter;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.OrganizationMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 组织资源库实现
 *
 * @author legendjw
 */
@Repository
public class OrganizationDomainRepositoryImpl implements OrganizationDomainRepository {
    private OrganizationMapper organizationMapper;
    private OrganizationConverter organizationConverter;

    public OrganizationDomainRepositoryImpl(OrganizationMapper organizationMapper, OrganizationConverter organizationConverter) {
        this.organizationMapper = organizationMapper;
        this.organizationConverter = organizationConverter;
    }

    @Override
    public Organization findById(OrganizationId id) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getId, id.getId())
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        OrganizationDO organizationDO = organizationMapper.selectOne(queryWrapper);
        return organizationDO == null ? null : organizationConverter.toEntity(organizationDO);
    }

    @Override
    public OrganizationId save(Organization organization) {
        OrganizationDO organizationDO = organizationConverter.toDO(organization);
        if (organization.getId() == null) {
            organizationMapper.insert(organizationDO);
        }
        else {
            organizationMapper.updateById(organizationDO);
        }
        return new OrganizationId(organizationDO.getId());
    }

    @Override
    public void remove(OrganizationId id, Identity identity) {
        OrganizationDO organizationDO = organizationMapper.selectById(id.getId());
        organizationDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        organizationDO.setDeletorId(identity.getId());
        organizationDO.setDeletorName(identity.getName());
        organizationDO.setDeleteTime(LocalDateTime.now());
        organizationMapper.updateById(organizationDO);
    }

    @Override
    public int countByCreatorAndName(Long creatorId, String name, Long excludeId) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getCreatorId, creatorId)
                .eq(OrganizationDO::getName, name)
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(OrganizationDO::getId, excludeId);
        }
        return organizationMapper.selectCount(queryWrapper);
    }
}
