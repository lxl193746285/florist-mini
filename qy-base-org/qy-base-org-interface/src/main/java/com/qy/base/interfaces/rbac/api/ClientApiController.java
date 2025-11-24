package com.qy.base.interfaces.rbac.api;

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
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v4/api/rbac/clients")
public class ClientApiController {

    @Autowired
    private ClientQueryService clientQueryService;

    /**
     * 根据id查询客户端
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询客户端")
    @GetMapping("/by-id")
    public ClientDTO getClient(
            @RequestParam(value = "id") Long id
    ) {
        ClientDTO clientDTO = clientQueryService.getClientById(id);
//        if (clientDTO == null) {
//            throw new NotFoundException("未找到指定的客户端");
//        }
        return clientDTO;
    }

    /**
     * 根据客户端id查询客户端
     *
     * @param clientId
     * @return
     */
    @ApiOperation("获取单个客户端")
    @GetMapping
    public ClientDTO getClient(
        @RequestParam(value = "client_id") String clientId
    ) {
        ClientDTO clientDTO = clientQueryService.getClientByClientId(clientId);
//        if (clientDTO == null) {
//            throw new NotFoundException("未找到指定的客户端");
//        }
        return clientDTO;
    }
}

