package com.qy.base.interfaces.system.web;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.util.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.app.config.dto.SystemConfigCategoryDTO;
import com.qy.system.app.config.dto.SystemConfigCategoryFormDTO;
import com.qy.system.app.config.dto.SystemConfigCategoryQueryDTO;
import com.qy.system.app.config.entity.SystemConfigCategoryEntity;
import com.qy.system.app.config.service.SystemConfigCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import com.qy.rest.util.ResponseUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 配置类别
 *
 * @author hh
 * @since 2024-07-09
 */
@RestController
@Validated
@RequestMapping("/v4/system/system-config-categorys")
@Api(tags = "配置类别")
public class SystemConfigCategoryController  extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private SystemConfigCategoryService systemConfigCategoryService;

    /**
     * 获取配置类别列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config_category', 'index')")
    @ApiOperation(value = "获取配置类别分页列表")
    public List<SystemConfigCategoryDTO> getSystemConfigCategorys(
        @ModelAttribute SystemConfigCategoryQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        IPage iPage = this.getPagination();
        IPage<SystemConfigCategoryDTO> pm = systemConfigCategoryService.getSystemConfigCategorys(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }

    /**
     * 获取单个配置类别
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config_category', 'view')")
    @ApiOperation(value = "获取单个配置类别")
    public SystemConfigCategoryDTO getSystemConfigCategory(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

    return systemConfigCategoryService.getSystemConfigCategoryDTO(id, currentUser);
    }

    /**
     * 创建单个配置类别
     *
     * @param systemConfigCategoryFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config_category', 'create')")
    @ApiOperation(value = "创建单个配置类别")
    public ResponseEntity<Object> createSystemConfigCategory(
        @Validated @RequestBody SystemConfigCategoryFormDTO systemConfigCategoryFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigCategoryService.createSystemConfigCategory(systemConfigCategoryFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个配置类别
     *
     * @param id
     * @param systemConfigCategoryFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config_category', 'update')")
    @ApiOperation(value = "修改单个配置类别")
    public ResponseEntity<Object> updateSystemConfigCategory(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody SystemConfigCategoryFormDTO systemConfigCategoryFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigCategoryService.updateSystemConfigCategory(id, systemConfigCategoryFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个配置类别
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config_category', 'delete')")
    @ApiOperation(value = "删除单个配置类别")
    public ResponseEntity<Object> deleteSystemConfigCategory(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigCategoryService.deleteSystemConfigCategory(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

