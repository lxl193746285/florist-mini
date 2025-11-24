package com.qy.base.interfaces.authorization.api;

import com.qy.authorization.app.application.command.CreateAuthClientCommand;
import com.qy.authorization.app.application.command.UpdateAuthClientCommand;
import com.qy.authorization.app.application.service.AuthClientService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/authorization/clients")
public class AuthClientController {
    private AuthClientService authClientService;

    public AuthClientController(AuthClientService authClientService) {
        this.authClientService = authClientService;
    }

    /**
     * 创建单个客户端
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createClient(
            @Valid @RequestBody CreateAuthClientCommand command
    ) {
        authClientService.createAuthClient(command);

        return ResponseUtils.ok("客户端创建成功").build();
    }

    /**
     * 修改单个客户端
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateClient(
        @PathVariable(value = "id") String id,
        @Valid @RequestBody UpdateAuthClientCommand command
    ) {
        command.setId(id);
        authClientService.updateAuthClient(command);

        return ResponseUtils.ok("客户端修改成功").build();
    }

    /**
     * 删除单个客户端
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(
        @PathVariable(value = "id") String id
    ) {
        authClientService.deleteAuthClient(id);

        return ResponseUtils.noContent("删除客户端成功").build();
    }
}

