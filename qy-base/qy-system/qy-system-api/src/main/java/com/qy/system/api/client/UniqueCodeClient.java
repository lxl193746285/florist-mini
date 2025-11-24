package com.qy.system.api.client;

import com.qy.system.api.command.UserUniqueCodeBasicDTO;
import com.qy.system.api.command.UserUniqueCodeFormDTO;
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
@FeignClient(name = "qy-base", contextId = "qy-system-unique-code")
public interface UniqueCodeClient {

    /**
     * 创建单个用户设备唯一码
     * @param dto
     * @return
     */
    @PostMapping("/v4/api/system/user-unique-codes")
    void createLoginLog(
            @RequestBody UserUniqueCodeFormDTO dto
    );


    @GetMapping("/v4/api/system/user-unique-codes")
    public UserUniqueCodeBasicDTO getUserUniqueCode(
            @RequestParam(value = "member_id") Long memberId,
            @RequestParam(value = "org_id") Long orgId
    );
}
