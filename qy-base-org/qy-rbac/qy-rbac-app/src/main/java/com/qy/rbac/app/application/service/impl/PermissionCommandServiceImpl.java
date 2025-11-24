package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.service.PermissionCommandService;
import com.qy.rbac.app.domain.auth.Permission;
import com.qy.rbac.app.domain.auth.PermissionId;
import com.qy.rbac.app.infrastructure.persistence.AuthItemDomainRepository;
import com.qy.rest.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 权限节点命令服务实现
 *
 * @author legendjw
 */
@Service
public class PermissionCommandServiceImpl implements PermissionCommandService {
    private AuthItemDomainRepository authItemDomainRepository;

    public PermissionCommandServiceImpl(AuthItemDomainRepository authItemDomainRepository) {
        this.authItemDomainRepository = authItemDomainRepository;
    }

    @Override
    public PermissionId createPermission(String id, String description) {
        if (authItemDomainRepository.countPermissionId(id, null) > 0) {
            throw new ValidationException(String.format("名称为%s的权限节点已经存在", id));
        }

        Permission permission = Permission.builder()
                .id(new PermissionId(id))
                .description(description)
                .createTime(LocalDateTime.now())
                .build();
        PermissionId permissionId = authItemDomainRepository.createPermission(permission);

        return permissionId;
    }

    @Override
    public void updatePermission(String id, String newId, String description) {
        if (authItemDomainRepository.countPermissionId(newId, id) > 0) {
            throw new ValidationException(String.format("名称为%s的权限节点已经存在", newId));
        }
        PermissionId permissionId = new PermissionId(id);
        Permission permission = authItemDomainRepository.findPermissionById(permissionId);
        permission.modifyId(new PermissionId(newId));
        permission.modifyDescription(description);
        authItemDomainRepository.updatePermission(permissionId, permission);
    }

    @Override
    public void deletePermission(String id) {
        authItemDomainRepository.removePermission(new PermissionId(id));
    }
}
