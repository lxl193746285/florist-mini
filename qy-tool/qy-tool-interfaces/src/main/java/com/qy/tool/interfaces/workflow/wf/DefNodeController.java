package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.dto.DefNodeDTO;
import com.qy.wf.defNode.dto.DefNodeFormNodeDTO;
import com.qy.wf.defNode.dto.DefNodeQueryDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNode.service.DefNodeService;
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
 * 工作流_设计_节点
 *
 * @author syf
 * @since 2022-11-14
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-nodes")
@Api(tags = "工作流_设计_节点")
public class DefNodeController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeService defNodeService;

    /**
     * 获取工作流_设计_节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点分页列表")
    public List<DefNodeDTO> getDefNodes(
        @ModelAttribute DefNodeQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeEntity> pm = defNodeService.getDefNodes(iPage, queryDTO, currentUser);

        List<DefNodeDTO> dtos = defNodeService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    @GetMapping("/select")
    @ApiOperation(value = "获取工作流_设计_节点分页列表-下拉用")
    public List<DefNodeDTO> getDefNodesSelect(
            @ModelAttribute DefNodeQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeEntity> pm = defNodeService.getDefNodesSelect(iPage, queryDTO, currentUser);

        List<DefNodeDTO> dtos = defNodeService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_节点
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_节点")
    public DefNodeDTO getDefNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeEntity defNodeEntity = defNodeService.getDefNode(id, currentUser);

        return defNodeService.mapperToDTO(defNodeEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点
     *
     * @param defNodeFormNodeDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点")
    public ResponseEntity createDefNode(
        @Validated @RequestBody DefNodeFormNodeDTO defNodeFormNodeDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeService.createDefNode(defNodeFormNodeDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_节点
     *
     * @param id
     * @param defNodeFormNodeDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点")
    public ResponseEntity updateDefNode(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeFormNodeDTO defNodeFormNodeDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeService.updateDefNode(id, defNodeFormNodeDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_设计_节点
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_节点")
    public ResponseEntity deleteDefNode(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeService.deleteDefNode(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }

    @GetMapping("/getByWfId/{wfId}")
    @ApiOperation(value = "根据工作流id获取节点数据")
    public List<DefNodeDTO> getNodesByWfId(
            @PathVariable(value = "wfId") Long wfId,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        List<DefNodeDTO> nodeDTOList = defNodeService.getNodesByWfId(wfId, currentUser);
        return nodeDTOList;
    }

    /**
     * 根据设计节点获取节点信息
     */
    @GetMapping("/getByNodeCode/{node_code}")
    @ApiOperation(value = "根据设计节点获取节点信息")
    public DefNodeDTO getByNodeCode(
            @RequestParam(value = "type") Integer type,
            @PathVariable(value = "node_code") String nodeCode,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        //type:1,节点查询；type:2,连线查询
        DefNodeDTO defNodeDTO = defNodeService.getByNodeCode(nodeCode,type, currentUser);
        return defNodeDTO;
    }

    /**
     * 批量保存工作流相关信息
     * @param defNodeBacthDTO
     * @param result
     * @param response
     * @return
     */
    @PostMapping("/batch-create")
    @ApiOperation(value = "批量保存工作流相关信息")
    public ResponseEntity batchCreate(
            @Validated @RequestBody DefNodeBacthDTO defNodeBacthDTO,
            BindingResult result,
            HttpServletResponse response
            ) {

        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeService.batchCreateDefNode(defNodeBacthDTO, currentUser);
        return ResponseUtils.ok("保存成功").build();
    }

//    @GetMapping("/test")
//    @ApiOperation(value = "测试")
//    public List<DefNodeDTO> test(
//            @ModelAttribute DefNodeQueryDTO queryDTO,
//            HttpServletResponse response
//    ) {
//        EmployeeIdentity currentUser = context.getEmployee();
//
//        IPage iPage = this.getPagination();
//        IPage<DefNodeEntity> pm = defNodeService.getDefNodes(iPage, queryDTO, currentUser);
//
//        List<DefNodeDTO> dtos = defNodeService.mapperToDTO(pm.getRecords(), currentUser);
//        RestUtils.initResponseByPage(pm, response);
//        return dtos;
//    }
}

