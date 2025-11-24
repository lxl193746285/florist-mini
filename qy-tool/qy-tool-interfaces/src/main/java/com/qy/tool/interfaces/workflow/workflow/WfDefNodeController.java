package com.qy.tool.interfaces.workflow.workflow;

import com.qy.common.BaseController;
import com.qy.workflow.entity.WfRunWfEntity;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfDefNodeEntity;
import com.qy.workflow.service.WfDefNodeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作流_设计_节点
 *
 * @author iFeng
 * @since 2022-11-15
 */
@RestController
@Validated
@RequestMapping("/v4/platform/workflow/wf-def-nodes")
@Api(tags = "工作流_设计_节点")
public class WfDefNodeController extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private MemberSystemSessionContext memberSystemSessionContext;
    @Autowired
    private WfDefNodeService wfDefNodeService;

    /**
     * 获取工作流_设计_节点列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点分页列表")
    public List<WfDefNodeDTO> getWfDefNodes(
            @ModelAttribute WfDefNodeQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<WfDefNodeEntity> pm = wfDefNodeService.getWfDefNodes(iPage, queryDTO, currentUser);
        List<WfDefNodeDTO> dtos = wfDefNodeService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    @GetMapping("/getQueryTables")
    @ApiOperation(value = "获取查询表单通过platform")
    public List<WfFilterTable> getQueryTables(
            @RequestParam(value = "wf_id") Long wf_Id,
            @RequestParam(value = "platform") String platform,
            //BindingResult result,
            HttpServletResponse response) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        return wfDefNodeService.getFilterTables(wf_Id, platform, currentUser);
    }


    @GetMapping("/testApplyUser")
    @ApiOperation(value = "测试消息")
    public void testApplyUser(
            @RequestParam(value = "wf_id") Long wf_id,
            @RequestParam(value = "wf_run_id") Long wf_run_id,
            @RequestParam(value = "node_id") Long node_id,
            @RequestParam(value = "wf_status") Integer wf_status,
            @RequestParam(value = "approve_result") Integer approve_result,
            @RequestParam(value = "approve_comments") String approve_comments,
            @RequestParam(value = "apply_userId") Long apply_userId,
            @RequestParam(value = "create_userId") Long create_userId,
            @RequestParam(value = "type") Integer type,
            //BindingResult result,
            HttpServletResponse response) {
        if (type == 1) {
            wfDefNodeService.testApplyUserBegin(wf_id, wf_run_id, node_id, wf_status, approve_result, approve_comments, apply_userId, create_userId);
        } else if (type == 2) {
            wfDefNodeService.testApplyUserProcess(wf_id, wf_run_id, node_id, wf_status, approve_result, approve_comments, apply_userId, create_userId);
        } else if (type == 3) {
            wfDefNodeService.testApplyUserEnd(wf_id, wf_run_id, node_id, wf_status, approve_result, approve_comments, apply_userId, create_userId);
        }
    }

    @GetMapping("/testSendMessageDealUser")
    @ApiOperation(value = "测试办理人消息")
    public void testSendMessageDealUser(
            @RequestParam(value = "wf_id") Long wf_id,
            @RequestParam(value = "wf_run_id") Long wf_run_id,
            @RequestParam(value = "node_id") Long node_id,
            @RequestParam(value = "wf_status") Integer wf_status,
            @RequestParam(value = "approve_result") Integer approve_result,
            @RequestParam(value = "approve_comments") String approve_comments,
            @RequestParam(value = "deal_user_id") Long deal_user_id,
            //BindingResult result,
            HttpServletResponse response) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        wfDefNodeService.testSendMessageDealUser(wf_id, wf_run_id, node_id, deal_user_id, wf_status, approve_result, approve_comments, currentUser);

    }

    @GetMapping("/testSendMessageCcUser")
    @ApiOperation(value = "测试抄送人消息")
    public void testSendMessageCcUser(
            @RequestParam(value = "wf_id") Long wf_id,
            @RequestParam(value = "wf_run_id") Long wf_run_id,
            @RequestParam(value = "node_id") Long node_id,
            @RequestParam(value = "wf_status") Integer wf_status,
            @RequestParam(value = "approve_result") Integer approve_result,
            @RequestParam(value = "approve_comments") String approve_comments,
            //BindingResult result,
            HttpServletResponse response) {
        wfDefNodeService.testSendMessageCcUser(wf_id, wf_run_id, node_id, wf_status, approve_result, approve_comments);

    }

    /**
     * 启动工作流
     *
     * @param startDTO
     * @param response
     */
    @PostMapping("/start")
    @ApiOperation(value = "启动工作流")
    public ResponseEntity<WfRunWfEntity> start(
            @Validated @RequestBody WfStartDTO startDTO,
            HttpServletRequest request,
            @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response
    ) {
        // 获取服务器的基础URL
        HttpRequestDTO data = new HttpRequestDTO();
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String newAuthHeader = authHeader.replace("Bearer ", "");
        data.setToken(newAuthHeader);
        data.setServerUrl(serverUrl);

        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        if (startDTO.getApplyUserId() == null) {// 如果申请人为空，则默认为当前用户
            startDTO.setApplyUserId(currentUser.getId());
        }
        WfRunWfEntity wfRunWfEntity = wfDefNodeService.startWF(startDTO, data, currentUser);
        return ResponseUtils.ok("工作流启动成功").body(wfRunWfEntity);
    }

    @PostMapping("/restart")
    @ApiOperation(value = "重新发起工作流")
    public ResponseEntity<WfRunWfEntity> restart(
            @Validated @RequestBody WfStartDTO startDTO,
            HttpServletRequest request,
            @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response
    ) throws Exception {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        if (startDTO.getApplyUserId() == null) {// 如果申请人为空，则默认为当前用户
            startDTO.setApplyUserId(currentUser.getId());
        }
        HttpRequestDTO data = new HttpRequestDTO();
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String newAuthHeader = authHeader.replace("Bearer ", "");
        data.setToken(newAuthHeader);
        data.setServerUrl(serverUrl);
        WfRunWfEntity wfRunWfEntity = wfDefNodeService.reStartWF(startDTO, data, currentUser);
        return ResponseUtils.ok("工作流重新发起成功").body(wfRunWfEntity);
    }

    @PostMapping("/deal")
    @ApiOperation(value = "工作流办理")
    public ResponseEntity deal(
            @RequestBody WfDealDTO dealDTO,
            HttpServletRequest request,
            @RequestHeader("Authorization") String authHeader,
            //BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        // 获取服务器的基础URL
        HttpRequestDTO data = new HttpRequestDTO();
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String newAuthHeader = authHeader.replace("Bearer ", "");
        data.setToken(newAuthHeader);
        data.setServerUrl(serverUrl);
        wfDefNodeService.dealWF(dealDTO, data, currentUser);
        return ResponseUtils.ok("办理成功").build();
    }


    @PostMapping("/testVars")
    @ApiOperation(value = "工作流变量测试")
    public ResponseEntity testVars(
            @RequestBody List<IdeCtrlValueDTO> ideVars,
            //BindingResult result,
            HttpServletResponse response
    ) {
//        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        wfDefNodeService.IdeVarDeal(ideVars);
        return ResponseUtils.ok("successful").build();
    }

    @PostMapping("/dealstart")
    @ApiOperation(value = "开始节点办理")
    public ResponseEntity dealstart(
            @RequestBody WfDealStartDTO dealDTO,
            HttpServletRequest request,
            @RequestHeader("Authorization") String authHeader,
            //BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        HttpRequestDTO data = new HttpRequestDTO();
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String newAuthHeader = authHeader.replace("Bearer ", "");
        data.setToken(newAuthHeader);
        data.setServerUrl(serverUrl);
        wfDefNodeService.dealWFStartUser(dealDTO, data, currentUser);
        return ResponseUtils.ok("办理成功").build();
    }

    /**
     * 获取办理信息
     *
     * @param type
     * @param funName
     * @param wfRunNodeId
     * @param applyUserId 申请人
     * @param response
     * @return
     */
    @GetMapping("/getdealinfo")
    @ApiOperation(value = "获取办理信息")
    public WfDealNodeInfoDTO getdealinfo(
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "fun_name", required = false) String funName,
            @RequestParam(value = "wf_run_node_id") Long wfRunNodeId,
            @RequestParam(value = "apply_user_id", required = false) Long applyUserId,
            @RequestParam(value = "platform", required = false, defaultValue = "0") Integer platform,
            //BindingResult result,
            HttpServletResponse response) {

        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        if (applyUserId == null || applyUserId == 0L) {
            applyUserId = currentUser.getId();
        }
        //RestUtils.validation(result);
        WfDealNodeInfoDTO lst = wfDefNodeService.getdealinfo(platform, type, funName, wfRunNodeId, applyUserId, currentUser);
        return lst;
    }

    @GetMapping("/getPreNode")
    @ApiOperation(value = "获取前置节点")
    public List<WfNodeSimpleDTO> getdealinfo(
            @RequestParam(value = "wf_id") Long wf_id,
            @RequestParam(value = "node_id") Long node_id,
            //BindingResult result,
            HttpServletResponse response) {
        //RestUtils.validation(result);
        return wfDefNodeService.getPreNode(wf_id, node_id);
    }

    @GetMapping("/getPreNodeBycode")
    @ApiOperation(value = "获取前置节点")
    public List<WfNodeSimpleDTO> getPreNodeBycode(
            @RequestParam(value = "wf_id") Long wf_id,
            @RequestParam(value = "node_code") String node_code,
            //BindingResult result,
            HttpServletResponse response) {
        //RestUtils.validation(result);
        return wfDefNodeService.getPreNodeBycode(wf_id, node_code);
    }

    /**
     * 作废获取节点信息，作废必须是已经发生过的流上的节点。
     *
     * @param run_wf_id
     * @param wf_run_node_id
     * @param response
     * @return
     */
    @GetMapping("/getInvalidNode")
    @ApiOperation(value = "获取办理信息")
    public List<WfInvalidRunNodeDTO> getInvalidNode(
            @RequestParam(value = "run_wf_id") Long run_wf_id,
            @RequestParam(value = "wf_run_node_id", required = false, defaultValue = "0") Long wf_run_node_id,
            //BindingResult result,
            HttpServletResponse response) {

        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        Long user_id = currentUser.getId();
        //RestUtils.validation(result);
        List<WfInvalidRunNodeDTO> lst = wfDefNodeService.getInvalidNode(run_wf_id, user_id, wf_run_node_id);
        return lst;
    }

    @GetMapping("/testExcel")
    @ApiOperation(value = "excel测试")
    public void testExcel(
            @RequestParam(value = "name") String name,
            //BindingResult result,
            HttpServletResponse response) {
        wfDefNodeService.testExcel(response);
    }

    @GetMapping("/getTables")
    @ApiOperation(value = "获取办理信息")
    public List<WfRunQueryTableDTO> getTables(
            @RequestParam(value = "platform", defaultValue = "1") Integer platform,
            @RequestParam(value = "showType", defaultValue = "1") Integer showType,
            @RequestParam(value = "wfRunNodeId", defaultValue = "0") Long wfRunNodeId,
            @RequestParam(value = "dealResult", defaultValue = "-1") Integer dealResult,
            //BindingResult result,
            HttpServletResponse response) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        List<WfRunQueryTableDTO> wfRunQueryTableDTOs = wfDefNodeService.getTables(0L, 0, wfRunNodeId, platform, showType, dealResult, currentUser);
        return wfRunQueryTableDTOs;
    }

    @PostMapping("/getwftables")
    @ApiOperation(value = "获取工作流节点表")
    public List<WfRunQueryTableDTO> getwftables(
            @RequestBody WfGetTableDTO getTableDTO,
            //BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        List<WfRunQueryTableDTO> wfRunQueryTableDTOs = wfDefNodeService.getWFTables(getTableDTO, currentUser);
        return wfRunQueryTableDTOs;
    }


    @PostMapping("/getwftables_startNode")
    @ApiOperation(value = "获取工作流开始节点表")
    public List<WfRunQueryTableDTO> getwftables_startNode(
            @RequestBody WfGetTableByFunNameDTO getTableDTO,
            //BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        //RestUtils.validation(result);
        List<WfRunQueryTableDTO> wfRunQueryTableDTOs = wfDefNodeService.getWFTablesByFunName(getTableDTO, currentUser);
        return wfRunQueryTableDTOs;
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
    public WfDefNodeDTO getWfDefNode(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        WfDefNodeEntity wfDefNodeEntity = wfDefNodeService.getWfDefNode(id, currentUser);

        return wfDefNodeService.mapperToDTO(wfDefNodeEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点
     *
     * @param wfDefNodeFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点")
    public ResponseEntity createWfDefNode(
            @Validated @RequestBody WfDefNodeFormDTO wfDefNodeFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfDefNodeService.createWfDefNode(wfDefNodeFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_节点
     *
     * @param id
     * @param wfDefNodeFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点")
    public ResponseEntity updateWfDefNode(
            @PathVariable(value = "id") Long id,
            @Validated @RequestBody WfDefNodeFormDTO wfDefNodeFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfDefNodeService.updateWfDefNode(id, wfDefNodeFormDTO, currentUser);
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
    public ResponseEntity deleteWfDefNode(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        wfDefNodeService.deleteWfDefNode(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

