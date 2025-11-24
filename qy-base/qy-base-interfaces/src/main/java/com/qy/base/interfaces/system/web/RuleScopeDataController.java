package com.qy.base.interfaces.system.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.loginlog.dto.LoginLogDTO;
import com.qy.system.app.loginlog.dto.LoginLogQueryDTO;
import com.qy.system.app.loginlog.service.LoginLogService;
import com.qy.system.app.scopedata.service.ScopeDataService;
import com.qy.system.app.util.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 规则范围数据
 *
 * @author wwd
 * @since 2024-04-18
 */
@RestController
@Validated
@RequestMapping("/v4/system/rule-scopes")
@Api(tags = "规则范围数据")
public class RuleScopeDataController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private ScopeDataService scopeDataService;

    @ApiOperation("获取指定规则范围可选择的数据")
    @GetMapping("/{id}/select-data")
    public Object getRuleScopeSelectData(
            @PathVariable(value = "id") String id
    ) {
        return scopeDataService.getScopeData(id);
    }
}

