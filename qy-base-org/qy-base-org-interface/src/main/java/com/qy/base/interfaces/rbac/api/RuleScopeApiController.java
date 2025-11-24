package com.qy.base.interfaces.rbac.api;

import com.qy.rbac.app.application.command.CreateRuleScopeCommand;
import com.qy.rbac.app.application.command.DeleteRuleScopeCommand;
import com.qy.rbac.app.application.command.UpdateRuleScopeCommand;
import com.qy.rbac.app.application.dto.RuleScopeDTO;
import com.qy.rbac.app.application.query.RuleScopeQuery;
import com.qy.rbac.app.application.service.RuleScopeCommandService;
import com.qy.rbac.app.application.service.RuleScopeQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 规则范围
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/rbac/rule-scopes")
public class RuleScopeApiController {
    private SessionContext sessionContext;
    private RuleScopeQueryService ruleScopeQueryService;

    public RuleScopeApiController(SessionContext sessionContext, RuleScopeQueryService ruleScopeQueryService) {
        this.sessionContext = sessionContext;
        this.ruleScopeQueryService = ruleScopeQueryService;
    }

    /**
     * 获取单个规则范围
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RuleScopeDTO getRuleScope(
        @PathVariable(value = "id") String id
    ) {
        RuleScopeDTO ruleScopeDTO = ruleScopeQueryService.getRuleScopeById(id);
        if (ruleScopeDTO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        return ruleScopeDTO;
    }
}

