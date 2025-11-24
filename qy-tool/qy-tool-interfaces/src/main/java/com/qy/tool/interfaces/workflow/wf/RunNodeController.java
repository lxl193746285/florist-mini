package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import com.qy.wf.runNode.dto.ReplaceApproverFormDTO;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.runNode.dto.RunNodeDTO;
import com.qy.wf.runNode.dto.RunNodeFormDTO;
import com.qy.wf.runNode.dto.RunNodeQueryDTO;
import com.qy.wf.runNode.entity.RunNodeEntity;
import com.qy.wf.runNode.service.RunNodeService;
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
 * @author wch
 * @since 2022-11-17
 */
@RestController
@Validated
@RequestMapping("/v4/wf/run-nodes")
@Api(tags = "工作流_执行_节点")
public class RunNodeController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private RunNodeService runNodeService;

    /**
     * 获取工作流_执行_节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_执行_节点分页列表")
    public List<RunNodeDTO> getRunNodes(
        @ModelAttribute RunNodeQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<RunNodeEntity> pm = runNodeService.getRunNodes(iPage, queryDTO, currentUser);
        List<RunNodeDTO> dtos = runNodeService.mapperToDTO(pm.getRecords(), currentUser);
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
    public RunNodeDTO getRunNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        RunNodeEntity runNodeEntity = runNodeService.getRunNode(id, currentUser);

        return runNodeService.mapperToDTO(runNodeEntity, currentUser);
    }

    /**
     * 创建单个工作流_执行_节点
     *
     * @param runNodeFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_执行_节点")
    public ResponseEntity createRunNode(
        @Validated @RequestBody RunNodeFormDTO runNodeFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        runNodeService.createRunNode(runNodeFormDTO, currentUser);
        return ResponseUtils.ok("工作流_执行_节点新增成功").build();
    }

    /**
     * 修改单个工作流_执行_节点
     *
     * @param id
     * @param runNodeFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_执行_节点")
    public ResponseEntity updateRunNode(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody RunNodeFormDTO runNodeFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        runNodeService.updateRunNode(id, runNodeFormDTO, currentUser);
        return ResponseUtils.ok("工作流_执行_节点修改成功").build();
    }

    /**
     * 删除单个工作流_执行_节点
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_执行_节点")
    public ResponseEntity deleteRunNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        runNodeService.deleteRunNode(id, currentUser);
        return ResponseUtils.ok("工作流_执行_节点删除成功").build();
    }

    /**
     * 更换工作流_执行_节点的办理人
     *
     * @param approverFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/replace-approver")
    @ApiOperation(value = "更换工作流_执行_节点的办理人")
    public ResponseEntity replaceRunNodeApprover(
            @Validated @RequestBody ReplaceApproverFormDTO approverFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        runNodeService.replaceRunNodeApprover(approverFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

