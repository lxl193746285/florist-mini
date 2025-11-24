package com.qy.crm.customer.app.infrastructure.persistence.mybatis;

import com.qy.crm.customer.app.application.query.CustomerQuery;
import com.qy.crm.customer.app.infrastructure.persistence.CustomerDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.CustomerMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 岗位数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class CustomerDataRepositoryImpl implements CustomerDataRepository {
    private CustomerMapper customerMapper;

    public CustomerDataRepositoryImpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<CustomerDO> findByQuery(CustomerQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CustomerDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(CustomerDO::getCreateTime);
        if (filterQuery == null) {
            queryWrapper.eq(CustomerDO::getId, 0);
        }
        else {
            queryWrapper.in(CustomerDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(CustomerDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getDepartmentId() != null, CustomerDO::getDepartmentId, permissionFilterQuery.getDepartmentId())
                                    .in(permissionFilterQuery.getDepartmentIds() != null, CustomerDO::getDepartmentId, permissionFilterQuery.getDepartmentIds())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, CustomerDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(CustomerDO::getName, query.getKeywords())
                    .or().like(CustomerDO::getNamePinyin, query.getKeywords())
                    .or().like(CustomerDO::getTel,query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(CustomerDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getDepartmentId() != null) {
            queryWrapper.eq(CustomerDO::getDepartmentId, query.getDepartmentId());
        }
        if (query.getLevelId() != null) {
            queryWrapper.eq(CustomerDO::getLevelId, query.getLevelId());
        }
        if (query.getSourceId() != null) {
            queryWrapper.eq(CustomerDO::getSourceId, query.getSourceId());
        }
        if (query.getStatusId() != null) {
            queryWrapper.eq(CustomerDO::getStatusId, query.getStatusId());
        }
        if (query.getIsOpenAccount() != null) {
            queryWrapper.eq(CustomerDO::getIsOpenAccount, query.getIsOpenAccount());
        }
        if (query.getCreateStartTime() != null) {
            queryWrapper.ge(CustomerDO::getCreateTime, LocalDateTime.of(LocalDate.parse(query.getCreateStartTime()), LocalTime.MIDNIGHT));
        }
        if (query.getCreateEndTime() != null) {
            queryWrapper.lt(CustomerDO::getCreateTime, LocalDateTime.of(LocalDate.parse(query.getCreateEndTime()), LocalTime.MIDNIGHT).plusDays(1));
        }

        IPage<CustomerDO> iPage = customerMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<CustomerDO> findByIds(List<Long> ids) {
        return customerMapper.selectBatchIds(ids);
    }

    @Override
    public CustomerDO findById(Long id) {
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CustomerDO::getId, id)
                .eq(CustomerDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return customerMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(CustomerDO customerDO) {
        if (customerDO.getId() == null) {
            customerMapper.insert(customerDO);
        }
        else {
            customerMapper.updateById(customerDO);
        }
        return customerDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity identity) {
        CustomerDO customerDO = findById(id);
        customerDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        customerDO.setDeletorId(identity != null ? identity.getId() : 0L);
        customerDO.setDeletorName(identity != null ? identity.getName() : "");
        customerDO.setDeleteTime(LocalDateTime.now());
        customerMapper.updateById(customerDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId) {
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CustomerDO::getOrganizationId, organizationId)
                .eq(CustomerDO::getName, name)
                .eq(CustomerDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(CustomerDO::getId, excludeId);
        }
        return customerMapper.selectCount(queryWrapper);
    }
}