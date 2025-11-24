package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationFormDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationQueryDTO;
import com.qy.wf.defNodeRelation.entity.DefNodeRelationEntity;
import com.qy.wf.defNodeRelation.service.DefNodeRelationService;
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
 * @author syf
 * @since 2022-11-14
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-node-relations")
@Api(tags = "工作流_设计_节点关系")
public class DefNodeRelationController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeRelationService defNodeRelationService;

    /**
     * 获取工作流_设计_节点关系列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点关系分页列表")
    public List<DefNodeRelationDTO> getDefNodeRelations(
        @ModelAttribute DefNodeRelationQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeRelationEntity> pm = defNodeRelationService.getDefNodeRelations(iPage, queryDTO, currentUser);

        List<DefNodeRelationDTO> dtos = defNodeRelationService.mapperToDTO(pm.getRecords(), currentUser);
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
    public DefNodeRelationDTO getDefNodeRelation(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeRelationEntity defNodeRelationEntity = defNodeRelationService.getDefNodeRelation(id, currentUser);

        return defNodeRelationService.mapperToDTO(defNodeRelationEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点关系
     *
     * @param defNodeRelationFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点关系")
    public ResponseEntity createDefNodeRelation(
        @Validated @RequestBody DefNodeRelationFormDTO defNodeRelationFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeRelationService.createDefNodeRelation(defNodeRelationFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_节点关系
     *
     * @param id
     * @param defNodeRelationFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点关系")
    public ResponseEntity updateDefNodeRelation(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeRelationFormDTO defNodeRelationFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeRelationService.updateDefNodeRelation(id, defNodeRelationFormDTO, currentUser);
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
    public ResponseEntity deleteDefNodeRelation(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeRelationService.deleteDefNodeRelation(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

