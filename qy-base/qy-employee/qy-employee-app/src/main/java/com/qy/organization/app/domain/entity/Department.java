package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.organization.app.domain.valueobject.DepartmentId;
import com.qy.organization.app.domain.valueobject.PinyinName;
import lombok.Builder;
import lombok.Getter;

/**
 * 部门
 *
 * @author legendjw
 */
@Getter
@Builder
public class Department implements Entity {
    /**
     * 部门id
     */
    private DepartmentId id;

    /**
     * 名称
     */
    private PinyinName name;

    /**
     * 父级公司
     */
    private Department parentDepartment;
}