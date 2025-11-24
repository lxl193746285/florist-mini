package com.qy.base.interfaces.rbac.web;

import com.qy.rbac.app.application.command.BatchDeleteMenuCommand;
import com.qy.rbac.app.application.command.CreateMenuCommand;
import com.qy.rbac.app.application.command.DeleteMenuCommand;
import com.qy.rbac.app.application.command.UpdateMenuCommand;
import com.qy.rbac.app.application.dto.MenuDTO;
import com.qy.rbac.app.application.query.MenuQuery;
import com.qy.rbac.app.application.service.AuthCommandService;
import com.qy.rbac.app.application.service.MenuCommandService;
import com.qy.rbac.app.application.service.MenuQueryService;
import com.qy.rbac.app.domain.menu.MenuType;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.util.TreeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单
 *
 * @author legendjw
 */
@Api(tags = "菜单")
@RestController
@RequestMapping("/v4/rbac/menus")
public class MenuController {
    private OrganizationSessionContext sessionContext;
    private MenuCommandService menuCommandService;
    private MenuQueryService menuQueryService;
    private AuthCommandService authCommandService;

    public MenuController(OrganizationSessionContext sessionContext, MenuCommandService menuCommandService, MenuQueryService menuQueryService, AuthCommandService authCommandService) {
        this.sessionContext = sessionContext;
        this.menuCommandService = menuCommandService;
        this.menuQueryService = menuQueryService;
        this.authCommandService = authCommandService;
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @ApiOperation("获取菜单列表")
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenus(MenuQuery query) {
        List<MenuDTO> menuDTOS = menuQueryService.getMenus(query, sessionContext.getUser());

        return ResponseUtils.ok().body(menuDTOS);
    }

    /**
     * 获取树形菜单列表
     *
     * @return
     */
    @ApiOperation("获取树形菜单列表")
    @GetMapping("/tree")
    public ResponseEntity<List<MenuDTO>> getTreeMenus(MenuQuery query) {
        List<MenuDTO> menuDTOS = menuQueryService.getMenus(query, sessionContext.getUser());
        //如果有搜索条件则不展示树级
        if (StringUtils.isBlank(query.getKeywords())) {
            return ResponseUtils.ok().body(TreeUtils.toTree(menuDTOS));
        }
        else {
            return ResponseUtils.ok().body(menuDTOS);
        }
    }

    /**
     * 获取单个菜单
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个菜单")
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenu(
        @PathVariable(value = "id") Long id
    ) {
        MenuDTO menuDTO = menuQueryService.getMenuById(id, sessionContext.getUser());
        if (menuDTO == null) {
            throw new NotFoundException("未找到指定的菜单");
        }
        return ResponseUtils.ok().body(menuDTO);
    }

    /**
     * 创建单个菜单
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个菜单")
    @PostMapping
    public ResponseEntity<Object> createMenu(
            @Valid @RequestBody CreateMenuCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getUser());
        if (command.getTypeId().intValue() == MenuType.GENERAL_MENU.getId()) {
            menuCommandService.createGeneralMenu(command);
        }
        if (command.getTypeId().intValue() == MenuType.PERMISSION_MENU.getId()){
            menuCommandService.createPermissionMenu(command);
        }

        if (command.getTypeId().intValue() == MenuType.PATH_MENU.getId()){
            menuCommandService.createPathMenu(command);
        }
        return ResponseUtils.ok("菜单创建成功").build();
    }

    /**
     * 修改单个菜单
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个菜单")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateMenu(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateMenuCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        MenuDTO menuDTO = getMenuById(id);

        command.setId(menuDTO.getId());
        command.setIdentity(sessionContext.getUser());
        if (menuDTO.getTypeId() == MenuType.GENERAL_MENU.getId()) {
            menuCommandService.updateGeneralMenu(command);
        }
        if (command.getTypeId().intValue() == MenuType.PERMISSION_MENU.getId()){
            menuCommandService.updatePermissionMenu(command);
        }
        if (command.getTypeId().intValue() == MenuType.PATH_MENU.getId()){
            menuCommandService.updatePathMenu(command);
        }
        return ResponseUtils.ok("菜单修改成功").build();
    }

    /**
     * 删除单个菜单
     *
     * @param id
     */
    @ApiOperation("删除单个菜单")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMenu(
        @PathVariable(value = "id") Long id
    ) {
        MenuDTO menuDTO = getMenuById(id);
        DeleteMenuCommand command = new DeleteMenuCommand();
        command.setId(menuDTO.getId());
        command.setIdentity(sessionContext.getUser());
        menuCommandService.deleteMenu(command);

        return ResponseUtils.noContent("删除菜单成功").build();
    }

    /**
     * 批量删除菜单
     *
     * @param ids
     */
    @ApiOperation("批量删除菜单")
    @DeleteMapping
    public ResponseEntity<Object> batchDeleteMenu(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        BatchDeleteMenuCommand command = new BatchDeleteMenuCommand();
        command.setIds(ids);
        command.setIdentity(sessionContext.getUser());
        menuCommandService.batchDeleteMenu(command);

        return ResponseUtils.noContent("批量删除菜单成功").build();
    }

    /**
     * 授权所有权限给超管
     *
     * @return
     */
    @ApiOperation("授权所有权限给超管")
    @PostMapping("/authorize-all-permission-to-root")
    public ResponseEntity<Object> authorizeAllPermissionToRoot() {
        authCommandService.authorizeAllPermissionToRoot();
        return ResponseUtils.ok("授权所有角色给超管成功").build();
    }

    /**
     * 批量修复菜单权限
     *
     * @return
     */
    @ApiOperation("批量修复菜单权限")
    @PostMapping("/batch-repair-menu-permission")
    public ResponseEntity<Object> batchRepairMenuPermission() {
        menuCommandService.batchRepairMenuPermission();
        return ResponseUtils.ok("批量修复菜单权限成功").build();
    }

    /**
     * 菜单列表导出
     *
     * @return
     */
    @ApiOperation("菜单列表导出")
    @GetMapping("/menus-export")
    public void exportMenus() {
        MenuQuery query = new MenuQuery();
        menuQueryService.exportMenus(query, sessionContext.getUser());
    }

    /**
     * 根据id获取菜单
     *
     * @param id
     * @return
     */
    private MenuDTO getMenuById(Long id) {
        MenuDTO menuDTO = menuQueryService.getMenuById(id, sessionContext.getUser());
        if (menuDTO == null) {
            throw new NotFoundException("未找到指定的菜单");
        }
        return menuDTO;
    }
}

