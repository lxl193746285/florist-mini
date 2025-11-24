package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defNodeEvent.dto.DefNodeEventDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventFormDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventQueryDTO;
import com.qy.wf.defNodeEvent.entity.DefNodeEventEntity;
import com.qy.wf.defNodeEvent.service.DefNodeEventService;
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
 * 工作流_设计_节点事件
 *
 * @author syf
 * @since 2022-11-21
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-node-events")
@Api(tags = "工作流_设计_节点事件")
public class DefNodeEventController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeEventService defNodeEventService;

    /**
     * 获取工作流_设计_节点事件列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点事件分页列表")
    public List<DefNodeEventDTO> getDefNodeEvents(
        @ModelAttribute DefNodeEventQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeEventEntity> pm = defNodeEventService.getDefNodeEvents(iPage, queryDTO, currentUser);

        List<DefNodeEventDTO> dtos = defNodeEventService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_节点事件
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_节点事件")
    public DefNodeEventDTO getDefNodeEvent(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeEventEntity defNodeEventEntity = defNodeEventService.getDefNodeEvent(id, currentUser);

        return defNodeEventService.mapperToDTO(defNodeEventEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点事件
     *
     * @param defNodeEventFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点事件")
    public ResponseEntity createDefNodeEvent(
        @Validated @RequestBody DefNodeEventFormDTO defNodeEventFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeEventService.createDefNodeEvent(defNodeEventFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_节点事件
     *
     * @param id
     * @param defNodeEventFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点事件")
    public ResponseEntity updateDefNodeEvent(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeEventFormDTO defNodeEventFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeEventService.updateDefNodeEvent(id, defNodeEventFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_节点事件
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_节点事件")
    public ResponseEntity deleteDefNodeEvent(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeEventService.deleteDefNodeEvent(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }
}

