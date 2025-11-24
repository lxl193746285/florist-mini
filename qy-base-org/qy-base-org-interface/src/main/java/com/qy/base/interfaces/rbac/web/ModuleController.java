package com.qy.base.interfaces.rbac.web;

import com.qy.rbac.app.application.command.CreateModuleCommand;
import com.qy.rbac.app.application.command.DeleteModuleCommand;
import com.qy.rbac.app.application.command.UpdateModuleCommand;
import com.qy.rbac.app.application.dto.ModuleDTO;
import com.qy.rbac.app.application.query.ModuleQuery;
import com.qy.rbac.app.application.service.ModuleCommandService;
import com.qy.rbac.app.application.service.ModuleQueryService;
import com.qy.rest.exception.NotFoundException;
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
 * 模块
 *
 * @author legendjw
 */
@Api(tags = "模块")
@RestController
@RequestMapping("/v4/rbac/modules")
public class ModuleController {
    private SessionContext sessionContext;
    private ModuleCommandService moduleCommandService;
    private ModuleQueryService moduleQueryService;

    public ModuleController(SessionContext sessionContext, ModuleCommandService moduleCommandService, ModuleQueryService moduleQueryService) {
        this.sessionContext = sessionContext;
        this.moduleCommandService = moduleCommandService;
        this.moduleQueryService = moduleQueryService;
    }

    /**
     * 获取模块列表
     *
     * @return
     */
    @ApiOperation("获取模块列表")
    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getModules(ModuleQuery query) {
        List<ModuleDTO> moduleDTOS = moduleQueryService.getModules(query, sessionContext.getUser());

        return ResponseUtils.ok().body(moduleDTOS);
    }

    /**
     * 获取单个模块
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个模块")
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModule(
        @PathVariable(value = "id") Long id
    ) {
        ModuleDTO moduleDTO = moduleQueryService.getModuleById(id);
        if (moduleDTO == null) {
            throw new NotFoundException("未找到指定的模块");
        }
        return ResponseUtils.ok().body(moduleDTO);
    }

    /**
     * 创建单个模块
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个模块")
    @PostMapping
    public ResponseEntity<Object> createModule(
            @Valid @RequestBody CreateModuleCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getUser());
        moduleCommandService.createModule(command);

        return ResponseUtils.ok("模块创建成功").build();
    }

    /**
     * 修改单个模块
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个模块")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateModule(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateModuleCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        ModuleDTO moduleDTO = getModuleById(id);

        command.setId(moduleDTO.getId());
        command.setIdentity(sessionContext.getUser());
        moduleCommandService.updateModule(command);

        return ResponseUtils.ok("模块修改成功").build();
    }

    /**
     * 删除单个模块
     *
     * @param id
     */
    @ApiOperation("删除单个模块")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteModule(
        @PathVariable(value = "id") Long id
    ) {
        ModuleDTO moduleDTO = getModuleById(id);
        DeleteModuleCommand command = new DeleteModuleCommand();
        command.setId(moduleDTO.getId());
        command.setIdentity(sessionContext.getUser());
        moduleCommandService.deleteModule(command);

        return ResponseUtils.noContent("删除模块成功").build();
    }

    /**
     * 根据id获取模块
     *
     * @param id
     * @return
     */
    private ModuleDTO getModuleById(Long id) {
        ModuleDTO moduleDTO = moduleQueryService.getModuleById(id);
        if (moduleDTO == null) {
            throw new NotFoundException("未找到指定的模块");
        }
        return moduleDTO;
    }
}

