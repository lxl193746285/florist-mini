package com.qy.uims.security.permission;

import com.qy.organization.api.dto.DepartmentBasicDTO;
import com.qy.security.permission.scope.PermissionScope;
import com.qy.security.permission.scope.SecurityPermissionScope;

/**
 * 通用本部门的权限范围
 *
 * @author legendjw
 */
@SecurityPermissionScope
public class DepartmentPermissionScope implements PermissionScope<DepartmentBasicDTO> {
    @Override
    public String getId() {
        return "departmentScope";
    }

    @Override
    public String getName() {
        return "通用本部门范围";
    }

    @Override
    public DepartmentBasicDTO getScopeData(Object data) {
        return null;
    }
}
