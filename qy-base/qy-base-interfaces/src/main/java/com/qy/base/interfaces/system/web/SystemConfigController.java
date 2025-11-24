package com.qy.base.interfaces.system.web;

import com.qy.system.app.comment.BaseController;
import com.qy.system.app.config.dto.SystemConfigSearchDTO;
import com.qy.system.app.util.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.app.config.dto.SystemConfigDTO;
import com.qy.system.app.config.dto.SystemConfigFormDTO;
import com.qy.system.app.config.dto.SystemConfigQueryDTO;
import com.qy.system.app.config.entity.SystemConfigEntity;
import com.qy.system.app.config.service.SystemConfigService;
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
 * 配置
 *
 * @author hh
 * @since 2024-07-09
 */
@RestController
@Validated
@RequestMapping("/v4/system/system-configs")
@Api(tags = "配置")
public class SystemConfigController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 获取配置列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config', 'index')")
    @ApiOperation(value = "获取配置分页列表")
    public List<SystemConfigDTO> getSystemConfigs(
            @ModelAttribute SystemConfigQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        IPage iPage = this.getPagination();
        IPage<SystemConfigDTO> pm = systemConfigService.getSystemConfigs(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }

    /**
     * 获取单个配置
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config', 'view')")
    @ApiOperation(value = "获取单个配置")
    public SystemConfigDTO getSystemConfig(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        return systemConfigService.getSystemConfigDTO(id, currentUser);
    }

    /**
     * 创建单个配置
     *
     * @param systemConfigFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config', 'create')")
    @ApiOperation(value = "创建单个配置")
    public ResponseEntity<Object> createSystemConfig(
            @Validated @RequestBody SystemConfigFormDTO systemConfigFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigService.createSystemConfig(systemConfigFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个配置
     *
     * @param id
     * @param systemConfigFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config', 'update')")
    @ApiOperation(value = "修改单个配置")
    public ResponseEntity<Object> updateSystemConfig(
            @PathVariable(value = "id") Long id,
            @Validated @RequestBody SystemConfigFormDTO systemConfigFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigService.updateSystemConfig(id, systemConfigFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个配置
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_config', 'delete')")
    @ApiOperation(value = "删除单个配置")
    public ResponseEntity<Object> deleteSystemConfig(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        systemConfigService.deleteSystemConfig(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }

    /**
     * 根据属性查找数据
     *
     * @param attribute
     * @param response
     * @return
     */
    @GetMapping("/search")
    @ApiOperation(value = "根据属性查找数据")
    public SystemConfigSearchDTO getSystemConfig(
            @RequestParam(value = "attribute") String attribute,
            @RequestParam(value = "category_identifier") String categoryIdentifier,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        return systemConfigService.getSystemConfigSearchDTO(attribute, categoryIdentifier, currentUser);
    }

    /**
     * 获取所有配置列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping("/search/list")
    @ApiOperation(value = "获取所有配置分页列表")
    public List<SystemConfigDTO> getSystemConfigList(
            @ModelAttribute SystemConfigQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        IPage iPage = this.getPagination();
        IPage<SystemConfigDTO> pm = systemConfigService.getSystemConfigs(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }
}

