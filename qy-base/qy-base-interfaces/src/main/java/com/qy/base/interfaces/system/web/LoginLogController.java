package com.qy.base.interfaces.system.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.loginlog.dto.LoginLogDTO;
import com.qy.system.app.loginlog.dto.LoginLogFormDTO;
import com.qy.system.app.loginlog.dto.LoginLogQueryDTO;
import com.qy.system.app.loginlog.service.LoginLogService;
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
 * 系统登录日志
 *
 * @author wwd
 * @since 2024-04-18
 */
@RestController
@Validated
@RequestMapping("/v4/system/login-logs")
@Api(tags = "系统登录日志")
public class LoginLogController extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private MemberSystemSessionContext memberContext;
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 获取系统登录日志列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
//    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_sys_login_log', 'index')")
    @ApiOperation(value = "获取系统登录日志分页列表")
    public List<LoginLogDTO> getLoginLogs(
        @ModelAttribute LoginLogQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<LoginLogDTO> pm = loginLogService.getLoginLogs(iPage, queryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return pm.getRecords();
    }

    /**
     * 获取单个系统登录日志
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
//    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_sys_login_log', 'view')")
    @ApiOperation(value = "获取单个系统登录日志")
    public LoginLogDTO getLoginLog(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        return loginLogService.getLoginLogDTO(id, currentUser);
    }


    /**
     * 删除单个系统登录日志
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('ark_sys_login_log', 'delete')")
    @ApiOperation(value = "删除单个系统登录日志")
    public ResponseEntity deleteLoginLog(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        loginLogService.deleteLoginLog(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }

    @GetMapping("/getLastLog")
    @ApiOperation(value = "根据当前人获取最后一次登录信息")
    public LoginLogDTO getLastLog(
            HttpServletResponse response
    ) {
        MemberIdentity member = memberContext.getMember();
        LoginLogDTO loginLogDTO = loginLogService.getLastLog(member);
        return loginLogDTO;
    }
}

