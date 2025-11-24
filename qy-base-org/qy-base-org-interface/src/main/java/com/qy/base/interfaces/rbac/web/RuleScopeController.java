package com.qy.base.interfaces.rbac.web;

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
@Api(tags = "规则范围")
@RestController
@RequestMapping("/v4/rbac/rule-scopes")
public class RuleScopeController {
    private SessionContext sessionContext;
    private RuleScopeCommandService ruleScopeCommandService;
    private RuleScopeQueryService ruleScopeQueryService;

    public RuleScopeController(SessionContext sessionContext, RuleScopeCommandService ruleScopeCommandService, RuleScopeQueryService ruleScopeQueryService) {
        this.sessionContext = sessionContext;
        this.ruleScopeCommandService = ruleScopeCommandService;
        this.ruleScopeQueryService = ruleScopeQueryService;
    }

    /**
     * 获取规则范围列表
     *
     * @return
     */
    @ApiOperation("获取规则范围列表")
    @GetMapping
    public ResponseEntity<List<RuleScopeDTO>> getRuleScopes(RuleScopeQuery query) {
        List<RuleScopeDTO> ruleScopeDTOS = ruleScopeQueryService.getRuleScopes(query, sessionContext.getUser());

        return ResponseUtils.ok().body(ruleScopeDTOS);
    }

    /**
     * 获取单个规则范围
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个规则范围")
    @GetMapping("/{id}")
    public ResponseEntity<RuleScopeDTO> getRuleScope(
        @PathVariable(value = "id") String id
    ) {
        RuleScopeDTO ruleScopeDTO = ruleScopeQueryService.getRuleScopeById(id);
        if (ruleScopeDTO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        return ResponseUtils.ok().body(ruleScopeDTO);
    }

    /**
     * 创建单个规则范围
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个规则范围")
    @PostMapping
    public ResponseEntity<Object> createRuleScope(
            @Valid @RequestBody CreateRuleScopeCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        ruleScopeCommandService.createRuleScope(command);

        return ResponseUtils.ok("规则范围创建成功").build();
    }

    /**
     * 修改单个规则范围
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个规则范围")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateRuleScope(
        @PathVariable(value = "id") String id,
        @Valid @RequestBody UpdateRuleScopeCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        RuleScopeDTO ruleScopeDTO = getRuleScopeById(id);

        command.setUpdateId(id);
        ruleScopeCommandService.updateRuleScope(command);

        return ResponseUtils.ok("规则范围修改成功").build();
    }

    /**
     * 删除单个规则范围
     *
     * @param id
     */
    @ApiOperation("删除单个规则范围")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRuleScope(
        @PathVariable(value = "id") String id
    ) {
        RuleScopeDTO ruleScopeDTO = getRuleScopeById(id);
        DeleteRuleScopeCommand command = new DeleteRuleScopeCommand();
        command.setId(ruleScopeDTO.getId());
        ruleScopeCommandService.deleteRuleScope(command);

        return ResponseUtils.noContent("删除规则范围成功").build();
    }

    /**
     * 获取指定规则范围可选择的数据
     *
     * @param id
     * @return
     */
    @ApiOperation("获取指定规则范围可选择的数据")
    @GetMapping("/{id}/select-data")
    public ResponseEntity<Object> getRuleScopeSelectData(
            @PathVariable(value = "id") String id
    ) {
        Identity identity = sessionContext.getUser();
        RuleScopeDTO ruleScopeDTO = ruleScopeQueryService.getRuleScopeById(id);
        if (ruleScopeDTO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        return ResponseUtils.ok().body(ruleScopeQueryService.getRuleScopeSelectData(id, identity));
    }

    /**
     * 根据id获取规则范围
     *
     * @param id
     * @return
     */
    private RuleScopeDTO getRuleScopeById(String id) {
        RuleScopeDTO ruleScopeDTO = ruleScopeQueryService.getRuleScopeById(id);
        if (ruleScopeDTO == null) {
            throw new NotFoundException("未找到指定的规则范围");
        }
        return ruleScopeDTO;
    }
}

