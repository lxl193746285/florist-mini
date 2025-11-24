package com.qy.rbac.app.application.service;

/**
 * 权限节点查询服务
 *
 * @author legendjw
 */
public interface PermissionQueryService {
    /**
     * 指定id的权限节点是否存在
     *
     * @param id
     * @return
     */
    boolean isPermissionExist(String id);
}
