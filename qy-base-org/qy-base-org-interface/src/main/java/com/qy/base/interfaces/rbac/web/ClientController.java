package com.qy.base.interfaces.rbac.web;

import com.qy.rbac.app.application.command.CreateClientCommand;
import com.qy.rbac.app.application.command.DeleteClientCommand;
import com.qy.rbac.app.application.command.UpdateClientCommand;
import com.qy.rbac.app.application.dto.ClientDTO;
import com.qy.rbac.app.application.query.ClientQuery;
import com.qy.rbac.app.application.service.ClientCommandService;
import com.qy.rbac.app.application.service.ClientQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 客户端
 *
 * @author legendjw
 */
@Api("客户端")
@RestController
@RequestMapping("/v4/rbac/clients")
public class ClientController {
    private SessionContext sessionContext;
    private ClientCommandService clientCommandService;
    private ClientQueryService clientQueryService;

    public ClientController(SessionContext sessionContext, ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.sessionContext = sessionContext;
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    /**
     * 获取客户端列表
     *
     * @return
     */
    @ApiOperation("获取客户端列表")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(ClientQuery query) {
        Page<ClientDTO> page = clientQueryService.getClients(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个客户端
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个客户端")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(
        @PathVariable(value = "id") Long id
    ) {
        ClientDTO clientDTO = clientQueryService.getClientById(id);
        if (clientDTO == null) {
            throw new NotFoundException("未找到指定的客户端");
        }
        return ResponseUtils.ok().body(clientDTO);
    }

    /**
     * 创建单个客户端
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个客户端")
    @PostMapping
    public ResponseEntity<Object> createClient(
            @Valid @RequestBody CreateClientCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getUser());
        clientCommandService.createClient(command);

        return ResponseUtils.ok("客户端创建成功").build();
    }

    /**
     * 修改单个客户端
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个客户端")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateClient(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateClientCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        ClientDTO clientDTO = getClientById(id);

        command.setId(clientDTO.getId());
        command.setIdentity(sessionContext.getUser());
        clientCommandService.updateClient(command);

        return ResponseUtils.ok("客户端修改成功").build();
    }

    /**
     * 删除单个客户端
     *
     * @param id
     */
    @ApiOperation("删除单个客户端")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(
        @PathVariable(value = "id") Long id
    ) {
        ClientDTO clientDTO = getClientById(id);
        DeleteClientCommand command = new DeleteClientCommand();
        command.setId(clientDTO.getId());
        command.setIdentity(sessionContext.getUser());
        clientCommandService.deleteClient(command);

        return ResponseUtils.noContent("删除客户端成功").build();
    }

    /**
     * 根据id获取客户端
     *
     * @param id
     * @return
     */
    private ClientDTO getClientById(Long id) {
        ClientDTO clientDTO = clientQueryService.getClientById(id);
        if (clientDTO == null) {
            throw new NotFoundException("未找到指定的客户端");
        }
        return clientDTO;
    }
}

