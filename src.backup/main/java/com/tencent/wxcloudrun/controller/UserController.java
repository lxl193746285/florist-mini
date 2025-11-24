package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ChangePasswordRequest;
import com.tencent.wxcloudrun.dto.UpdateProfileRequest;
import com.tencent.wxcloudrun.dto.UserInfoVO;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器（需要登录）
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ApiResponse getCurrentUser(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            UserInfoVO userInfo = userService.getUserInfo(userId);
            return ApiResponse.ok(userInfo);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 修改用户资料
     */
    @PutMapping("/profile")
    public ApiResponse updateProfile(HttpServletRequest request, @RequestBody UpdateProfileRequest updateRequest) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            userService.updateProfile(userId, updateRequest);
            return ApiResponse.ok("修改成功");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public ApiResponse changePassword(HttpServletRequest request, @Validated @RequestBody ChangePasswordRequest changeRequest) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            userService.changePassword(userId, changeRequest);
            return ApiResponse.ok("密码修改成功");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
