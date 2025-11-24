package com.qy.organization.app.infrastructure.persistence.mybatis;

import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.valueobject.DepartmentId;
import com.qy.organization.app.infrastructure.persistence.DepartmentDomainRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.converter.DepartmentConverter;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.DepartmentMapper;
import org.springframework.stereotype.Repository;

/**
 * 部门领域服务实现
 *
 * @author legendjw
 */
@Repository
public class DepartmentDomainRepositoryImpl implements DepartmentDomainRepository {
    private DepartmentConverter departmentConverter;
    private DepartmentMapper departmentMapper;

    public DepartmentDomainRepositoryImpl(DepartmentConverter departmentConverter, DepartmentMapper departmentMapper) {
        this.departmentConverter = departmentConverter;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public Department findById(DepartmentId id) {
        DepartmentDO departmentDO = departmentMapper.selectById(id.getId());
        if (departmentDO == null) {
            return null;
        }
        DepartmentDO parentDepartmentDO = (departmentDO.getParentId().longValue() != 0L) ? departmentMapper.selectById(id.getId()) : null;

        return departmentConverter.toEntity(departmentDO, parentDepartmentDO);
    }

    @Override
    public Long findTopByOrganizationId(Long organizationId) {
        Long departmentId = departmentMapper.getTopDepartmentId(organizationId);
        if (departmentId == null) {
            return 0L;
        }
        return departmentId;
    }
}
