package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse login(@Validated @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ApiResponse.ok(response);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 会员注册
     */
    @PostMapping("/register/member")
    public ApiResponse registerMember(@Validated @RequestBody MemberRegisterRequest request) {
        try {
            authService.registerMember(request);
            return ApiResponse.ok("注册成功");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 管理员注册（实际应用中需要权限验证）
     */
    @PostMapping("/register/admin")
    public ApiResponse registerAdmin(@Validated @RequestBody AdminRegisterRequest request) {
        try {
            authService.registerAdmin(request);
            return ApiResponse.ok("注册成功");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 测试接口 - 需要登录才能访问（后续通过拦截器实现）
     */
    @GetMapping("/test")
    public ApiResponse test() {
        return ApiResponse.ok("登录验证成功");
    }
}
