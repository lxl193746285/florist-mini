package com.qy.tool.interfaces.workflow.workflow;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.WfDefNodeRelationDTO;
import com.qy.workflow.dto.WfDefNodeRelationFormDTO;
import com.qy.workflow.dto.WfDefNodeRelationQueryDTO;
import com.qy.workflow.entity.WfDefNodeRelationEntity;
import com.qy.workflow.service.WfDefNodeRelationService;
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
 * 工作流_设计_节点关系
 *
 * @author iFeng
 * @since 2022-11-15
 */
@RestController
@Validated
@RequestMapping("/v4/platform/workflow/wf-def-node-relations")
@Api(tags = "工作流_设计_节点关系")
public class WfDefNodeRelationController  extends BaseController {

    @Autowired
    private WfDefNodeRelationService wfDefNodeRelationService;

    /**
     * 获取工作流_设计_节点关系列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点关系分页列表")
    public List<WfDefNodeRelationDTO> getWfDefNodeRelations(
        @ModelAttribute WfDefNodeRelationQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<WfDefNodeRelationEntity> pm = wfDefNodeRelationService.getWfDefNodeRelations(iPage, queryDTO, currentUser);
        List<WfDefNodeRelationDTO> dtos = wfDefNodeRelationService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_节点关系
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_节点关系")
    public WfDefNodeRelationDTO getWfDefNodeRelation(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        WfDefNodeRelationEntity wfDefNodeRelationEntity = wfDefNodeRelationService.getWfDefNodeRelation(id, currentUser);

        return wfDefNodeRelationService.mapperToDTO(wfDefNodeRelationEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点关系
     *
     * @param wfDefNodeRelationFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点关系")
    public ResponseEntity createWfDefNodeRelation(
        @Validated @RequestBody WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfDefNodeRelationService.createWfDefNodeRelation(wfDefNodeRelationFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_节点关系
     *
     * @param id
     * @param wfDefNodeRelationFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点关系")
    public ResponseEntity updateWfDefNodeRelation(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfDefNodeRelationService.updateWfDefNodeRelation(id, wfDefNodeRelationFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_设计_节点关系
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_节点关系")
    public ResponseEntity deleteWfDefNodeRelation(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        wfDefNodeRelationService.deleteWfDefNodeRelation(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

