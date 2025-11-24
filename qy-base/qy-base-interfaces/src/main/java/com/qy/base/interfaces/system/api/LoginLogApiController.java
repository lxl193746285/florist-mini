package com.qy.base.interfaces.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.loginlog.dto.LoginLogDTO;
import com.qy.system.app.loginlog.dto.LoginLogFormDTO;
import com.qy.system.app.loginlog.dto.LoginLogQueryDTO;
import com.qy.system.app.loginlog.entity.LoginLogEntity;
import com.qy.system.app.loginlog.service.LoginLogService;
import com.qy.system.app.util.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v4/api/system/login-logs")
public class LoginLogApiController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 创建单个系统登录日志
     *
     * @param loginLogFormDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个系统登录日志")
    public Long createLoginLog(
        @RequestBody LoginLogFormDTO loginLogFormDTO
    ) {

        LoginLogEntity entity = loginLogService.createLoginLog(loginLogFormDTO, null);
        return entity.getId();
    }
}

