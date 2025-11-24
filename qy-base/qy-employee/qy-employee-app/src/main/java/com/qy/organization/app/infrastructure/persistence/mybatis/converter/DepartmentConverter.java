package com.qy.organization.app.infrastructure.persistence.mybatis.converter;

import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.valueobject.DepartmentId;
import com.qy.organization.app.domain.valueobject.PinyinName;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import org.mapstruct.Mapper;

/**
 * 部门转化器
 *
 * @author legendjw
 */
@Mapper
public interface DepartmentConverter {
    /**
     * DO类转化为部门实体
     *
     * @param departmentDO
     * @return
     */
    default Department toEntity(DepartmentDO departmentDO, DepartmentDO parentDepartmentDO) {
        if (departmentDO == null) {
            return null;
        }
        Department parentDepartment = parentDepartmentDO == null ? null : Department.builder()
                .id(new DepartmentId(parentDepartmentDO.getId()))
                .name(new PinyinName(parentDepartmentDO.getName(), parentDepartmentDO.getNamePinyin())).build();
        return Department.builder()
                .id(new DepartmentId(departmentDO.getId()))
                .name(new PinyinName(departmentDO.getName(), departmentDO.getNamePinyin()))
                .parentDepartment(parentDepartment)
                .build();
    }
}