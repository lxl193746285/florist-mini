package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseFormDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseQueryDTO;
import com.qy.wf.nodeRepulse.entity.DefNodeRepulseEntity;
import com.qy.wf.nodeRepulse.service.DefNodeRepulseService;
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
 * 工作流_设计_打回节点
 *
 * @author syf
 * @since 2023-08-16
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-node-repulses")
@Api(tags = "工作流_设计_打回节点")
public class DefNodeRepulseController extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeRepulseService defNodeRepulseService;

    /**
     * 获取工作流_设计_打回节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_打回节点分页列表")
    public List<DefNodeRepulseDTO> getDefNodeRepulses(
        @ModelAttribute DefNodeRepulseQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeRepulseEntity> pm = defNodeRepulseService.getDefNodeRepulses(iPage, queryDTO, currentUser);

        List<DefNodeRepulseDTO> dtos = defNodeRepulseService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_打回节点
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_打回节点")
    public DefNodeRepulseDTO getDefNodeRepulse(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeRepulseEntity defNodeRepulseEntity = defNodeRepulseService.getDefNodeRepulse(id, currentUser);

        return defNodeRepulseService.mapperToDTO(defNodeRepulseEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_打回节点
     *
     * @param defNodeRepulseFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_打回节点")
    public ResponseEntity createDefNodeRepulse(
        @Validated @RequestBody DefNodeRepulseFormDTO defNodeRepulseFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeRepulseService.createDefNodeRepulse(defNodeRepulseFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_打回节点
     *
     * @param id
     * @param defNodeRepulseFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_打回节点")
    public ResponseEntity updateDefNodeRepulse(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeRepulseFormDTO defNodeRepulseFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeRepulseService.updateDefNodeRepulse(id, defNodeRepulseFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_打回节点
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_打回节点")
    public ResponseEntity deleteDefNodeRepulse(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeRepulseService.deleteDefNodeRepulse(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

