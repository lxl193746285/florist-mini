package com.qy.authorization.api.client;

import com.qy.authorization.api.command.CreateAuthClientCommand;
import com.qy.authorization.api.command.UpdateAuthClientCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-authorization-client")
public interface AuthClientClient {
    /**
     * 创建单个客户端
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/authorization/clients")
    void createClient(
            @Valid @RequestBody CreateAuthClientCommand command
    );

    /**
     * 修改单个客户端
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/authorization/clients/{id}")
    void updateClient(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody UpdateAuthClientCommand command
    );

    /**
     * 删除单个客户端
     *
     * @param id
     */
    @DeleteMapping("/v4/api/authorization/clients/{id}")
    void deleteClient(
            @PathVariable(value = "id") String id
    );
}
