package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.querytable.dto.QueryTableDTO;
import com.qy.wf.querytable.dto.QueryTableFormDTO;
import com.qy.wf.querytable.dto.QueryTableQueryDTO;
import com.qy.wf.querytable.entity.QueryTableEntity;
import com.qy.wf.querytable.service.QueryTableService;
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
 * 工作流_设计_查询表单
 *
 * @author hh
 * @since 2022-11-19
 */
@RestController
@Validated
@RequestMapping("/v4/wf/query-tables")
@Api(tags = "工作流_设计_查询表单")
public class QueryTableController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private QueryTableService queryTableService;

    /**
     * 获取工作流_设计_查询表单列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_查询表单分页列表")
    public List<QueryTableDTO> getQueryTables(
        @ModelAttribute QueryTableQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

//        QueryTableQueryDTO newDTO=queryDTO;
//        newDTO.setExtendTableType(1);

        IPage iPage = this.getPagination();
        IPage<QueryTableEntity> pm = queryTableService.getQueryTables(iPage, queryDTO, currentUser);
        List<QueryTableDTO> dtos = queryTableService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取工作流_设计_查询表单列表-设计器设计时使用
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping("/getQueryTablesByTableType")
    @ApiOperation(value = "获取工作流_设计_查询表单分页列表--设计器设计时使用")
    public List<QueryTableDTO> getQueryTablesByTableType(
            @ModelAttribute QueryTableQueryDTO queryDTO,
            HttpServletResponse response
    ) {
//        QueryTableQueryDTO newDTO=queryDTO;
//        newDTO.setExtendTableType(2);

        EmployeeIdentity currentUser = context.getEmployee();
        IPage iPage = this.getPagination();
        IPage<QueryTableEntity> pm = queryTableService.getQueryCustomTable(iPage,2, queryDTO, currentUser);
        List<QueryTableDTO> dtos = queryTableService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }
    /**
     * 获取单个工作流_设计_查询表单
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_查询表单")
    public QueryTableDTO getQueryTable(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        QueryTableEntity queryTableEntity = queryTableService.getQueryTable(id, currentUser);

        return queryTableService.mapperToDTO(queryTableEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_查询表单
     *
     * @param queryTableFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_查询表单")
    public ResponseEntity createQueryTable(
        @Validated @RequestBody QueryTableFormDTO queryTableFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        queryTableService.createQueryTable(queryTableFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_查询表单
     *
     * @param id
     * @param queryTableFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_查询表单")
    public ResponseEntity updateQueryTable(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody QueryTableFormDTO queryTableFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        queryTableService.updateQueryTable(id, queryTableFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_查询表单
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_查询表单")
    public ResponseEntity deleteQueryTable(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        queryTableService.deleteQueryTable(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

