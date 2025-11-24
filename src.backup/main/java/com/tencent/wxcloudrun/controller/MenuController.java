package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.MenuTreeVO;
import com.tencent.wxcloudrun.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单管理接口
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取当前用户的菜单树
     */
    @GetMapping("/list")
    public ApiResponse getUserMenuTree(HttpServletRequest request) {
        // 从请求属性中获取用户ID（由JWT拦截器设置）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ApiResponse.error("未登录");
        }

        List<MenuTreeVO> menuTree = menuService.getUserMenuTree(userId);
        return ApiResponse.ok(menuTree);
    }

    /**
     * 获取所有菜单树（管理员使用）
     */
    @GetMapping("/all")
    public ApiResponse getAllMenuTree() {
        List<MenuTreeVO> menuTree = menuService.getAllMenuTree();
        return ApiResponse.ok(menuTree);
    }
}
