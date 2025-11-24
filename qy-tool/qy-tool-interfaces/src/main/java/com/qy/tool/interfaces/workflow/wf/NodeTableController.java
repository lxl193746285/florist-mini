package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.nodetable.dto.NodeTableDTO;
import com.qy.wf.nodetable.dto.NodeTableFormDTO;
import com.qy.wf.nodetable.dto.NodeTableQueryDTO;
import com.qy.wf.nodetable.entity.NodeTableEntity;
import com.qy.wf.nodetable.service.NodeTableService;
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
 * 工作流_设计_节点表单
 *
 * @author hh
 * @since 2022-11-19
 */
@RestController
@Validated
@RequestMapping("/v4/wf/node-tables")
@Api(tags = "工作流_设计_节点表单")
public class NodeTableController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private NodeTableService nodeTableService;

    /**
     * 获取工作流_设计_节点表单列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点表单分页列表")
    public List<NodeTableDTO> getNodeTables(
        @ModelAttribute NodeTableQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<NodeTableEntity> pm = nodeTableService.getNodeTables(iPage, queryDTO, currentUser);
        List<NodeTableDTO> dtos = nodeTableService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_节点表单
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_节点表单")
    public NodeTableDTO getNodeTable(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        NodeTableEntity nodeTableEntity = nodeTableService.getNodeTable(id, currentUser);

        return nodeTableService.mapperToDTO(nodeTableEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点表单
     *
     * @param nodeTableFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点表单")
    public ResponseEntity createNodeTable(
        @Validated @RequestBody NodeTableFormDTO nodeTableFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        nodeTableService.createNodeTable(nodeTableFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_节点表单
     *
     * @param id
     * @param nodeTableFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点表单")
    public ResponseEntity updateNodeTable(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody NodeTableFormDTO nodeTableFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        nodeTableService.updateNodeTable(id, nodeTableFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_节点表单
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_节点表单")
    public ResponseEntity deleteNodeTable(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        nodeTableService.deleteNodeTable(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

