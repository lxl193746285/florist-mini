package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.application.query.DepartmentQuery;
import com.qy.organization.app.infrastructure.persistence.DepartmentDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.DepartmentMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class DepartmentDataRepositoryImpl implements DepartmentDataRepository {
    private DepartmentMapper departmentMapper;

    public DepartmentDataRepositoryImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<DepartmentDO> findByQuery(DepartmentQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(DepartmentDO::getId, DepartmentDO::getParentId)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);

        if (filterQuery == null) {
            queryWrapper.eq(DepartmentDO::getId, 0);
        }
        else {
            queryWrapper.in(DepartmentDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(DepartmentDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getDepartmentId() != null, DepartmentDO::getId, permissionFilterQuery.getDepartmentId())
                                    .in(permissionFilterQuery.getDepartmentIds() != null, DepartmentDO::getId, permissionFilterQuery.getDepartmentIds())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, DepartmentDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (query.getOrganizationId() != null) {
            queryWrapper.eq(DepartmentDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getOrganizationIds() != null) {
            queryWrapper.in(DepartmentDO::getOrganizationId, query.getOrganizationIds());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(DepartmentDO::getName, query.getKeywords()).or().like(DepartmentDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getCreatorId() != null) {
            queryWrapper.eq(DepartmentDO::getCreatorId, query.getCreatorId());
        }
        if (query.getCreatorIds() != null) {
            queryWrapper.in(DepartmentDO::getCreatorId, query.getCreatorIds());
        }
        //查询包含所有父级菜单
        List<Map<String, Object>> departments = departmentMapper.selectMaps(queryWrapper);
        List<Long> allIds = new ArrayList<>();
        List<Long> parentIds = new ArrayList<>();
        for (Map<String, Object> department : departments) {
            Long id = ((BigInteger) department.get("id")).longValue();
            Long parentId = ((BigInteger) department.get("parent_id")).longValue();
            allIds.add(id);
            parentIds.add(parentId);
        }
        for (Long parentId : parentIds) {
            recursionLoadParentDepartmentIds(parentId, allIds);
        }
        if (allIds.isEmpty()) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<DepartmentDO> allQueryWrapper = new LambdaQueryWrapper<>();
        allQueryWrapper
                .in(DepartmentDO::getId, allIds)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(DepartmentDO::getSort)
                .orderByAsc(DepartmentDO::getCreateTime);
        return departmentMapper.selectList(allQueryWrapper);
    }

    @Override
    public List<DepartmentDO> findByQuery(DepartmentQuery query) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(DepartmentDO::getId, DepartmentDO::getParentId)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);

        if (query.getOrganizationId() != null) {
            queryWrapper.eq(DepartmentDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getOrganizationIds() != null) {
            queryWrapper.in(DepartmentDO::getOrganizationId, query.getOrganizationIds());
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(DepartmentDO::getName, query.getKeywords()).or().like(DepartmentDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getParentId() != null) {
            queryWrapper.eq(DepartmentDO::getParentId, query.getParentId());
        }
        if (query.getCreatorId() != null) {
            queryWrapper.eq(DepartmentDO::getCreatorId, query.getCreatorId());
        }
        if (query.getCreatorIds() != null) {
            queryWrapper.in(DepartmentDO::getCreatorId, query.getCreatorIds());
        }
        //查询包含所有父级菜单
        List<Map<String, Object>> departments = departmentMapper.selectMaps(queryWrapper);
        List<Long> allIds = new ArrayList<>();
        List<Long> parentIds = new ArrayList<>();
        for (Map<String, Object> department : departments) {
            Long id = ((BigInteger) department.get("id")).longValue();
            Long parentId = ((BigInteger) department.get("parent_id")).longValue();
            allIds.add(id);
            parentIds.add(parentId);
        }
        for (Long parentId : parentIds) {
            recursionLoadParentDepartmentIds(parentId, allIds);
        }
        if (allIds.isEmpty()) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<DepartmentDO> allQueryWrapper = new LambdaQueryWrapper<>();
        allQueryWrapper
                .in(DepartmentDO::getId, allIds)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(DepartmentDO::getSort)
                .orderByAsc(DepartmentDO::getCreateTime);
        return departmentMapper.selectList(allQueryWrapper);
    }

    @Override
    public List<DepartmentDO> findChildren(Long parentId) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DepartmentDO::getParentId, parentId)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByAsc(DepartmentDO::getSort)
                .orderByAsc(DepartmentDO::getCreateTime);
        return departmentMapper.selectList(queryWrapper);
    }

    @Override
    public DepartmentDO findOrganizationTopDepartment(Long organizationId) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DepartmentDO::getOrganizationId, organizationId)
                .eq(DepartmentDO::getParentId, 0L)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return departmentMapper.selectOne(queryWrapper);
    }

    @Override
    public DepartmentDO findById(Long id) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DepartmentDO::getId, id)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return departmentMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DepartmentDO> findByIds(List<Long> ids) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(DepartmentDO::getId, ids)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return departmentMapper.selectList(queryWrapper);
    }

    @Override
    public Long save(DepartmentDO departmentDO) {
        if (departmentDO.getId() == null) {
            departmentMapper.insert(departmentDO);
        }
        else {
            departmentMapper.updateById(departmentDO);
        }
        return departmentDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        DepartmentDO departmentDO = findById(id);
        departmentDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        departmentDO.setDeletorId(employee.getId());
        departmentDO.setDeletorName(employee.getName());
        departmentDO.setDeleteTime(LocalDateTime.now());
        departmentMapper.updateById(departmentDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DepartmentDO::getOrganizationId, organizationId)
                .eq(DepartmentDO::getName, name)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(DepartmentDO::getId, excludeId);
        }
        return departmentMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByParentId(Long parentId) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(DepartmentDO::getParentId, parentId)
                .eq(DepartmentDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return departmentMapper.selectCount(queryWrapper);
    }

    private void recursionLoadParentDepartmentIds(Long parentId, List<Long> ids) {
        DepartmentDO parentDepartment = parentId == null || parentId == 0L ? null : findById(parentId);
        if (parentDepartment != null && !ids.contains(parentDepartment.getId())) {
            ids.add(parentDepartment.getId());
            recursionLoadParentDepartmentIds(parentDepartment.getParentId(), ids);
        }
    }
}
