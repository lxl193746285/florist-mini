package com.qy.tool.interfaces.workflow.workflow;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.WfRunVarDTO;
import com.qy.workflow.dto.WfRunVarFormDTO;
import com.qy.workflow.dto.WfRunVarQueryDTO;
import com.qy.workflow.entity.WfRunVarEntity;
import com.qy.workflow.service.WfRunVarService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * 工作流_执行_节点
 *
 * @author iFeng
 * @since 2022-11-23
 */
@RestController
@Validated
@RequestMapping("/v4/platform/workflow/wf-run-vars")
@Api(tags = "工作流_执行_节点")
public class WfRunVarController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private WfRunVarService wfRunVarService;

    /**
     * 获取工作流_执行_节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_执行_节点分页列表")
    public List<WfRunVarDTO> getWfRunVars(
        @ModelAttribute WfRunVarQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<WfRunVarEntity> pm = wfRunVarService.getWfRunVars(iPage, queryDTO, currentUser);
        List<WfRunVarDTO> dtos = wfRunVarService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_执行_节点
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_执行_节点")
    public WfRunVarDTO getWfRunVar(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        WfRunVarEntity wfRunVarEntity = wfRunVarService.getWfRunVar(id, currentUser);

        return wfRunVarService.mapperToDTO(wfRunVarEntity, currentUser);
    }

    /**
     * 创建单个工作流_执行_节点
     *
     * @param wfRunVarFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_执行_节点")
    public ResponseEntity createWfRunVar(
        @Validated @RequestBody WfRunVarFormDTO wfRunVarFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunVarService.createWfRunVar(wfRunVarFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_执行_节点
     *
     * @param id
     * @param wfRunVarFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_执行_节点")
    public ResponseEntity updateWfRunVar(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody WfRunVarFormDTO wfRunVarFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunVarService.updateWfRunVar(id, wfRunVarFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_执行_节点
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_执行_节点")
    public ResponseEntity deleteWfRunVar(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        wfRunVarService.deleteWfRunVar(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

