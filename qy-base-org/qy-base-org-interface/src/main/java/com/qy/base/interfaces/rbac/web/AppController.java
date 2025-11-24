package com.qy.base.interfaces.rbac.web;

import com.qy.rbac.app.application.command.CreateAppCommand;
import com.qy.rbac.app.application.command.DeleteAppCommand;
import com.qy.rbac.app.application.command.UpdateAppCommand;
import com.qy.rbac.app.application.dto.AppDTO;
import com.qy.rbac.app.application.query.AppQuery;
import com.qy.rbac.app.application.service.AppCommandService;
import com.qy.rbac.app.application.service.AppQueryService;
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
 * 应用
 *
 * @author legendjw
 */
@Api(tags = "应用")
@RestController
@RequestMapping("/v4/rbac/apps")
public class AppController {
    private SessionContext sessionContext;
    private AppCommandService appCommandService;
    private AppQueryService appQueryService;

    public AppController(SessionContext sessionContext, AppCommandService appCommandService, AppQueryService appQueryService) {
        this.sessionContext = sessionContext;
        this.appCommandService = appCommandService;
        this.appQueryService = appQueryService;
    }

    /**
     * 获取应用列表
     *
     * @return
     */
    @ApiOperation("获取应用列表")
    @GetMapping
    public ResponseEntity<List<AppDTO>> getApps(AppQuery query) {
        List<AppDTO> appDTOS = appQueryService.getApps(query, sessionContext.getUser());

        return ResponseUtils.ok().body(appDTOS);
    }

    /**
     * 获取单个应用
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个应用")
    @GetMapping("/{id}")
    public ResponseEntity<AppDTO> getApp(
        @PathVariable(value = "id") Long id
    ) {
        AppDTO appDTO = appQueryService.getAppById(id);
        if (appDTO == null) {
            throw new NotFoundException("未找到指定的应用");
        }
        return ResponseUtils.ok().body(appDTO);
    }

    /**
     * 创建单个应用
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个应用")
    @PostMapping
    public ResponseEntity<Object> createApp(
            @Valid @RequestBody CreateAppCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getUser());
        appCommandService.createApp(command);

        return ResponseUtils.ok("应用创建成功").build();
    }

    /**
     * 修改单个应用
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个应用")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateApp(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateAppCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AppDTO appDTO = getAppById(id);

        command.setId(appDTO.getId());
        command.setIdentity(sessionContext.getUser());
        appCommandService.updateApp(command);

        return ResponseUtils.ok("应用修改成功").build();
    }

    /**
     * 删除单个应用
     *
     * @param id
     */
    @ApiOperation("删除单个应用")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApp(
        @PathVariable(value = "id") Long id
    ) {
        AppDTO appDTO = getAppById(id);
        DeleteAppCommand command = new DeleteAppCommand();
        command.setId(appDTO.getId());
        command.setIdentity(sessionContext.getUser());
        appCommandService.deleteApp(command);

        return ResponseUtils.noContent("删除应用成功").build();
    }

    /**
     * 根据id获取应用
     *
     * @param id
     * @return
     */
    private AppDTO getAppById(Long id) {
        AppDTO appDTO = appQueryService.getAppById(id);
        if (appDTO == null) {
            throw new NotFoundException("未找到指定的应用");
        }
        return appDTO;
    }
}

