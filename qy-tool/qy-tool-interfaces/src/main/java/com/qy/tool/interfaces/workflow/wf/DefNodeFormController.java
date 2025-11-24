package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defNodeForm.dto.DefNodeFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormQueryDTO;
import com.qy.wf.defNodeForm.entity.DefNodeFormEntity;
import com.qy.wf.defNodeForm.service.DefNodeFormService;
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
 * 工作流_设计_表单
 *
 * @author syf
 * @since 2022-11-14
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-node-forms")
@Api(tags = "工作流_设计_表单")
public class DefNodeFormController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeFormService defNodeFormService;

    /**
     * 获取工作流_设计_表单列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_表单分页列表")
    public List<DefNodeFormDTO> getDefNodeForms(
        @ModelAttribute DefNodeFormQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeFormEntity> pm = defNodeFormService.getDefNodeForms(iPage, queryDTO, currentUser);

        List<DefNodeFormDTO> dtos = defNodeFormService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_表单
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_表单")
    public DefNodeFormDTO getDefNodeForm(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeFormEntity defNodeFormEntity = defNodeFormService.getDefNodeForm(id, currentUser);

        return defNodeFormService.mapperToDTO(defNodeFormEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_表单
     *
     * @param defNodeFormFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_表单")
    public ResponseEntity createDefNodeForm(
        @Validated @RequestBody DefNodeFormFormDTO defNodeFormFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeFormService.createDefNodeForm(defNodeFormFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_表单
     *
     * @param id
     * @param defNodeFormFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_表单")
    public ResponseEntity updateDefNodeForm(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeFormFormDTO defNodeFormFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeFormService.updateDefNodeForm(id, defNodeFormFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_设计_表单
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_表单")
    public ResponseEntity deleteDefNodeForm(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeFormService.deleteDefNodeForm(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

