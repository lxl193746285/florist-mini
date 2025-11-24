package com.qy.tool.interfaces.workflow.wf;

import com.google.common.base.Strings;
import com.qy.common.BaseController;
import com.qy.message.api.dto.MessageLogDTO;
import com.qy.rest.exception.ValidationException;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.runWf.dto.*;
import com.qy.wf.runWf.service.RunWfService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author wch
 * @since 2022-11-17
 */
@RestController
@Validated
@RequestMapping("/v4/wf/run-wfs")
@Api(tags = "工作流_执行_工作流")
public class RunWfController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private RunWfService runWfService;

    /**
     * 获取工作流_执行_工作流列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_执行_工作流分页列表")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf', 'index')")
    public List<RunWfDTO> getRunWfs(
        @ModelAttribute RunWfQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<RunWfDTO> pm = runWfService.getRunWfs(iPage, queryDTO, currentUser);
        List<RunWfDTO> dtos = runWfService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_执行_工作流
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_执行_工作流")
    public RunWfDTO getRunWf(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RunWfDTO runWfDTO = runWfService.getRunWfDTO(id, currentUser);
        return runWfService.mapperToDTO(runWfDTO, currentUser);
    }

    /**
     * 工作流-导出
     */
    @GetMapping("/export")
    @ApiOperation(value = "执行工作流-导出")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf', 'export')")
    public ResponseEntity export(
            @ModelAttribute RunWfQueryDTO queryDTO,
            HttpServletResponse response
    ){
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        if (Strings.isNullOrEmpty(queryDTO.getBeginTimeStart()) ||Strings.isNullOrEmpty(queryDTO.getBeginTimeEnd())) {
            throw new ValidationException("请选择发起时间");
        }
        LocalDate start = LocalDate.parse(queryDTO.getBeginTimeStart());
        LocalDate endTime = LocalDate.parse(queryDTO.getBeginTimeEnd());

        if (start.plusMonths(1).isBefore(endTime)) {
            throw new com.qy.rest.exception.ValidationException("发起时间间隔不能超过1个月");
        }
        List<RunWfDTO> runWfDTOList = runWfService.getRunWfs(queryDTO, currentUser);
        runWfService.export(runWfDTOList, response, currentUser);

        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 获取工作流-明细列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping("/detail/list")
    @ApiOperation(value = "获取工作流明细-分页列表")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf_detail', 'index')")
    public List<RunWfListDTO> getRunWfList(
            @ModelAttribute RunWfQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<RunWfListDTO> pm = runWfService.getRunWfDetailList(iPage, queryDTO, currentUser);
        List<RunWfListDTO> dtos = runWfService.mapperDetailListToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 工作流明细-导出
     */
    @GetMapping("/export/list")
    @ApiOperation(value = "工作流明细-导出")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf_detail', 'export')")
    public ResponseEntity exportList(
            @ModelAttribute RunWfQueryDTO queryDTO,
            HttpServletResponse response
    ){
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        List<RunWfListDTO> runWfEntities= runWfService.getRunWfExportList(queryDTO,currentUser);
        runWfService.exportList(runWfEntities,response,currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 获取单个执行工作流-明细的详情
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取执行工作流明细的详情")
    public RunWfDetailDTO getRunWfDetail(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        RunWfDetailDTO runWfDetailDTO = runWfService.getRunWfDetail(id, currentUser);

        return runWfDetailDTO;
    }

    /**
     * 执行工作流详情--消息推送
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/messageSend/{id}")
    @ApiOperation(value = "获取单个工作流_执行_工作流")
    public List<MessageLogDTO> getMessageSend(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        List<MessageLogDTO> messageLogDTOList = runWfService.getMessageSend(id, currentUser);

        return messageLogDTOList;
    }

    /**
     * 创建单个工作流_执行_工作流
     *
     * @param runWfFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_执行_工作流")
    public ResponseEntity createRunWf(
        @Validated @RequestBody RunWfFormDTO runWfFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        runWfService.createRunWf(runWfFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }


//    /**
//     * 创建单个工作流_执行_工作流
//     *
//     * @param
//     * @param response
//     * @return
//     */
//    @PostMapping("/add")
//    @ApiOperation(value = "创建单个工作流_执行_工作流")
//    public ResponseEntity createRunWfs(
//            HttpServletResponse response
//    ) {
//        MemberIdentity currentUser = memberSystemSessionContext.getMember();
////        RestUtils.validation(result);
//
//        runWfService.createRunWfs(currentUser);
//        return ResponseUtils.ok("操作成功").build();
//    }

    /**
     * 修改单个工作流_执行_工作流
     *
     * @param id
     * @param runWfFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_执行_工作流")
    public ResponseEntity updateRunWf(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody RunWfFormDTO runWfFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        runWfService.updateRunWf(id, runWfFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_执行_工作流
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_执行_工作流")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf', 'delete')")
    public ResponseEntity deleteRunWf(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        runWfService.deleteRunWf(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    @PostMapping("/deleteRunWfRepair/{id}")
    @ApiOperation(value = "恢复删除数据")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_run_wf', 'delete_repair')")
    public ResponseEntity deleteRunWfRepair(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        runWfService.deleteRunWfRepair(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 获取指定工作流节点操作按钮的api
     *
     * @param runNodeId 执行节点id
     * @return
     */
    @GetMapping("/api/wf-action/{id}")
    @ApiOperation(value = "获取指定工作流节点操作按钮的api")
    public List<Action> getRunWfActionListForClient(
            @PathVariable(value = "id") Long runNodeId,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        List<Action> actions = currentUser.getActions("wf_run_wf");
        return runWfService.getRunWfActionList(1, runNodeId, actions, currentUser);
    }

    /**
     * 获取多个工作流节点操作按钮的api
     *
     * @param actionFormDTO
     * @return
     */
    @PostMapping("/api/wf-actions")
    @ApiOperation(value = "获取多个工作流节点操作按钮的api")
    public List<RWActionDTO> getRunWfExistActionsList(
            @Validated @RequestBody RWActionFormDTO actionFormDTO
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        if (actionFormDTO.getActions() == null) {
            List<Action> actions = currentUser.getActions("wf_run_wf");
            actionFormDTO.setActions(actions);
        }
        return runWfService.getRunWfActionListByRunNodeIds(1, actionFormDTO.getRunNodeIds(), actionFormDTO.getActions(), currentUser);
    }

}

