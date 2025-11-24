package com.qy.rbac.app.infrastructure.persistence.mybatis.converter;

import com.qy.rbac.app.domain.auth.Permission;
import com.qy.rbac.app.domain.auth.PermissionId;
import com.qy.rbac.app.domain.auth.Role;
import com.qy.rbac.app.domain.auth.RoleId;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 权限项转换器
 *
 * @author legendjw
 */
@Mapper
public interface AuthItemConverter {
    @Mappings({
            @Mapping(source = "id.id", target = "name"),
    })
    AuthItemDO roleToDO(Role role);

    @Mappings({
            @Mapping(source = "id.id", target = "name"),
    })
    AuthItemDO permissionToDO(Permission permission);

    default Role toRole(AuthItemDO authItemDO) {
        return Role.builder()
                .id(new RoleId(authItemDO.getName()))
                .description(authItemDO.getDescription())
                .createTime(authItemDO.getCreateTime())
                .updateTime(authItemDO.getUpdateTime())
                .build();
    }

    default Permission toPermission(AuthItemDO authItemDO) {
        return Permission.builder()
                .id(new PermissionId(authItemDO.getName()))
                .description(authItemDO.getDescription())
                .createTime(authItemDO.getCreateTime())
                .updateTime(authItemDO.getUpdateTime())
                .build();
    }
}