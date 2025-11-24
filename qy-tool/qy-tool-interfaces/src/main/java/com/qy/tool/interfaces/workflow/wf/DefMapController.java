package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defMap.dto.DefMapDTO;
import com.qy.wf.defMap.dto.DefMapFormDTO;
import com.qy.wf.defMap.dto.DefMapQueryDTO;
import com.qy.wf.defMap.entity.DefMapEntity;
import com.qy.wf.defMap.service.DefMapService;
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
 * 工作流_设计_映射表 记录业务表与工作流id关联
 *
 * @author syf
 * @since 2022-11-21
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-maps")
@Api(tags = "工作流_设计_映射表 记录业务表与工作流id关联")
public class DefMapController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefMapService defMapService;

    /**
     * 获取工作流_设计_映射表 记录业务表与工作流id关联列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_映射表 记录业务表与工作流id关联分页列表")
    public List<DefMapDTO> getDefMaps(
        @ModelAttribute DefMapQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefMapEntity> pm = defMapService.getDefMaps(iPage, queryDTO, currentUser);

        List<DefMapDTO> dtos = defMapService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_映射表 记录业务表与工作流id关联")
    public DefMapDTO getDefMap(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefMapEntity defMapEntity = defMapService.getDefMap(id, currentUser);

        return defMapService.mapperToDTO(defMapEntity, currentUser);
    }

    /**
     * 根据工作流id获取详情
     * @param wfId
     * @param response
     * @return
     */
    @GetMapping("/getByWfId/{wf_id}")
    @ApiOperation(value = "根据工作流id获取详情")
    public DefMapDTO getDefMapByWfId(
            @PathVariable(value = "wf_id") Long wfId,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefMapEntity defMapEntity = defMapService.getDefMapByWfId(wfId, currentUser);

        return defMapService.mapperToDTO(defMapEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param defMapFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_映射表 记录业务表与工作流id关联")
    public ResponseEntity createDefMap(
        @Validated @RequestBody DefMapFormDTO defMapFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defMapService.createDefMap(defMapFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param id
     * @param defMapFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_映射表 记录业务表与工作流id关联")
    public ResponseEntity updateDefMap(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefMapFormDTO defMapFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defMapService.updateDefMap(id, defMapFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_映射表 记录业务表与工作流id关联")
    public ResponseEntity deleteDefMap(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defMapService.deleteDefMap(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

