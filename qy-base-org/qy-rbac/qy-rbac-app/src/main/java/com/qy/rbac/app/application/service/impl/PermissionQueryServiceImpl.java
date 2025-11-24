package com.qy.rbac.app.application.service.impl;

import com.qy.rbac.app.application.service.PermissionQueryService;
import com.qy.rbac.app.domain.auth.Permission;
import com.qy.rbac.app.domain.auth.PermissionId;
import com.qy.rbac.app.infrastructure.persistence.AuthItemDomainRepository;
import org.springframework.stereotype.Service;

/**
 * 权限节点查询服务实现
 *
 * @author legendjw
 */
@Service
public class PermissionQueryServiceImpl implements PermissionQueryService {
    private AuthItemDomainRepository authItemDomainRepository;

    public PermissionQueryServiceImpl(AuthItemDomainRepository authItemDomainRepository) {
        this.authItemDomainRepository = authItemDomainRepository;
    }

    @Override
    public boolean isPermissionExist(String id) {
        Permission permission = authItemDomainRepository.findPermissionById(new PermissionId(id));
        return permission == null ? false : true;
    }
}
