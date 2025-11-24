package com.qy.base.interfaces.authorization.api;

import com.qy.authorization.app.application.command.GenerateAccessTokenCommand;
import com.qy.authorization.app.application.command.RefreshAccessTokenCommand;
import com.qy.authorization.app.application.dto.AccessTokenDTO;
import com.qy.authorization.app.application.dto.ValidateClientResultDTO;
import com.qy.authorization.app.application.service.AuthorizationService;
import com.qy.rest.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 授权内部服务接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/authorization")
public class AuthorizationApiController {
    private AuthorizationService authorizationService;

    public AuthorizationApiController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * 验证客户端
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    @GetMapping("validate-client")
    public ResponseEntity<ValidateClientResultDTO> validateClient(
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "client_secret") String clientSecret
    ) {
        return ResponseUtils.ok().body(authorizationService.validateClient(clientId, clientSecret));
    }

    /**
     * 生成访问token
     *
     * @param generateAccessTokenCommand
     * @return
     */
    @PostMapping("/oauth/token")
    public ResponseEntity<AccessTokenDTO> generateAccessToken(
            @RequestBody GenerateAccessTokenCommand generateAccessTokenCommand
    ) {
        return ResponseUtils.ok().body(authorizationService.generateAccessToken(generateAccessTokenCommand));
    }

    /**
     * 刷新token
     *
     * @param refreshAccessTokenCommand
     * @return
     */
    @PostMapping("/oauth/refresh-token")
    public ResponseEntity<AccessTokenDTO> refreshAccessToken(
            @RequestBody RefreshAccessTokenCommand refreshAccessTokenCommand
    ) {
        return ResponseUtils.ok().body(authorizationService.refreshAccessToken(refreshAccessTokenCommand));
    }

    @DeleteMapping("/oauth/remove-token")
    public ResponseEntity removeAccessToken(
            @RequestParam(name = "member_id") Long memberId
    ){
        authorizationService.deleteUserAccessToken(memberId);
        return ResponseUtils.ok().build();
    }
}