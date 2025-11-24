package com.qy.system.api.client;

import com.qy.system.api.command.LoginLogFormDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录、登出日志
 *
 * @author wwd
 */
@FeignClient(name = "qy-base", contextId = "qy-system-login-log")
public interface LoginLogClient {

    /**
     * 创建登录、登出日志
     * @param dto
     * @return
     */
    @PostMapping("/v4/api/system/login-logs")
    Long createLoginLog(
            @RequestBody LoginLogFormDTO dto
            );
}
