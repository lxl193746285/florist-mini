package com.qy.rbac.api.client;

import com.qy.rbac.api.dto.AppBasicDTO;
import com.qy.rbac.api.dto.ClientDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-client")
public interface ClientClient {
    /**
     * 根据ID查询客户端
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/clients/by-id")
    ClientDTO getClientById(
            @RequestParam(value = "id") Long id
    );

    /**
     * 根据客户端id查询客户端
     *
     * @return
     */
    @GetMapping("/v4/api/rbac/clients")
    ClientDTO getClient(
            @RequestParam(value = "client_id") String clientId
    );
}
