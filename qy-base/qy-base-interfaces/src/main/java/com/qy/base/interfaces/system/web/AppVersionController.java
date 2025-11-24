package com.qy.base.interfaces.system.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.system.app.util.RestUtils;
import com.qy.system.app.version.dto.AppVersionDTO;
import com.qy.system.app.version.dto.AppVersionFormDTO;
import com.qy.system.app.version.dto.AppVersionQueryDTO;
import com.qy.system.app.version.service.AppVersionService;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.app.comment.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * APP版本
 *
 * @author syf
 * @since 2024-05-21
 */
@RestController
@Validated
@RequestMapping("/v4/system/app-versions")
@Api(tags = "APP版本")
public class AppVersionController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private AppVersionService appVersionService;

    /**
     * 获取APP版本列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_app_version', 'index')")
    @ApiOperation(value = "获取APP版本分页列表")
    public List<AppVersionDTO> getAppVersions(
        @ModelAttribute AppVersionQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        IPage iPage = this.getPagination();
        IPage<AppVersionDTO> pm = appVersionService.getAppVersions(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }

    /**
     * 获取单个APP版本
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_app_version', 'view')")
    @ApiOperation(value = "获取单个APP版本")
    public AppVersionDTO getAppVersion(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        return appVersionService.getAppVersionDTO(id, currentUser);
    }

    /**
     * 创建单个APP版本
     *
     * @param appVersionFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_app_version', 'create')")
    @ApiOperation(value = "创建单个APP版本")
    public ResponseEntity<Object> createAppVersion(
        @Validated @RequestBody AppVersionFormDTO appVersionFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        appVersionService.createAppVersion(appVersionFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个APP版本
     *
     * @param id
     * @param appVersionFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_app_version', 'update')")
    @ApiOperation(value = "修改单个APP版本")
    public ResponseEntity<Object> updateAppVersion(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody AppVersionFormDTO appVersionFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        appVersionService.updateAppVersion(id, appVersionFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个APP版本
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('st_system_app_version', 'delete')")
    @ApiOperation(value = "删除单个APP版本")
    public ResponseEntity<Object> deleteAppVersion(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = context.getMember();

        appVersionService.deleteAppVersion(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }

    /**
     * 获取APP最新版本列表
     *
     * @param type
     * @param itemType
     * @return
     */
    @GetMapping("/latest")
    @ApiOperation(value = "获取APP最新版本列表")
    public AppVersionDTO getAppVersion(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "item_type") String itemType,
            HttpServletResponse response
    ) {
        AppVersionDTO appVersionDTO = appVersionService.getNewVersion(type, itemType);
        return appVersionDTO;
    }

}

