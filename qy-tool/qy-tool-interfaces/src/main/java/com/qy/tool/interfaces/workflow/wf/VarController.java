package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.var.dto.VarDTO;
import com.qy.wf.var.dto.VarFormDTO;
import com.qy.wf.var.dto.VarQueryDTO;
import com.qy.wf.var.entity.VarEntity;
import com.qy.wf.var.service.VarService;
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
 * 工作流_变量
 *
 * @author syf
 * @since 2022-11-21
 */
@RestController
@Validated
@RequestMapping("/v4/wf/vars")
@Api(tags = "工作流_变量")
public class VarController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private VarService varService;

    /**
     * 获取工作流_变量列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_变量分页列表")
    public List<VarDTO> getVars(
        @ModelAttribute VarQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<VarEntity> pm = varService.getVars(iPage, queryDTO, currentUser);

        List<VarDTO> dtos = varService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_变量
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_变量")
    public VarDTO getVar(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        VarEntity varEntity = varService.getVar(id, currentUser);

        return varService.mapperToDTO(varEntity, currentUser);
    }

    /**
     * 创建单个工作流_变量
     *
     * @param varFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_变量")
    public ResponseEntity createVar(
        @Validated @RequestBody VarFormDTO varFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        varService.createVar(varFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_变量
     *
     * @param id
     * @param varFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_变量")
    public ResponseEntity updateVar(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody VarFormDTO varFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        varService.updateVar(id, varFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_变量
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_变量")
    public ResponseEntity deleteVar(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        varService.deleteVar(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

