package com.qy.system.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-autonumber", configuration = FeignTokenRequestInterceptor.class)
public interface AutoNumberClient {

    /**
     * 获取编号
     * @param noId
     * @param companyId
     * @param userId
     * @return
     */
    @GetMapping("/v4/api/system/autonumber/getAutoNumber")
    String getAutoNumber(
            @RequestParam(value = "no_id") String noId,
            @RequestParam(value = "company_id") Long companyId,
            @RequestParam(value = "user_id") Long userId
    );
}
