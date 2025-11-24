package com.qy.crm.customer.app.infrastructure.persistence.mybatis;

import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.crm.customer.app.infrastructure.persistence.OpenBankDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.OpenBankRelationDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.OpenBankMapper;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.OpenBankRelationMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 开户行数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class OpenBankDataRepositoryImpl implements OpenBankDataRepository {
    private OpenBankMapper openBankMapper;
    private OpenBankRelationMapper openBankRelationMapper;

    public OpenBankDataRepositoryImpl(OpenBankMapper openBankMapper, OpenBankRelationMapper openBankRelationMapper) {
        this.openBankMapper = openBankMapper;
        this.openBankRelationMapper = openBankRelationMapper;
    }

    @Override
    public Page<OpenBankDO> findByQuery(OpenBankQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<OpenBankDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpenBankDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(OpenBankDO::getCreateTime);
        if (filterQuery == null) {
            queryWrapper.eq(OpenBankDO::getId, 0);
        }
        else {
            queryWrapper.in(OpenBankDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(OpenBankDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, OpenBankDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(OpenBankDO::getName, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(OpenBankDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getRelatedModuleId() != null && query.getRelatedDataId() != null) {
            List<Long> relatedOpenBankIds = getRelatedOpenBankIds(query.getRelatedModuleId(), query.getRelatedDataId());
            if (relatedOpenBankIds.isEmpty()) {
                queryWrapper.eq(OpenBankDO::getId, 0L);
            }
            else {
                queryWrapper.in(OpenBankDO::getId, relatedOpenBankIds);
            }
        }

        IPage<OpenBankDO> iPage = openBankMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<OpenBankDO> findByIds(List<Long> ids) {
        return openBankMapper.selectBatchIds(ids);
    }

    @Override
    public OpenBankDO findById(Long id) {
        LambdaQueryWrapper<OpenBankDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpenBankDO::getId, id)
                .eq(OpenBankDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return openBankMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(OpenBankDO openBankDO) {
        if (openBankDO.getId() == null) {
            openBankMapper.insert(openBankDO);
        }
        else {
            openBankMapper.updateById(openBankDO);
        }
        return openBankDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity identity) {
        OpenBankDO openBankDO = findById(id);
        openBankDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        openBankDO.setDeletorId(identity != null ? identity.getId() : 0L);
        openBankDO.setDeletorName(identity != null ? identity.getName() : "");
        openBankDO.setDeleteTime(LocalDateTime.now());
        openBankMapper.updateById(openBankDO);
    }

    @Override
    public List<Long> getRelatedOpenBankIds(String relatedModuleId, Long relatedDataId) {
        LambdaQueryWrapper<OpenBankRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(OpenBankRelationDO::getOpenBankId)
                .eq(OpenBankRelationDO::getModuleId, relatedModuleId)
                .eq(OpenBankRelationDO::getDataId, relatedDataId);
        List<OpenBankRelationDO> relationDOS = openBankRelationMapper.selectList(queryWrapper);
        return relationDOS.stream().map(OpenBankRelationDO::getOpenBankId).collect(Collectors.toList());
    }

    @Override
    public Long saveRelation(String relatedModuleId, Long relatedDataId, Long openBankId) {
        OpenBankRelationDO relationDO = new OpenBankRelationDO();
        relationDO.setOpenBankId(openBankId);
        relationDO.setModuleId(relatedModuleId);
        relationDO.setDataId(relatedDataId);
        relationDO.setCreateTime(LocalDateTime.now());
        openBankRelationMapper.insert(relationDO);

        return relationDO.getId();
    }

    @Override
    public int removeRelation(Long openBankId) {
        LambdaQueryWrapper<OpenBankRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpenBankRelationDO::getOpenBankId, openBankId);
        return openBankRelationMapper.delete(queryWrapper);
    }
}