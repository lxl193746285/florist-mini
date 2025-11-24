package com.qy.base.interfaces.system.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.useruniquecode.dto.UserUniqueCodeDTO;
import com.qy.system.app.useruniquecode.dto.UserUniqueCodeFormDTO;
import com.qy.system.app.useruniquecode.dto.UserUniqueCodeQueryDTO;
import com.qy.system.app.useruniquecode.service.UserUniqueCodeService;
import com.qy.system.app.util.RestUtils;
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
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@RestController
@Validated
@RequestMapping("/v4/system/user-unique-codes")
@Api(tags = "用户设备唯一码")
public class UserUniqueCodeController extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private UserUniqueCodeService userUniqueCodeService;

    /**
     * 获取用户设备唯一码列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_user_unique_code', 'index')")
    @ApiOperation(value = "获取用户设备唯一码分页列表")
    public List<UserUniqueCodeDTO> getUserUniqueCodes(
        @ModelAttribute UserUniqueCodeQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<UserUniqueCodeDTO> pm = userUniqueCodeService.getUserUniqueCodes(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }

    /**
     * 修改单个用户设备唯一码
     *
     * @param id
     * @param userUniqueCodeFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_user_unique_code', 'update')")
    @ApiOperation(value = "修改单个用户设备唯一码")
    public ResponseEntity updateUserUniqueCode(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody UserUniqueCodeFormDTO userUniqueCodeFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        userUniqueCodeService.updateUserUniqueCode(id, userUniqueCodeFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 获取单个用户设备唯一码
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个用户设备唯一码")
    public UserUniqueCodeDTO getUserUniqueCode(
        @PathVariable(value = "id") Long id
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        return userUniqueCodeService.getUserUniqueCodeDTO(id, currentUser);
    }

    /**
     * 删除单个用户设备唯一码
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_user_unique_code', 'delete')")
    @ApiOperation(value = "删除单个用户设备唯一码")
    public ResponseEntity deleteUserUniqueCode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        userUniqueCodeService.deleteUserUniqueCode(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

