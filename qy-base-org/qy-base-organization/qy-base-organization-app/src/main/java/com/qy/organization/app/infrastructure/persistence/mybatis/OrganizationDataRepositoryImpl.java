package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.dto.EmployeeBasicDTO;
import com.qy.organization.app.application.query.OrganizationQuery;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.OrganizationMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class OrganizationDataRepositoryImpl implements OrganizationDataRepository {
    private OrganizationMapper organizationMapper;
    private EmployeeClient employeeClient;

    public OrganizationDataRepositoryImpl(OrganizationMapper organizationMapper, EmployeeClient employeeClient) {
        this.organizationMapper = organizationMapper;
        this.employeeClient = employeeClient;
    }

    @Override
    public Page<OrganizationDO> findPageByQuery(OrganizationQuery query) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(OrganizationDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(OrganizationDO::getName, query.getKeywords()).or().like(OrganizationDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getCreatorId() != null) {
            queryWrapper.eq(OrganizationDO::getCreatorId, query.getCreatorId());
        }
        if (query.getJoinId() != null) {
            List<OrganizationDO> joinOrganizations = findUserJoinOrganizations(query.getJoinId());
            List<Long> joinOrganizationIds = joinOrganizations.stream().map(OrganizationDO::getId).collect(Collectors.toList());
            if (joinOrganizationIds.isEmpty()) { joinOrganizationIds.add(0L); }
            queryWrapper.and(i -> i.in(OrganizationDO::getId, joinOrganizationIds).or().in(OrganizationDO::getParentId, joinOrganizationIds));
//            queryWrapper.in(OrganizationDO::getId, joinOrganizationIds);
        }
        IPage<OrganizationDO> iPage = organizationMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<OrganizationDO> findAllOrganizations() {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(OrganizationDO::getCreateTime);
        return organizationMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrganizationDO> findUserJoinOrganizations(Long userId) {
        List<EmployeeBasicDTO> employees = employeeClient.getUserJoinOrganizationBasicEmployees(userId);
        List<Long> organizationIds = employees.stream().map(EmployeeBasicDTO::getOrganizationId).collect(Collectors.toList());
        if (organizationIds.isEmpty()) {
            return new ArrayList<>();
        }

        return findByIds(organizationIds);
    }

    @Override
    public List<OrganizationDO> findByIds(List<Long> ids) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(OrganizationDO::getId, ids)
                .orderByAsc(OrganizationDO::getCreateTime);
        return organizationMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrganizationDO> findByIdsAndKeywords(List<Long> ids, String keywords) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(OrganizationDO::getId, ids)
                .orderByAsc(OrganizationDO::getCreateTime);
        if (StringUtils.isNotBlank(keywords)) {
            queryWrapper.and(i -> i.like(OrganizationDO::getName, keywords).or().like(OrganizationDO::getShortName, keywords));
        }

        return organizationMapper.selectList(queryWrapper);
    }

    @Override
    public OrganizationDO findById(Long id) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getId, id)
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return organizationMapper.selectOne(queryWrapper);
    }

    @Override
    public OrganizationDO findBySource(String source, Long sourceDataId) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getSource, source)
                .eq(OrganizationDO::getSourceDataId, sourceDataId)
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return organizationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<OrganizationDO> findByParentId(Long parentId) {
        LambdaQueryWrapper<OrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrganizationDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(OrganizationDO::getParentId, parentId)
                .orderByAsc(OrganizationDO::getCreateTime);
        return organizationMapper.selectList(queryWrapper);
    }
}
