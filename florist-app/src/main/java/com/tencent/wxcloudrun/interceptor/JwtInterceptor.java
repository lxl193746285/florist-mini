package com.tencent.wxcloudrun.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT Token 拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理OPTIONS请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 获取Token
        String token = getTokenFromRequest(request);

        if (token == null || token.isEmpty()) {
            sendErrorResponse(response, "未提供认证令牌");
            return false;
        }

        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            sendErrorResponse(response, "认证令牌无效或已过期");
            return false;
        }

        // 将用户信息放入request attribute
        Long userId = jwtUtil.getUserIdFromToken(token);
        Integer userType = jwtUtil.getUserTypeFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);

        request.setAttribute("userId", userId);
        request.setAttribute("userType", userType);
        request.setAttribute("username", username);

        return true;
    }

    /**
     * 从请求中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 从Header中获取
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 从参数中获取（备用）
        return request.getParameter("token");
    }

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponse apiResponse = ApiResponse.error(401, message);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
