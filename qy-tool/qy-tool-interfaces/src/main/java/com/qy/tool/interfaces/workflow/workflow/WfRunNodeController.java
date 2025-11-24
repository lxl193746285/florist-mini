package com.qy.tool.interfaces.workflow.workflow;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.WfRunNodeDTO;
import com.qy.workflow.dto.WfRunNodeFormDTO;
import com.qy.workflow.dto.WfRunNodeQueryDTO;
import com.qy.workflow.entity.WfRunNodeEntity;
import com.qy.workflow.service.WfRunNodeService;
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
 * @since 2022-11-16
 */
@RestController
@Validated
@RequestMapping("/v4/platform/workflow/wf-run-nodes")
@Api(tags = "工作流_执行_节点")
public class WfRunNodeController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private WfRunNodeService wfRunNodeService;

    /**
     * 获取工作流_执行_节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_执行_节点分页列表")
    public List<WfRunNodeDTO> getWfRunNodes(
        @ModelAttribute WfRunNodeQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<WfRunNodeEntity> pm = wfRunNodeService.getWfRunNodes(iPage, queryDTO, currentUser);
        List<WfRunNodeDTO> dtos = wfRunNodeService.mapperToDTO(pm.getRecords(), currentUser);
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
    public WfRunNodeDTO getWfRunNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        WfRunNodeEntity wfRunNodeEntity = wfRunNodeService.getWfRunNode(id, currentUser);

        return wfRunNodeService.mapperToDTO(wfRunNodeEntity, currentUser);
    }

    /**
     * 创建单个工作流_执行_节点
     *
     * @param wfRunNodeFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_执行_节点")
    public ResponseEntity createWfRunNode(
        @Validated @RequestBody WfRunNodeFormDTO wfRunNodeFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunNodeService.createWfRunNode(wfRunNodeFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_执行_节点
     *
     * @param id
     * @param wfRunNodeFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_执行_节点")
    public ResponseEntity updateWfRunNode(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody WfRunNodeFormDTO wfRunNodeFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunNodeService.updateWfRunNode(id, wfRunNodeFormDTO, currentUser);
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
    public ResponseEntity deleteWfRunNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        wfRunNodeService.deleteWfRunNode(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

