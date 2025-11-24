package com.qy.crm.customer.app.infrastructure.persistence.mybatis;

import com.qy.crm.customer.app.application.query.BusinessLicenseQuery;
import com.qy.crm.customer.app.infrastructure.persistence.BusinessLicenseDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseRelationDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.BusinessLicenseMapper;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.BusinessLicenseRelationMapper;
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
 * 营业执照数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class BusinessLicenseRepositoryImpl implements BusinessLicenseDataRepository {
    private BusinessLicenseMapper businessLicenseMapper;
    private BusinessLicenseRelationMapper businessLicenseRelationMapper;

    public BusinessLicenseRepositoryImpl(BusinessLicenseMapper businessLicenseMapper, BusinessLicenseRelationMapper businessLicenseRelationMapper) {
        this.businessLicenseMapper = businessLicenseMapper;
        this.businessLicenseRelationMapper = businessLicenseRelationMapper;
    }

    @Override
    public Page<BusinessLicenseDO> findByQuery(BusinessLicenseQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<BusinessLicenseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(BusinessLicenseDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(BusinessLicenseDO::getCreateTime);
        if (filterQuery == null) {
            queryWrapper.eq(BusinessLicenseDO::getId, 0);
        }
        else {
            queryWrapper.in(BusinessLicenseDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(BusinessLicenseDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, BusinessLicenseDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(BusinessLicenseDO::getName, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(BusinessLicenseDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getRelatedModuleId() != null && query.getRelatedDataId() != null) {
            List<Long> relatedBusinessLicenseIds = getRelatedBusinessLicenseIds(query.getRelatedModuleId(), query.getRelatedDataId());
            if (relatedBusinessLicenseIds.isEmpty()) {
                queryWrapper.eq(BusinessLicenseDO::getId, 0L);
            }
            else {
                queryWrapper.in(BusinessLicenseDO::getId, relatedBusinessLicenseIds);
            }
        }

        IPage<BusinessLicenseDO> iPage = businessLicenseMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<BusinessLicenseDO> findByIds(List<Long> ids) {
        return businessLicenseMapper.selectBatchIds(ids);
    }

    @Override
    public BusinessLicenseDO findById(Long id) {
        LambdaQueryWrapper<BusinessLicenseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(BusinessLicenseDO::getId, id)
                .eq(BusinessLicenseDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return businessLicenseMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(BusinessLicenseDO businessLicenseDO) {
        if (businessLicenseDO.getId() == null) {
            businessLicenseMapper.insert(businessLicenseDO);
        }
        else {
            businessLicenseMapper.updateById(businessLicenseDO);
        }
        return businessLicenseDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity identity) {
        BusinessLicenseDO businessLicenseDO = findById(id);
        businessLicenseDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        businessLicenseDO.setDeletorId(identity != null ? identity.getId() : 0L);
        businessLicenseDO.setDeletorName(identity != null ? identity.getName() : "");
        businessLicenseDO.setDeleteTime(LocalDateTime.now());
        businessLicenseMapper.updateById(businessLicenseDO);
    }

    @Override
    public List<Long> getRelatedBusinessLicenseIds(String relatedModuleId, Long relatedDataId) {
        LambdaQueryWrapper<BusinessLicenseRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(BusinessLicenseRelationDO::getBusinessLicenseId)
                .eq(BusinessLicenseRelationDO::getModuleId, relatedModuleId)
                .eq(BusinessLicenseRelationDO::getDataId, relatedDataId);
        List<BusinessLicenseRelationDO> relationDOS = businessLicenseRelationMapper.selectList(queryWrapper);
        return relationDOS.stream().map(BusinessLicenseRelationDO::getBusinessLicenseId).collect(Collectors.toList());
    }

    @Override
    public Long saveRelation(String relatedModuleId, Long relatedDataId, Long businessLicenseId) {
        BusinessLicenseRelationDO relationDO = new BusinessLicenseRelationDO();
        relationDO.setBusinessLicenseId(businessLicenseId);
        relationDO.setModuleId(relatedModuleId);
        relationDO.setDataId(relatedDataId);
        relationDO.setCreateTime(LocalDateTime.now());
        businessLicenseRelationMapper.insert(relationDO);

        return relationDO.getId();
    }

    @Override
    public int removeRelation(Long businessLicenseId) {
        LambdaQueryWrapper<BusinessLicenseRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessLicenseRelationDO::getBusinessLicenseId, businessLicenseId);
        return businessLicenseRelationMapper.delete(queryWrapper);
    }
}