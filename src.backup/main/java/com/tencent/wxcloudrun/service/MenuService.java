package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.PermissionMapper;
import com.tencent.wxcloudrun.dao.RoleMapper;
import com.tencent.wxcloudrun.dto.MenuTreeVO;
import com.tencent.wxcloudrun.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务
 */
@Service
public class MenuService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取用户菜单树（基于用户角色权限）
     */
    public List<MenuTreeVO> getUserMenuTree(Long userId) {
        // 1. 查询用户的所有权限
        List<Permission> permissions = permissionMapper.selectByUserId(userId);

        // 2. 只保留菜单类型的权限（type=1）
        List<Permission> menuPermissions = permissions.stream()
                .filter(p -> p.getPermissionType() == 1 && p.getStatus() == 1)
                .sorted((a, b) -> {
                    int sortCompare = Integer.compare(
                        a.getSort() != null ? a.getSort() : 0,
                        b.getSort() != null ? b.getSort() : 0
                    );
                    if (sortCompare != 0) return sortCompare;
                    return Long.compare(a.getId(), b.getId());
                })
                .collect(Collectors.toList());

        // 3. 构建树形结构
        return buildMenuTree(menuPermissions, 0L);
    }

    /**
     * 构建菜单树
     */
    private List<MenuTreeVO> buildMenuTree(List<Permission> permissions, Long parentId) {
        List<MenuTreeVO> result = new ArrayList<>();

        for (Permission permission : permissions) {
            if (permission.getParentId().equals(parentId)) {
                MenuTreeVO node = new MenuTreeVO();
                node.setId(permission.getId());
                node.setParentId(permission.getParentId());
                node.setName(permission.getPermissionName());
                node.setUrl(permission.getPath());
                node.setIcon(permission.getIcon());
                node.setSort(permission.getSort());

                // 递归查找子菜单
                List<MenuTreeVO> children = buildMenuTree(permissions, permission.getId());
                if (!children.isEmpty()) {
                    node.setChildren(children);
                }

                result.add(node);
            }
        }

        return result;
    }

    /**
     * 获取所有菜单（管理员使用）
     */
    public List<MenuTreeVO> getAllMenuTree() {
        List<Permission> allPermissions = permissionMapper.selectAll();

        // 只保留菜单类型的权限
        List<Permission> menuPermissions = allPermissions.stream()
                .filter(p -> p.getPermissionType() == 1 && p.getStatus() == 1)
                .sorted((a, b) -> {
                    int sortCompare = Integer.compare(
                        a.getSort() != null ? a.getSort() : 0,
                        b.getSort() != null ? b.getSort() : 0
                    );
                    if (sortCompare != 0) return sortCompare;
                    return Long.compare(a.getId(), b.getId());
                })
                .collect(Collectors.toList());

        return buildMenuTree(menuPermissions, 0L);
    }
}
