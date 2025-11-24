package com.qy.rbac.api.client;

import com.qy.rbac.api.dto.AppBasicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-app")
public interface AppClient {
    /**
     * 获取指定用户的权限
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/apps")
    List<AppBasicDTO> getAppByIds(
            @RequestParam(value = "app_ids") List<Long> appIds
    );
}
