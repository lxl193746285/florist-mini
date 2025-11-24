package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.service.RoleCommandService;
import com.qy.rbac.app.domain.auth.Role;
import com.qy.rbac.app.domain.auth.RoleId;
import com.qy.rbac.app.infrastructure.persistence.AuthItemDomainRepository;
import com.qy.rest.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 角色命令服务实现
 *
 * @author legendjw
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private AuthItemDomainRepository authItemDomainRepository;

    public RoleCommandServiceImpl(AuthItemDomainRepository authItemDomainRepository) {
        this.authItemDomainRepository = authItemDomainRepository;
    }

    @Override
    public RoleId createRole(String id, String description) {
        if (authItemDomainRepository.countRoleId(id, null) > 0) {
            throw new ValidationException(String.format("名称为%s的角色已经存在", id));
        }

        Role role = Role.builder()
                .id(new RoleId(id))
                .description(description)
                .createTime(LocalDateTime.now())
                .build();
        return authItemDomainRepository.createRole(role);
    }

    @Override
    public void updateRole(String id, String newId, String description) {
        RoleId roleId = new RoleId(id);
        Role role = authItemDomainRepository.findRoleById(roleId);
        if (authItemDomainRepository.countRoleId(newId, id) > 0) {
            throw new ValidationException(String.format("名称为%s的角色已经存在", newId));
        }

        role.modifyId(roleId);
        role.modifyDescription(description);
        authItemDomainRepository.updateRole(roleId, role);
    }

    @Override
    public void deleteRole(String id) {
        authItemDomainRepository.removeRole(new RoleId(id));
    }
}
