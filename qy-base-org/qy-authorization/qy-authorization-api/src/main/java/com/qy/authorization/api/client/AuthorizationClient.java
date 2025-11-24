package com.qy.authorization.api.client;

import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.authorization.api.command.RefreshAccessTokenCommand;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.authorization.api.dto.ValidateClientResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-authorization")
public interface AuthorizationClient {
    /**
     * 验证客户端
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    @GetMapping("/v4/api/authorization/validate-client")
    ValidateClientResultDTO validateClient(
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "client_secret") String clientSecret
    );

    /**
     * 生成一个访问令牌
     *
     * @param generateAccessTokenCommand
     * @return
     */
    @PostMapping(value = "/v4/api/authorization/oauth/token")
    AccessTokenDTO generateAccessToken(
            @RequestBody GenerateAccessTokenCommand generateAccessTokenCommand
    );

    /**
     * 刷新一个访问令牌
     *
     * @param refreshAccessTokenCommand
     * @return
     */
    @PostMapping(value = "/v4/api/authorization/oauth/refresh-token")
    AccessTokenDTO refreshAccessToken(
            @RequestBody RefreshAccessTokenCommand refreshAccessTokenCommand
    );

    /**
     * 删除指定用户访问令牌
     *
     * @param memberId
     * @return
     */
    @DeleteMapping(value = "/v4/api/authorization/oauth/remove-token")
    void removeToken(
            @RequestParam(name = "member_id") Long memberId
    );
}
