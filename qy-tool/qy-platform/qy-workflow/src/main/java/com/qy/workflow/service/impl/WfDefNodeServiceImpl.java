package com.qy.workflow.service.impl;

import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.message.api.client.TemplateMessageClient;
import com.qy.message.api.command.SendTemplateMessageCommand;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.MemberIdentity;
import com.qy.system.api.client.UniqueCodeClient;
import com.qy.system.api.command.UserUniqueCodeBasicDTO;
import com.qy.utils.RestUtils;
import com.qy.utils.excel.ExcelUtils;
import com.qy.wf.defNodeEvent.entity.DefNodeEventEntity;
import com.qy.wf.defNodeEvent.mapper.DefNodeEventMapper;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.*;
import com.qy.workflow.enums.*;
import com.qy.workflow.mapper.WfDefNodeMapper;
import com.qy.workflow.service.WfDefNodeService;
import com.qy.workflow.service.WfRunNodeService;
import com.qy.workflow.service.WfRunVarService;
import com.qy.workflow.service.WfRunWfService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.http.HttpHeaders.USER_AGENT;


/**
 * 工作流_设计_节点 服务实现类
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Service
public class WfDefNodeServiceImpl extends ServiceImpl<WfDefNodeMapper, WfDefNodeEntity> implements WfDefNodeService {

    @Autowired
    private WfRunVarService wfRunVarService;
    @Autowired
    private WfRunWfService wfRunWfService;
    @Autowired
    private WfRunNodeService wfRunNodeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TemplateMessageClient templateMessageClient;
    @Autowired
    private UniqueCodeClient uniqueCodeClient;

    /**
     * 变量表包含所有变量
     */
    private List<WfVarDTO> wfVarDTOList;
    @Autowired
    private MemberClient memberClient;

    @Autowired
    private DefNodeEventMapper defNodeEventMapper;


    private static final int DealResult_1 = 192;

    @Override
    public IPage<WfDefNodeEntity> getWfDefNodes(IPage iPage, WfDefNodeQueryDTO wfDefNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfDefNodeEntity> wfDefNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        wfDefNodeQueryWrapper.like(wfDefNodeQueryDTO.getName() != null, WfDefNodeEntity::getName, wfDefNodeQueryDTO.getName());
        wfDefNodeQueryWrapper.eq(wfDefNodeQueryDTO.getStatus() != null, WfDefNodeEntity::getStatus, wfDefNodeQueryDTO.getStatus());
        wfDefNodeQueryWrapper.eq(wfDefNodeQueryDTO.getWfId() != null, WfDefNodeEntity::getWfId, wfDefNodeQueryDTO.getWfId());
        wfDefNodeQueryWrapper.orderByDesc(WfDefNodeEntity::getCreateTime);

        return super.page(iPage, wfDefNodeQueryWrapper);
    }


    @Override
    public List<WfDefNodeEntity> getWfDefNodes(WfDefNodeQueryDTO wfDefNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfDefNodeEntity> wfDefNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(wfDefNodeQueryWrapper);
    }


    @Override
    public WfDefNodeEntity getWfDefNode(Long id, MemberIdentity currentUser) {
        WfDefNodeEntity wfDefNodeEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (wfDefNodeEntity == null) {
            throw new ValidationException("未找到 工作流_设计_节点");
        }

        return wfDefNodeEntity;
    }


    @Override
    public WfDefNodeEntity createWfDefNode(WfDefNodeFormDTO wfDefNodeFormDTO, MemberIdentity currentUser) {
        WfDefNodeEntity wfDefNodeEntity = new WfDefNodeEntity();
        BeanUtils.copyProperties(wfDefNodeFormDTO, wfDefNodeEntity);
        wfDefNodeEntity.setCreateTime(LocalDateTime.now());
        wfDefNodeEntity.setCreatorId(currentUser.getId());
        wfDefNodeEntity.setCreatorName(currentUser.getName());
        wfDefNodeEntity.setUpdatorId(Long.valueOf(0));
        wfDefNodeEntity.setIsDeleted((int) 0);
        wfDefNodeEntity.setDeletorId(Long.valueOf(0));

        this.save(wfDefNodeEntity);
        return wfDefNodeEntity;
    }


    @Override
    public WfDefNodeEntity updateWfDefNode(Long id, WfDefNodeFormDTO wfDefNodeFormDTO, MemberIdentity currentUser) {
        WfDefNodeEntity wfDefNodeEntity = getById(id);
        BeanUtils.copyProperties(wfDefNodeFormDTO, wfDefNodeEntity);
        wfDefNodeEntity.setUpdateTime(LocalDateTime.now());
        wfDefNodeEntity.setUpdatorId(currentUser.getId());
        wfDefNodeEntity.setUpdatorName(currentUser.getName());

        this.updateById(wfDefNodeEntity);
        return wfDefNodeEntity;
    }


    @Override
    public WfDefNodeEntity deleteWfDefNode(Long id, MemberIdentity currentUser) {
        WfDefNodeEntity wfDefNodeEntity = getWfDefNode(id, currentUser);
        wfDefNodeEntity.setDeleteTime(LocalDateTime.now());
        wfDefNodeEntity.setDeletorId(currentUser.getId());
        wfDefNodeEntity.setDeletorName(currentUser.getName());

        wfDefNodeEntity.setIsDeleted((int) 1);
        this.updateById(wfDefNodeEntity);
        return wfDefNodeEntity;
    }

    @Override
    public WfDefNodeDTO mapperToDTO(WfDefNodeEntity wfDefNodeEntity, MemberIdentity currentUser) {
        WfDefNodeDTO wfDefNodeDTO = new WfDefNodeDTO();
        BeanUtils.copyProperties(wfDefNodeEntity, wfDefNodeDTO);
        return wfDefNodeDTO;
    }

    @Override
    public List<WfDefNodeDTO> mapperToDTO(List<WfDefNodeEntity> wfDefNodeEntityList, MemberIdentity currentUser) {
        List<WfDefNodeDTO> wfDefNodeDTOs = new ArrayList<>();
        for (WfDefNodeEntity wfDefNodeEntity : wfDefNodeEntityList) {
            WfDefNodeDTO wfDefNodeDTO = mapperToDTO(wfDefNodeEntity, currentUser);
            wfDefNodeDTOs.add(wfDefNodeDTO);
        }


        return wfDefNodeDTOs;
    }

    private void sendMessageApplyBegin(Long wfId,Long wf_run_id,Long node_id,WfStatus wf_status,WfApproveResult approve_result,String approve_comments,
                                       Long applyUserId,Long crateUserId,Long src_id,Long src_row_id)
    {
//        return;
        try{
            //流程启动通知发起人创建人
            List<Long> lstUserIds = new ArrayList<>();
            lstUserIds.add(applyUserId);
            if (!applyUserId.equals(crateUserId)) {
                lstUserIds.add(crateUserId);
            }

            for (Long lstUserId : lstUserIds) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wf_id", wfId.toString());
                maps.put("wf_run_id", wf_run_id.toString());
                maps.put("node_id",node_id.toString());
                maps.put("wf_status",wf_status.getId());
                maps.put("approve_result",approve_result.getId());
                maps.put("approve_comments",approve_comments);
                SendTemplateMessageCommand cmd = new SendTemplateMessageCommand();
                cmd.setTemplateModuleId("workflow");
                cmd.setTemplateFunctionId("notice_apply_begin");
                cmd.setSrcId(src_id.toString());
                cmd.setSrcRowId(src_row_id);
                cmd.setUserId(lstUserId.toString());
                cmd.setParams(maps);

                Map<String, String> extraParams = new HashMap<>();
                extraParams.put("type", "notice_apply_begin");
                extraParams.put("relation_id", src_row_id.toString());
                cmd.setExtraParams(extraParams);

                MemberBasicDTO dto = memberClient.getBasicMember(lstUserId);
                if (dto != null) {
                    UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(lstUserId, dto.getOrganizationId());
                    if (userUniqueCodeDTO != null) {
                        cmd.setMobileId(userUniqueCodeDTO.getRegistrationId());
                        templateMessageClient.sendTemplateMessage(cmd);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("工作流启动，消息推送给发起人出错："+e.getMessage());
        }
    }
    private void sendMessageApplyProcess(Long wfId,Long wf_run_id,Long node_id,WfStatus wf_status,WfApproveResult approve_result,String approve_comments,
                                         Long applyUserId,Long createUserId,Long src_id,Long src_row_id)
    {
//        return;

//        try{
////进行中通知发起人，创建人。
//            List<Long> lstUserIds = new ArrayList<>();
//            lstUserIds.add(applyUserId);
//            if (!applyUserId.equals(createUserId))
//                lstUserIds.add(createUserId);
//
//            Map<String, Object> maps = new HashMap<>();
//            maps.put("wf_id", wfId.toString());
//            maps.put("wf_run_id", wf_run_id.toString());
//            maps.put("node_id",node_id.toString());
//            maps.put("wf_status",wf_status);
//            maps.put("approve_result",approve_result);
//            maps.put("approve_comments",approve_comments);
//            SendTemplateMessageCommand cmd
//                    = new SendTemplateMessageCommand();
//            cmd.setTemplateModuleId("workflow");
//            cmd.setTemplateFunctionId("notice_apply_process");
//            cmd.setSrcId(src_id.toString());
//            cmd.setSrcRowId(src_row_id);
//            cmd.setUserIds(lstUserIds);
//            cmd.setParams(maps);
//            messageSendService.messageSend(cmd);
//        }catch (Exception e) {
//            throw new ValidationException("审批办理，消息推送给发起人出错："+e.getMessage());
//        }

        try{
//进行中通知发起人，创建人。
            List<Long> lstUserIds = new ArrayList<>();
            lstUserIds.add(applyUserId);
            if (!applyUserId.equals(createUserId))
                lstUserIds.add(createUserId);
            for (Long lstUserId : lstUserIds) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wf_id", wfId.toString());
                maps.put("wf_run_id", wf_run_id.toString());
                maps.put("node_id",node_id.toString());
                maps.put("wf_status",wf_status);
                maps.put("approve_result",approve_result);
                maps.put("approve_comments",approve_comments);
                SendTemplateMessageCommand cmd
                        = new SendTemplateMessageCommand();
                cmd.setTemplateModuleId("workflow");
                cmd.setTemplateFunctionId("notice_apply_process");
                cmd.setSrcId(src_id.toString());
                cmd.setSrcRowId(src_row_id);
                cmd.setUserId(applyUserId.toString());
                cmd.setParams(maps);

                Map<String, String> extraParams = new HashMap<>();
                extraParams.put("type", "notice_apply_process");
                extraParams.put("relation_id", src_row_id.toString());
                cmd.setExtraParams(extraParams);

                MemberBasicDTO dto = memberClient.getBasicMember(lstUserId);
                if (dto != null) {
                    UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(lstUserId, dto.getOrganizationId());
                    if (userUniqueCodeDTO != null) {
                        cmd.setMobileId(userUniqueCodeDTO.getRegistrationId());
                        templateMessageClient.sendTemplateMessage(cmd);
                    }
                }
            }
        }catch (Exception e) {
            throw new ValidationException("审批办理，消息推送给发起人出错："+e.getMessage());
        }


    }
    private void sendMessageApplyEnd(Long wfId,Long wf_run_id,Long node_id,WfStatus wf_status,WfApproveResult approve_result,String approve_comments,
                                     Long applyUserId,Long createUserId,Long src_id,Long src_row_id)
    {
//        return;

//        try{
//            //流程结束通知发起人，创建人。
//            List<Long> lstUserIds = new ArrayList<>();
//            lstUserIds.add(applyUserId);
//            if (!applyUserId.equals(createUserId))
//                lstUserIds.add(createUserId);
//
//            Map<String, Object> maps = new HashMap<>();
//            maps.put("wf_id", wfId.toString());
//            maps.put("wf_run_id", wf_run_id.toString());
//            maps.put("node_id",node_id.toString());
//            maps.put("wf_status",wf_status);
//            maps.put("approve_result",approve_result);
//            maps.put("approve_comments",approve_comments);
//            SendTemplateMessageCommand cmd
//                    = new SendTemplateMessageCommand();
//            cmd.setTemplateModuleId("workflow");
//            cmd.setTemplateFunctionId("notice_apply_end");
//            cmd.setSrcId(src_id.toString());
//            cmd.setSrcRowId(src_row_id);
//            cmd.setUserIds(lstUserIds);
//            cmd.setParams(maps);
//            messageSendService.messageSend(cmd);
//        }catch (Exception e) {
//            throw new ValidationException("流程结束消息推送给发起人出错："+e.getMessage());
//        }

        try{
            //流程结束通知发起人，创建人。
            List<Long> lstUserIds = new ArrayList<>();
            lstUserIds.add(applyUserId);
            if (!applyUserId.equals(createUserId))
                lstUserIds.add(createUserId);
            for (Long lstUserId : lstUserIds) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wf_id", wfId.toString());
                maps.put("wf_run_id", wf_run_id.toString());
                maps.put("node_id",node_id.toString());
                maps.put("wf_status",wf_status);
                maps.put("approve_result",approve_result);
                maps.put("approve_comments",approve_comments);
                SendTemplateMessageCommand cmd
                        = new SendTemplateMessageCommand();
                cmd.setTemplateModuleId("workflow");
                cmd.setTemplateFunctionId("notice_apply_end");
                cmd.setSrcId(src_id.toString());
                cmd.setSrcRowId(src_row_id);
                cmd.setUserId(lstUserId.toString());
                cmd.setParams(maps);

                Map<String, String> extraParams = new HashMap<>();
                extraParams.put("type", "notice_apply_end");
                extraParams.put("relation_id", src_row_id.toString());
                cmd.setExtraParams(extraParams);

                MemberBasicDTO dto = memberClient.getBasicMember(lstUserId);
                if (dto != null) {
                    UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(lstUserId, dto.getOrganizationId());
                    if (userUniqueCodeDTO != null) {
                        cmd.setMobileId(userUniqueCodeDTO.getRegistrationId());
                        templateMessageClient.sendTemplateMessage(cmd);
                    }
                }
            }
        }catch (Exception e) {
            throw new ValidationException("流程结束消息推送给发起人出错："+e.getMessage());
        }

    }

    private void sendMessageDealUser(Long wfId, Long wf_run_id, Long node_id, Long dealUserId, WfStatus wf_status, WfApproveResult approve_result,
                                     String approve_comments, Long src_id, Long src_row_id, MemberIdentity currentUser
    )
    {
//        return;//暂时退出发送消息
        try{

            //通知下一节点办理人
            List<Long> lstUserIds = new ArrayList<>();
            lstUserIds.add(dealUserId);
            for (Long lstUserId : lstUserIds) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wf_id", wfId.toString());
                maps.put("wf_run_id", wf_run_id.toString());
                maps.put("node_id",node_id.toString());
                maps.put("wf_status",wf_status);
                maps.put("approve_result",approve_result);
                maps.put("approve_comments",approve_comments);
                SendTemplateMessageCommand cmd  = new SendTemplateMessageCommand();
                cmd.setTemplateModuleId("workflow");
                cmd.setTemplateFunctionId("notice_deal");
                cmd.setSrcId(src_id.toString());
                cmd.setSrcRowId(src_row_id);
                cmd.setUserId(lstUserId.toString());
                cmd.setParams(maps);

                Map<String, String> extraParams = new HashMap<>();
                extraParams.put("type", "notice_deal");
                extraParams.put("relation_id", src_row_id.toString());
                cmd.setExtraParams(extraParams);

                MemberBasicDTO dto = memberClient.getBasicMember(lstUserId);
                if (dto != null) {
                    UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(lstUserId, dto.getOrganizationId());
                    if (userUniqueCodeDTO != null) {
                        cmd.setMobileId(userUniqueCodeDTO.getRegistrationId());
                        templateMessageClient.sendTemplateMessage(cmd);
                    }
                }
            }
        }catch (Exception e) {
            throw new ValidationException("办理人消息推送出错："+e.getMessage());
        }

//        WfRunWfEntity wfRunWfEntity = wfRunWfService.getWfRunWf(wf_run_id, null);
//
//        SendTemplateMessageCommand command = new SendTemplateMessageCommand();
//        command.setTemplateModuleId("workflow");
//        command.setTemplateFunctionId("workflow_deal");
//        Map<String, Object> params = new HashMap<>();
//        params.put("wf_title", wfRunWfEntity.getWfTitle());
//        command.setParams(params);
//        Map<String, String> extraParams = new HashMap<>();
//        extraParams.put("type", "wf_handle");
//        extraParams.put("relation_id", wf_run_id.toString());
//        command.setExtraParams(extraParams);
//        try {
//            UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(dealUserId, currentUser.getOrganizationId());
//            command.setUserId(dealUserId.toString());
//            if (userUniqueCodeDTO != null) {
//                command.setMobileId(userUniqueCodeDTO.getRegistrationId());
//                templateMessageClient.sendTemplateMessage(command);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

    private void sendMessageCcUser(Long wfId,Long wf_run_id,Long node_id,WfStatus wf_status,WfApproveResult approve_result,String approve_comments,Long src_id,Long src_row_id)
    {
//        return;//暂时退出发送消息
        List<Long> lstUserIds = new ArrayList<>();
        try{
            //获取抄送人
            String sql=MessageFormat.format("SELECT wdnu.user_id FROM wf_def_node_user wdnu\n" +
                    "WHERE wdnu.wf_id=''{0}'' AND wdnu.node_id=''{1}'' and type=2 ",wfId.toString(),node_id.toString());
            List<Map<String, Object>> map1 = jdbcTemplate.queryForList(sql);
            if (map1 != null && map1.size() > 0) {
                for (int i = 0; i < map1.size(); i++) {
                    String user_id = map1.get(i).get("user_id").toString();
                    lstUserIds.add(Long.parseLong(user_id));
                }
            }
            if(lstUserIds.size()==0) return;

            for (Long lstUserId : lstUserIds) {
                Map<String, Object> maps = new HashMap<>();
                maps.put("wf_id", wfId.toString());
                maps.put("wf_run_id", wf_run_id.toString());
                maps.put("node_id",node_id.toString());
                maps.put("wf_status",wf_status);
                maps.put("approve_result",approve_result);
                maps.put("approve_comments",approve_comments);
                SendTemplateMessageCommand cmd
                        = new SendTemplateMessageCommand();
                cmd.setTemplateModuleId("workflow");
                cmd.setTemplateFunctionId("notice_ccuser");
                cmd.setSrcId(src_id.toString());
                cmd.setSrcRowId(src_row_id);
                cmd.setUserId(lstUserId.toString());
                cmd.setParams(maps);

                Map<String, String> extraParams = new HashMap<>();
                extraParams.put("type", "notice_ccuser");
                extraParams.put("relation_id", src_row_id.toString());
                cmd.setExtraParams(extraParams);

                MemberBasicDTO dto = memberClient.getBasicMember(lstUserId);
                if (dto != null) {
                    UserUniqueCodeBasicDTO userUniqueCodeDTO = uniqueCodeClient.getUserUniqueCode(lstUserId, dto.getOrganizationId());
                    if (userUniqueCodeDTO != null) {
                        cmd.setMobileId(userUniqueCodeDTO.getRegistrationId());
                        templateMessageClient.sendTemplateMessage(cmd);
                    }
                }
            }
        }catch (Exception e) {
            throw new ValidationException("抄送人消息推送出错："+e.getMessage());
        }
    }
    @Override
    public void dealWFStartUser(WfDealStartDTO dealNode,HttpRequestDTO httpRequestDTO, MemberIdentity currentUser) {
        if (dealNode.getWfRunId() == null) {
            throw new ValidationException("未找到办理节点数据");
        }
        LambdaQueryWrapper<WfRunNodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfRunNodeEntity::getRunWfId, dealNode.getWfRunId())
                .eq(WfRunNodeEntity::getIsCurMark, 1)
                .eq(WfRunNodeEntity::getWfId, dealNode.getWfId());
        //获取当前节点的上一个节点。
        String sql = MessageFormat.format("SELECT a.id,a.cur_node_user_id,a.creator_id, case when a.cur_node_id=b.id then 1 ELSE 0 END isstart " +
                "FROM wf_run_node a LEFT JOIN wf_def_node b ON a.wf_id=b.wf_id AND b.id=a.cur_node_id\n" +
                "WHERE (a.run_wf_id,a.sort)=(\n" +
                "SELECT run_wf_id,sort-1 FROM wf_run_node WHERE run_wf_id={0}\n" +
                "AND is_cur_mark=1)", dealNode.getWfRunId().toString());
        List<Map<String, Object>> map1 = jdbcTemplate.queryForList(sql);
        if (map1 == null || map1.size() == 0) {
            throw new ValidationException("未找到办理节点数据");
        }
        Long runNodeId = 0L;
        Integer isstart = 0;
        Long nodeUserId = 0L;
        Long creator_id = 0L;

        if (map1 != null && map1.size() > 0) {
            runNodeId = Long.parseLong(map1.get(0).get("id").toString());
            isstart = Integer.parseInt(map1.get(0).get("isstart").toString());
            nodeUserId = Long.parseLong(map1.get(0).get("cur_node_user_id").toString());
            creator_id = Long.parseLong(map1.get(0).get("creator_id").toString());
        }

        if (!currentUser.getId().equals(nodeUserId) && !currentUser.getId().equals(creator_id)) {
            throw new ValidationException("您不是当前节点办理人");
        }
        if (isstart == 0) {
            throw new ValidationException("您不是当前节点办理人");
        }

        WfDealDTO wfDealDTO = new WfDealDTO();
        wfDealDTO.setId(runNodeId);
        wfDealDTO.setNextNodeId(dealNode.getNextNodeId());
        wfDealDTO.setNextUserId(dealNode.getNextUserId());
        wfDealDTO.setResult(dealNode.getResult());
        wfDealDTO.setComment(dealNode.getComment());
        wfDealDTO.setReturnNodeId(dealNode.getReturnNodeId());
        wfDealDTO.setReturnUserId(dealNode.getReturnUserId());
        wfDealDTO.setOutVars(dealNode.getOutVars());
        wfDealDTO.setPlatform(dealNode.getPlatform());
        //通过发起人找到最新的节点
        dealWF(wfDealDTO,httpRequestDTO , currentUser);

    }

    /**
     * 办理工作流
     *
     * @param dealNode
     * @param currentUser
     */
    @Override
    public void dealWF(WfDealDTO dealNode, HttpRequestDTO requestData, MemberIdentity currentUser) {
        try {
            //region 变量取值 手工办理节点和退回节点取
            //当前执行ID
            Long runId = dealNode.getId();
            Long pNextNodeId = dealNode.getNextNodeId();
            Long pNextDealUserId = dealNode.getNextUserId();
            Long pReturnNodeId = dealNode.getReturnNodeId();
            Long pReturnUserId = dealNode.getReturnUserId();

            if (pNextNodeId==null) pNextNodeId=0L;
            if (pNextDealUserId==null) pNextDealUserId=0L;
            if (pReturnNodeId==null) pReturnNodeId=0L;
            if (pReturnUserId==null) pReturnUserId=0L;

            //获取工作流办理主表信息


            //判断当前节点运行ID是否存在
            //endregion
            //region 获取当前执行节点 并查找当前节点的工作流设置信息
            LambdaQueryWrapper<WfRunNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
            wrapper.eq(WfRunNodeEntity::getId, runId);
            WfRunNodeEntity curRunNode = wfRunNodeService.getOne(wrapper, false);

            String errorMsg = "";
            if (curRunNode == null) {
                errorMsg = "当前办理节点不存在";
                throw new ValidationException(errorMsg);
            }
            //撤销和撤回不限制当前节点  代码表 wf_approve_result 1-同意，2拒绝，3-退回，4-作废，5撤回， 6办理 ,7撤销
            if (curRunNode.getIsCurMark() != WfDealResult.Agree.getId() &&
                    dealNode.getResult() != WfDealResult.Revoke.getId() &&
                    dealNode.getResult() != WfDealResult.Withdraw.getId() &&
                    dealNode.getResult() != WfDealResult.Invalid.getId()) {
                errorMsg = "当前节点不是办理节点";
                throw new ValidationException(errorMsg);
            }

            LambdaQueryWrapper<WfRunWfEntity> wfEntityLambdaQueryWrapper = RestUtils.getLambdaQueryWrapper();
            wfEntityLambdaQueryWrapper.eq(WfRunWfEntity::getId, curRunNode.getRunWfId());
            WfRunWfEntity curRunWf = wfRunWfService.getOne(wfEntityLambdaQueryWrapper, false);
            if (curRunWf.getWfStatus().equals(WfStatus.Finish.getId()))
            {
                errorMsg = "流程已结束，请勿重复操作";
                throw new ValidationException(errorMsg);
            }

            //当前执行节点的ID
            Long curNodeId = curRunNode.getCurNodeId();
            /**
             * 下一个节点的主键,工作流运行写入的步骤表
             */
            Long nextWfRunNodeId = 0L;

            //当前执行节点ID 主键
            Long wfId = curRunNode.getWfId();
            Long runWfId = curRunNode.getRunWfId();
            //查找工作流节点设置
            LambdaQueryWrapper<WfDefNodeEntity> wrapperDefNode = RestUtils.getLambdaQueryWrapper();
            wrapperDefNode.eq(WfDefNodeEntity::getId, curNodeId)
                    .eq(WfDefNodeEntity::getWfId, wfId);
            WfDefNodeEntity defNode = this.getOne(wrapperDefNode, false);

            if (defNode == null) {
                errorMsg = "未找到工作流节点配置";
                throw new ValidationException(errorMsg);
            }
            //endregion

            //获取工作流的办理信息
            WfRunWfEntity runWfEntity= getStartNode(runWfId);
            Long startUserId=runWfEntity.getCreatorId();
            Long beginUserId=runWfEntity.getBeginUserId();
            Long beginNodeId=runWfEntity.getBeginNodeId();


            //region 查找下一节点部门与人员
            //初始化变量表并赋值
            List<WfVarDTO> tmpWfVarList = initVars(dealNode.getPlatform(),dealNode.getResult(), curRunNode.getWfId(), curRunNode.getCurNodeId(), curRunNode.getRunWfId(), runId, 0L,0L, currentUser.getId(), false, currentUser);
            wfVarDTOList =AppendOutVars(dealNode.getOutVars(), tmpWfVarList);
            wfVarDTOList=AppendIdeVars(dealNode.getIdeVars(),wfVarDTOList);

            //如果是退回获取上节点的人
            List<WfNextNodeDTO> nextNodeDTOList=new ArrayList<>();
            if (dealNode.getResult() == WfDealResult.Repulse.getId())
            {
                nextNodeDTOList = getWfRepulesNodeDTOS(curRunNode.getId(),curRunNode.getWfId(),curRunNode.getCurNodeId());
            } else
            {
                nextNodeDTOList = findRelationNextNodesAndUsers(wfId, curNodeId, currentUser.getId());
            }


            //直接获取下一节点和节点办理人
            if (nextNodeDTOList == null || nextNodeDTOList.size() == 0) {
                errorMsg = "未找到下一办理节点";
                throw new ValidationException(errorMsg);
            }

            //找人
            Boolean isFindUser = false;
            Integer nextNodeType = 0;
            //region 手工节点操作 指定节点及办理人 查找办理权限

            if (pNextNodeId > 0 || pNextDealUserId > 0) {
                for (int i = 0; i < nextNodeDTOList.size(); i++) {
                    WfNextNodeDTO relation = nextNodeDTOList.get(i);
                    Long nextNodeId = relation.getNodeId();
                    if (!nextNodeId.equals(pNextNodeId)) continue;
                    pNextNodeId = nextNodeId;
                    nextNodeType = relation.getNodeType();
                    if (relation.getUsers() == null || relation.getUsers().size() == 0) continue;
                    for (int user = 0; user < relation.getUsers().size(); user++) {
                        WfNodeUserDTO userDTO = relation.getUsers().get(user);
                        if (userDTO.getUserId().equals(pNextDealUserId)) {
                            pNextDealUserId = userDTO.getUserId();
                            isFindUser = true;
                        }
                    }
                    if (isFindUser) break;//找到一个节点办理就退出
                }
                if (!isFindUser) {
                    errorMsg = "您选择的节点或者节点办理人匹配失败请重试";
                    throw new ValidationException(errorMsg);
                }
            }
            //endregion
            //region 自动节点
            else {

                for (int i = 0; i < nextNodeDTOList.size(); i++) {
                    WfNextNodeDTO relation = nextNodeDTOList.get(i);
                    Long nextNodeId = relation.getNodeId();
                    pNextNodeId = nextNodeId;
                    nextNodeType = relation.getNodeType();
                    for (int user = 0; user < relation.getUsers().size(); user++) {
                        WfNodeUserDTO userDTO = relation.getUsers().get(user);
                        pNextDealUserId = userDTO.getUserId();
                        isFindUser = true;
                        break;
                    }
                    if (isFindUser) break;//找到一个节点办理就退出
                }
                if (!isFindUser) {
                    throw new ValidationException("未找到下一节点办理人");
                }
            }
            //endregion
            //endregion

            LocalDateTime curDateTime = LocalDateTime.now();
            Long cur_node_user_id = currentUser.getId();
            //查找办理权限 办理类型 1-同意，2拒绝，3-退回，4-作废，5撤回 6办理
            Integer dealResult = dealNode.getResult();

            //region  同意
            if (dealResult == WfDealResult.Agree.getId()) {
                if (defNode.getIsCanAgree() != 1) {
                    errorMsg = "没有同意权限";
                    throw new ValidationException(errorMsg);
                }
                //更新上一个节点的nextnodestatus状态为结束.
                WfRunNodeEntity runNodeEntity = updatePreNodeStatus(runId, WfNodeStatus.Finish, currentUser);
                Integer sort = curRunNode.getSort() + 1;
                WfRunNodeEntity wfNewRunNode = writeRunNodeInfo(dealNode, currentUser, runId, curRunNode, curDateTime, dealResult, nextNodeType, pNextNodeId, pNextDealUserId, sort);
                nextWfRunNodeId = wfNewRunNode.getId();

                if (nextNodeType == WfNodeType.Finish.getId()) //结束节点
                {
                    /*只推送给发起人，申请人，当前节点的抄送人*/
                    sendMessageApplyEnd(wfId, runWfId, beginNodeId, WfStatus.Finish, WfApproveResult.Agree, dealNode.getComment(), beginUserId, startUserId, runWfId, runId);
                    sendMessageCcUser(wfId, runWfId, curNodeId, WfStatus.Finish, WfApproveResult.Agree, dealNode.getComment(), runWfId, runId);
                } else {
                    sendMessageApplyProcess(wfId, runWfId, beginNodeId, WfStatus.Finish, WfApproveResult.Agree, dealNode.getComment(), beginUserId, startUserId, runWfId, runId);
                    sendMessageDealUser(wfId, runWfId, curNodeId, pNextDealUserId, WfStatus.Finish, WfApproveResult.Agree, dealNode.getComment(), runWfId, runId, currentUser);
                    sendMessageCcUser(wfId, runWfId, curNodeId, WfStatus.Finish, WfApproveResult.Agree, dealNode.getComment(), runWfId, runId);
                }
            }
            //endregion
            //region  拒绝
            else if (dealResult == WfDealResult.Refuse.getId()) {
                if (defNode.getIsCanRefuse() != 1) {
                    errorMsg = "没有拒绝权限";
                    throw new ValidationException(errorMsg);
                }
                //拒绝结束流程
                //更新上一个节点的nextnodestatus状态为结束.
                WfRunNodeEntity runNodeEntity = updatePreNodeStatus(runId,WfNodeStatus.Finish,currentUser);
                //更新当前节点的状态为拒绝，工作流执行表状态为结束并拒绝。
                WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                if (curNode.getCurReadTime() == null)
                    curNode.setCurReadTime(curDateTime);
                curNode.setCurStatus(WfNodeStatus.Finish.getId());
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurDealTime(curDateTime);
                curNode.setIsCurMark(0);
                curNode.setApproveResult(dealNode.getResult());
                curNode.setApproveComments(dealNode.getComment());
                wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);

                //更新主表状态时间
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setCurStatus(WfNodeStatus.Finish.getId());
                wfRunWfFormDTO.setCurDealTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfStatus.Finish.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setEndTime(curDateTime);
                // wfRunWfFormDTO.setPreRunNodeId(runId); 拒绝上一个节点和当前节点都是自己，不更新上一个节点。
                // wfRunWfFormDTO.setCurRunNodeId(runId);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyEnd(wfId, runWfId,curNodeId,WfStatus.Finish,WfApproveResult.Refuse,dealNode.getComment(), beginUserId,startUserId,runWfId,runId);
                sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Finish,WfApproveResult.Refuse,dealNode.getComment(),runWfId,runId);

            }
            //endregion
            //region  办理
            else if (dealResult == WfDealResult.Deal.getId()) {
                if (defNode.getIsCanRefuse() != 1) {
                    errorMsg = "没有办理权限";
                    throw new ValidationException(errorMsg);
                }
                //最后一个节点不能办理，要验证是不是最后一个节点。
                if (nextNodeDTOList.size() == 1) {
                    if (nextNodeDTOList.get(0).getNodeType() == WfNodeType.Finish.getId()) {
                        throw new ValidationException("最后一个节点不能设置办理权限");
                    }
                }
                Integer sort = curRunNode.getSort() + 1;

                //更新上一个节点的nextnodestatus状态为结束.
                updatePreNodeStatus(runId,WfNodeStatus.Finish,currentUser);
                //更新当前节点的办理数据
                WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                curNode.setIsCurMark(0);
                curNode.setCurStatus(WfNodeStatus.Finish.getId());
                curNode.setApproveResult(dealNode.getResult());
                curNode.setApproveComments(dealNode.getComment());
                curNode.setNextNodeId(pNextNodeId);
                curNode.setNextNodeUserId(pNextDealUserId);
                curNode.setNextNodeStatus(WfNodeStatus.Dealing.getId());
                if (curNode.getCurReadTime() == null)
                    curNode.setCurReadTime(curDateTime);
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurDealTime(curDateTime);
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurSendTime(curDateTime);
                WfRunNodeEntity curRunNodeEntity = wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);

                //写下一节点的办理数据
                WfRunNodeEntity wfRunNode = writeNextRunNode(currentUser, curRunNode, curDateTime, pNextNodeId, pNextDealUserId, sort, WfNodeStatus.Dealing);
                nextWfRunNodeId = wfRunNode.getId();

                // 更新当前节点的nextRunNodeId
                curRunNodeEntity.setNextRunNodeId(nextWfRunNodeId);
                wfRunNodeService.updateById(curRunNodeEntity);

                //更新主表状态时间
                curDateTime = curDateTime.plusSeconds(1);
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                wfRunWfFormDTO.setCurStatus(WfNodeStatus.Dealing.getId());
                wfRunWfFormDTO.setCurNodeId(pNextNodeId);
                wfRunWfFormDTO.setCurNodeUserId(pNextDealUserId);
                wfRunWfFormDTO.setCurArriveTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfNodeStatus.Dealing.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                wfRunWfFormDTO.setPreRunNodeId(runId);
                wfRunWfFormDTO.setCurRunNodeId(nextWfRunNodeId);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyProcess(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Deal,dealNode.getComment(),beginUserId,startUserId,runWfId,runId);
                sendMessageDealUser(wfId, runWfId,curNodeId,pNextDealUserId,WfStatus.Deal,WfApproveResult.Deal,dealNode.getComment(),runWfId,runId, currentUser);
                sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Deal,dealNode.getComment(),runWfId,runId);
            }
            //endregion
            //region  退回
            else if (dealResult == WfDealResult.Return.getId()) {
                if (defNode.getIsCanReturn() != 1) {
                    errorMsg = "没有退回权限";
                    throw new ValidationException(errorMsg);
                }

                List<WfNextNodeDTO> returnNodelist = FindReturnNode(wfId, runId);
                if (returnNodelist == null || returnNodelist.size() == 0) {
                    errorMsg = "未找到退回节点";
                    throw new ValidationException(errorMsg);
                }
                Boolean isFind = false;
                // 验证退回节点
                for (int i = 0; i < returnNodelist.size(); i++) {
                    WfNextNodeDTO relation = returnNodelist.get(i);
                    Long nextNodeId = relation.getNodeId();
                    if (!nextNodeId.equals(pReturnNodeId)) continue;

                    for (int user = 0; user < relation.getUsers().size(); user++) {
                        WfNodeUserDTO userDTO = relation.getUsers().get(user);
                        if (userDTO.getUserId().equals(pNextDealUserId)) {
                            pNextDealUserId = userDTO.getUserId();
                            isFindUser = true;
                            break;
                        }
                    }
                    if (isFindUser) break;//找到一个节点办理就退出
                }

                if (!isFindUser) {
                    throw new ValidationException("未找到退回节点办理人");
                }

                LocalDateTime curDealTime = curDateTime;
                //是否为开始节点,如果是开始节点更新当前节点状态为发起状态
                boolean isStartNode = isStartNode(pReturnNodeId, curRunNode.getRunWfId());
                WfNodeStatus nodeStatus = isStartNode ? WfNodeStatus.Start : WfNodeStatus.Dealing;
                //更新上一个节点状态为退回
                updatePreNodeStatus(runId,WfNodeStatus.Finish,currentUser);
                //更新当前节点状态为退回
                WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                if (curNode.getCurReadTime() == null)
                    curNode.setCurReadTime(curDateTime);
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurDealTime(curDealTime);
                curNode.setIsCurMark(0);
                curNode.setCurStatus(WfNodeStatus.Finish.getId());
                curNode.setApproveResult(dealNode.getResult());
                curNode.setApproveComments(dealNode.getComment());
                curNode.setNextNodeId(pNextNodeId);
                curNode.setNextNodeUserId(pNextDealUserId);
                curNode.setNextNodeStatus(nodeStatus.getId());
                WfRunNodeEntity node = wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);
                int sort = curRunNode.getSort() + 1;

                //写入新的节点为上一个节点
                WfRunNodeEntity wfRunNode = writeNextRunNode(currentUser, curRunNode, curDateTime, pReturnNodeId, pReturnUserId, sort, nodeStatus);
                nextWfRunNodeId = wfRunNode.getId();

                // 更新当前节点的nextRunNodeId
                node.setNextRunNodeId(nextWfRunNodeId);
                wfRunNodeService.updateById(node);

                //更新主表的状态
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setCurStatus(nodeStatus.getId());
                wfRunWfFormDTO.setCurNodeId(pReturnNodeId);
                wfRunWfFormDTO.setCurNodeUserId(pReturnUserId);
                wfRunWfFormDTO.setCurDealTime(curDealTime);
                wfRunWfFormDTO.setCurArriveTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfStatus.Deal.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                wfRunWfFormDTO.setPreRunNodeId(runId);
                wfRunWfFormDTO.setCurRunNodeId(nextWfRunNodeId);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyProcess(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Return,dealNode.getComment(),beginUserId,startUserId,runWfId,runId);
                sendMessageDealUser(wfId, runWfId,curNodeId,pReturnUserId,WfStatus.Deal,WfApproveResult.Return,dealNode.getComment(),runWfId,runId, currentUser);
                sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Return,dealNode.getComment(),runWfId,runId);

            }
            //endregion
            //region  作废
            else if (dealResult == WfDealResult.Invalid.getId()) {
                if (defNode.getIsCanInvalid() != 1) {
                    errorMsg = "没有作废权限";
                    throw new ValidationException(errorMsg);
                }
                if (curRunNode.getStatus()==WfNodeStatus.Finish.getId()){
                    throw new ValidationException("当前节点已经办理,不能重复操作");
                }
//            if (curRunNode.getIsCurMark()==0){
//                throw new ValidationException("当前节点不是办理节点");
//            }
                //更新上一个节点的nextnodestatus状态为结束.
                updatePreNodeStatus(runId,WfNodeStatus.Finish,currentUser);
                //更新当前节点的状态为作废，工作流执行表状态为作废。
                WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                if (curNode.getCurReadTime() == null)
                    curNode.setCurReadTime(curDateTime);
                curNode.setCurStatus(WfNodeStatus.Finish.getId());
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurDealTime(curDateTime);
                curNode.setIsCurMark(0);
                curNode.setApproveResult(dealNode.getResult());
                curNode.setApproveComments(dealNode.getComment());
                wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);

                //更新主表状态时间
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setCurStatus(WfNodeStatus.Finish.getId());
                wfRunWfFormDTO.setCurDealTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfStatus.Finish.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
//            wfRunWfFormDTO.setPreRunNodeId(); 作废不用更新上一个节点
//            wfRunWfFormDTO.setCurRunNodeId();作废不用更新当前节点
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setEndTime(curDateTime);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyEnd(wfId, runWfId, curNodeId, WfStatus.Finish, WfApproveResult.Invalid, dealNode.getComment(), beginUserId, startUserId, runWfId, runId);
                sendMessageCcUser(wfId, runWfId, curNodeId, WfStatus.Finish, WfApproveResult.Invalid, dealNode.getComment(), runWfId,runId);
            }
            //endregion
            //region撤回
            else if (dealResult == WfDealResult.Withdraw.getId()) {
                if (defNode.getIsCanWithdraw() != 1) {
                    errorMsg = "没有撤回权限";
                    throw new ValidationException(errorMsg);
                }

                if (curRunNode.getIsCurMark()==1){
                    throw new ValidationException("当前节点不能撤回，只有上一节点才能撤回");
                }
                //只要下一节点没有办理就可以撤回。 查找下一节点的状态。
                String nextDealStausSql = MessageFormat.format("SELECT id,sort,approve_result,cur_status,cur_deal_time,is_cur_mark,cur_node_id,cur_node_user_id FROM wf_run_node WHERE (run_wf_id,sort)=\n" +
                        "(SELECT wrn.run_wf_id,wrn.sort+1 FROM wf_run_node wrn WHERE id={0})", runId.toString());
                List<WfNextRunNodeDealDTO> nextRunNodeDealDTOS = jdbcTemplate.query(nextDealStausSql,
                        BeanPropertyRowMapper.newInstance(WfNextRunNodeDealDTO.class));
                if (nextRunNodeDealDTOS == null || nextRunNodeDealDTOS.size() < 1) {
                    throw new ValidationException("下一节点办理人不存在,不能撤回");
                }
                else if (nextRunNodeDealDTOS.size() > 0) {
                    WfNextRunNodeDealDTO dealDTO = nextRunNodeDealDTOS.get(0);
                    if (dealDTO.getCurStatus() == WfNodeStatus.Finish.getId() || dealDTO.getCurDealTime() != null || dealDTO.getApproveResult() > 0) {
                        throw new ValidationException("下一节点已经办理,不能撤回");
                    }
                    if (dealDTO.getIsCurMark() == 0) {
                        throw new ValidationException("下一节点不是办理节点,不能撤回");
                    }

                    if (!getMemberIsExist(pNextDealUserId))
                    {
                        errorMsg = "下一节点办理人["+currentUser.getId()+"]不存在!";
                        throw new ValidationException(errorMsg);
                    }

                    //更新当前节点的办理数据
                    WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                    curNode.setNextNodeStatus(WfNodeStatus.Finish.getId());
                    wfRunNodeService.updateWfRunNode(runId, curNode, currentUser);

                    //更新下一节点删除标记
                    WfRunNodeFormDTO wfRunNodeFormDTO = new WfRunNodeFormDTO();
                    wfRunNodeFormDTO.setIsCurMark(0);
                    wfRunNodeFormDTO.setCurStatus(WfNodeStatus.Finish.getId());
                    wfRunNodeFormDTO.setApproveResult(0);
                    wfRunNodeFormDTO.setNextNodeId(pNextNodeId);
                    wfRunNodeFormDTO.setNextNodeUserId(pNextDealUserId);
                    wfRunNodeFormDTO.setNextNodeStatus(WfNodeStatus.Finish.getId());
                    wfRunNodeFormDTO.setDeletor_id(cur_node_user_id);
                    wfRunNodeFormDTO.setDeleteTime(curDateTime);
                    wfRunNodeFormDTO.setIsDeleted(1);
                    wfRunNodeService.updateWfRunNode(dealDTO.getId(), wfRunNodeFormDTO, currentUser);

                    //是否为开始节点,如果是开始节点更新当前节点状态为发起状态
                    boolean isStartNode = isStartNode(curRunNode.getCurNodeId(), curRunNode.getRunWfId());
                    WfNodeStatus nodeStatus = isStartNode ? WfNodeStatus.Start : WfNodeStatus.Dealing;

                    //复制当前节点的数据
                    int nextSort = curRunNode.getSort() + 2;
                    //写入一个撤回的节点信息
                    curDateTime = curDateTime.plusSeconds(1);
                    WfRunNodeEntity wfRunNodeEntity = writedWithdrawRunNode(dealNode, currentUser, curRunNode, curDateTime, nextSort, pNextNodeId, pNextDealUserId, WfNodeStatus.Dealing);

                    //再写入新的办理节点
                    curDateTime = curDateTime.plusSeconds(1);
                    WfRunNodeEntity wfRunNode = writeNextRunNode(currentUser, curRunNode, curDateTime, curRunNode.getCurNodeId(), curRunNode.getCurNodeUserId(), nextSort + 1, nodeStatus);
                    nextWfRunNodeId = wfRunNode.getId();

                    // 更新上一节点的nextRunNodeId
                    wfRunNodeEntity.setNextRunNodeId(nextWfRunNodeId);
                    wfRunNodeService.updateById(wfRunNodeEntity);

                    //更新父表的数据
                    //如果是开始节点，如果是办理节点
                    WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                    wfRunWfFormDTO.setCurStatus(nodeStatus.getId());
                    wfRunWfFormDTO.setCurNodeId(curRunNode.getCurNodeId());
                    wfRunWfFormDTO.setCurNodeUserId(curRunNode.getCurNodeUserId());
                    wfRunWfFormDTO.setCurSendTime(null);
                    wfRunWfFormDTO.setCurReadTime(null);
                    wfRunWfFormDTO.setCurDealTime(null);
                    wfRunWfFormDTO.setCurArriveTime(curDateTime);
                    wfRunWfFormDTO.setApproveResult(dealNode.getResult());
                    wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                    wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                    wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                    wfRunWfFormDTO.setWfStatus(WfStatus.Deal.getId());
                    wfRunWfFormDTO.setApproveResult(dealResult);
                    wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                    wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                    wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                    wfRunWfFormDTO.setPreRunNodeId(runId);
                    wfRunWfFormDTO.setCurRunNodeId(nextWfRunNodeId);
                    WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                    sendMessageApplyProcess(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Withdraw,dealNode.getComment(),beginUserId,startUserId,runWfId,runId);
                    sendMessageDealUser(wfId, runWfId,pNextNodeId,pNextDealUserId,WfStatus.Deal,WfApproveResult.Withdraw,dealNode.getComment(),runWfId,runId, currentUser);
                    sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Withdraw,dealNode.getComment(),runWfId,runId);

                }
            }
            //endregion 打回
            else if (dealResult == WfDealResult.Repulse.getId()) {
                //打回是当前执行节点办理人员的之前的工作流配置节点
                if (defNode.getIsRepulse() != 1) {
                    errorMsg = "没有打回权限";
                    throw new ValidationException(errorMsg);
                }
                if (curRunNode.getIsCurMark()!=1){
                    throw new ValidationException("不是当前节点不能打回");
                }

                if (!getMemberIsExist(pNextDealUserId))
                {
                    errorMsg = "下一节点办理人["+pNextDealUserId+"]不存在!";
                    throw new ValidationException(errorMsg);
                }

                LocalDateTime curDealTime = curDateTime;
                //是否为开始节点,如果是开始节点更新当前节点状态为发起状态
                boolean isStartNode = isStartNode(pNextNodeId, curRunNode.getRunWfId());
                WfNodeStatus nodeStatus = isStartNode ? WfNodeStatus.Start : WfNodeStatus.Dealing;
                //更新上一个节点状态为完成
                updatePreNodeStatus(runId,WfNodeStatus.Finish,currentUser);
                //更新当前节点状态为打回
                WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
                if (curNode.getCurReadTime() == null)
                    curNode.setCurReadTime(curDateTime);
                curDateTime = curDateTime.plusSeconds(1);
                curNode.setCurDealTime(curDealTime);
                curNode.setIsCurMark(0);
                curNode.setCurStatus(WfNodeStatus.Finish.getId());
                curNode.setApproveResult(dealNode.getResult());
                curNode.setApproveComments(dealNode.getComment());
                curNode.setNextNodeId(pNextNodeId);
                curNode.setNextNodeUserId(pNextDealUserId);
                curNode.setNextNodeStatus(nodeStatus.getId());
                WfRunNodeEntity node = wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);
                int sort = curRunNode.getSort() + 1;

                //写入新的节点为下一个节点
                WfRunNodeEntity wfRunNode = writeNextRunNode(currentUser, curRunNode, curDateTime, pNextNodeId, pNextDealUserId, sort, nodeStatus);
                nextWfRunNodeId = wfRunNode.getId();

                // 更新上一节点的nextRunNodeId
                node.setNextRunNodeId(nextWfRunNodeId);
                wfRunNodeService.updateById(node);

                //更新主表的状态
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setCurStatus(nodeStatus.getId());
                wfRunWfFormDTO.setCurNodeId(pNextNodeId);
                wfRunWfFormDTO.setCurNodeUserId(pNextDealUserId);
                wfRunWfFormDTO.setCurDealTime(curDealTime);
                wfRunWfFormDTO.setCurArriveTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfStatus.Deal.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                wfRunWfFormDTO.setPreRunNodeId(runId);
                wfRunWfFormDTO.setCurRunNodeId(nextWfRunNodeId);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyProcess(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Repulse,dealNode.getComment(),beginUserId,startUserId,runWfId,runId);
                sendMessageDealUser(wfId, runWfId, pNextNodeId, pNextDealUserId, WfStatus.Deal,WfApproveResult.Repulse,dealNode.getComment(),runWfId,runId, currentUser);
                sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Deal,WfApproveResult.Repulse,dealNode.getComment(),runWfId,runId);
            }
            // region 发起人 撤销
            else if (dealResult == WfDealResult.Revoke.getId()) {
                Long StartNodeId = 0L;
                Long StartUserId = 0L;

                //只有发起人才能撤销
                String startNodeSql = MessageFormat.format("SELECT wdn.cur_node_id,wdn.cur_node_user_id FROM wf_run_node wdn WHERE wdn.run_wf_id=(\n" +
                        "SELECT wrn.run_wf_id FROM wf_run_node wrn WHERE id={0}) and wdn.sort=1", runId.toString());
                List<Map<String, Object>> map1 = jdbcTemplate.queryForList(startNodeSql);
                if (map1 == null || map1.size() == 0) {
                    throw new ValidationException("未找到开始节点");
                }
                if (map1 != null && map1.size() > 0) {
                    StartNodeId = Long.parseLong(map1.get(0).get("cur_node_id").toString());
                    StartUserId = Long.parseLong(map1.get(0).get("cur_node_user_id").toString());
                }
//            if (!currentUser.getId().equals(StartUserId))
//            {
//                throw new ValidationException("只有发起人才能撤销");
//            }
                String sqlRunNode = MessageFormat.format("SELECT * FROM wf_run_node wrn WHERE wrn.run_wf_id={0} ORDER BY wrn.sort DESC LIMIT 1",runWfId.toString());
                List<WfRunNodeEntity> lastRunNodeList = jdbcTemplate.query(sqlRunNode, BeanPropertyRowMapper.newInstance(WfRunNodeEntity.class));

                if (lastRunNodeList==null||lastRunNodeList.size()==0) {
                    throw new ValidationException("未找到最后的办理节点");
                }

                if (!getMemberIsExist(StartUserId))
                {
                    errorMsg = "发起人["+StartUserId+"]不存在!";
                    throw new ValidationException(errorMsg);
                }

                WfRunNodeEntity lastRunNode = lastRunNodeList.get(0);

                //最后一个节点的状态变为结束，办理意见为0，下一节点为结束。
                WfRunNodeFormDTO lastNode = new WfRunNodeFormDTO();
                lastNode.setCurStatus(WfNodeStatus.Finish.getId());
                lastNode.setApproveResult(0);
                curDateTime = curDateTime.plusSeconds(1);
                lastNode.setNextNodeId(StartNodeId);
                lastNode.setNextNodeUserId(StartUserId);
                lastNode.setNextNodeStatus(WfNodeStatus.Finish.getId());
                lastNode.setIsCurMark(0);
                WfRunNodeEntity lastNodeEntity = wfRunNodeService.updateWfRunNode(lastRunNode.getId(), lastNode, currentUser);

                //写入一个新的节点。
                int nextSort = lastRunNode.getSort() + 1;
                WfRunNodeFormDTO wrRunNextDealNode = new WfRunNodeFormDTO();
                wrRunNextDealNode.setCompanyId(currentUser.getOrganizationId());
                wrRunNextDealNode.setRunWfId(curRunNode.getRunWfId());
                wrRunNextDealNode.setWfId(curRunNode.getWfId());
                wrRunNextDealNode.setSort(nextSort);
                wrRunNextDealNode.setIsCurMark(0);
                wrRunNextDealNode.setCurStatus(WfNodeStatus.Finish.getId());
                wrRunNextDealNode.setCurNodeId(StartNodeId);
                wrRunNextDealNode.setCurNodeUserId(StartUserId);
                wrRunNextDealNode.setCurSendTime(curDateTime);
                wrRunNextDealNode.setCurArriveTime(curDateTime);
                wrRunNextDealNode.setCurReadTime(curDateTime);
                wrRunNextDealNode.setCurDealTime(curDateTime);
                wrRunNextDealNode.setApproveResult(dealNode.getResult());
                wrRunNextDealNode.setApproveComments(dealNode.getComment());
                wrRunNextDealNode.setPreRunNodeId(curRunNode.getId());
                wrRunNextDealNode.setPreNodeId(curRunNode.getCurNodeId());
                wrRunNextDealNode.setPreNodeUserId(curRunNode.getCurNodeUserId());
                wrRunNextDealNode.setPreNodeStatus(3);
                WfRunNodeEntity wfRunNode = wfRunNodeService.createWfRunNode(wrRunNextDealNode, currentUser);
                nextWfRunNodeId = wfRunNode.getId();

                // 更新上一节点的nextRunNodeId
                lastNodeEntity.setNextRunNodeId(nextWfRunNodeId);
                wfRunNodeService.updateById(lastNodeEntity);

                //更新主表数据结束
                WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setCurStatus(WfNodeStatus.Finish.getId());
                wfRunWfFormDTO.setCurDealTime(curDateTime);
                wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
                wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setApproveResult(dealResult);
                wfRunWfFormDTO.setApproveComments(dealNode.getComment());
                wfRunWfFormDTO.setWfStatus(WfStatus.Finish.getId());
                wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
                wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
                wfRunWfFormDTO.setPreRunNodeId(runId);
                wfRunWfFormDTO.setCurRunNodeId(nextWfRunNodeId);
                curDateTime = curDateTime.plusSeconds(1);
                wfRunWfFormDTO.setEndTime(curDateTime);
                WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

                sendMessageApplyEnd(wfId, runWfId,curNodeId,WfStatus.Finish,WfApproveResult.Revoke,dealNode.getComment(),beginUserId,startUserId,runWfId,runId);
                sendMessageDealUser(wfId, runWfId,lastRunNode.getCurNodeId(),lastRunNode.getCurNodeUserId(),WfStatus.Finish,WfApproveResult.Revoke,dealNode.getComment(),runWfId,runId, currentUser);
                sendMessageCcUser(wfId, runWfId,curNodeId,WfStatus.Finish,WfApproveResult.Revoke,dealNode.getComment(),runWfId,runId);
            }
            //endregion

            List<WfVarDTO> tmpWfVarList2 = initVars(dealNode.getPlatform(),dealNode.getResult(), curRunNode.getWfId(), curRunNode.getCurNodeId(),
                    curRunNode.getRunWfId(), runId, nextWfRunNodeId, 0L, currentUser.getId(), false, currentUser);
            wfVarDTOList =AppendOutVars(dealNode.getOutVars(),tmpWfVarList2);
            wfVarDTOList=AppendIdeVars(dealNode.getIdeVars(),wfVarDTOList);
            doEvent2(true, wfId, curNodeId, requestData);

        } catch (Exception e) {
            throw new ValidationException("工作流办理出错："+e.getMessage());
        }
    }

    //增加支持接口的处理
    public HttpResultDTO sendRequest(String requestMethod, String url,String queryParams, String bodyData, Map<String, String> headers,
                                     AuthenticationTypeEnum authType, String authValue) throws Exception {

        HttpResultDTO result = new HttpResultDTO();

        try{
            StringBuilder urlBuilder = new StringBuilder(url);
            if (queryParams != null && !queryParams.isEmpty()) {
                urlBuilder.append(url.contains("?") ? "&" : "?");
                urlBuilder.append(queryParams);
//                for (Map.Entry<String, String> entry : queryParams.entrySet()) {
//                    urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()))
//                            .append("=")
//                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()))
//                            .append("&");
//                }
//                // Remove the last '&' character
//                urlBuilder.setLength(urlBuilder.length() - 1);
            }

            URL obj = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(requestMethod);
            if ("PATCH".equals(requestMethod)) {
                // 通过反射修改请求方法
                Field methodField = con.getClass().getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(con, "PATCH"); // 强制改为 PATCH

                // 添加 X-HTTP-Method-Override 头（可选双重保障）
                con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            }
            con.setRequestProperty("User-Agent", USER_AGENT);
            // Add authentication based on the provided type
            if (authType.equals( AuthenticationTypeEnum.API_KEY)) {
                if (headers == null) {
                    headers = new HashMap<>();
                }
                headers.put("X-API-Key", authValue);
            } else if (authType.equals(AuthenticationTypeEnum.BEARER_TOKEN)
                    || authType.equals(AuthenticationTypeEnum.JWT)
                    ||authType.equals(AuthenticationTypeEnum.Inner_Token)) {
                con.setRequestProperty("Authorization", "Bearer " + authValue);
            } else if (authType.equals(AuthenticationTypeEnum.BASIC_AUTH)) {
                String encodedAuth = Base64.getEncoder().encodeToString((authValue + ":").getBytes(StandardCharsets.ISO_8859_1));
                con.setRequestProperty("Authorization", "Basic " + encodedAuth);
            } else {
                throw new IllegalArgumentException("Unsupported authentication type: " + authType);
            }

            // Add custom headers
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                    con.setRequestProperty(headerEntry.getKey(), headerEntry.getValue());
                }
            }

            // Build cookies string from map
//            StringBuilder cookiesBuilder = new StringBuilder();
//            if (cookies != null && !cookies.isEmpty()) {
//                for (Map.Entry<String, String> cookieEntry : cookies.entrySet()) {
//                    if (cookiesBuilder.length() > 0) {
//                        cookiesBuilder.append("; ");
//                    }
//                    cookiesBuilder.append(cookieEntry.getKey()).append("=").append(cookieEntry.getValue());
//                }
//                con.setRequestProperty("Cookie", cookiesBuilder.toString());
//            }

            if ("POST".equals(requestMethod) || "PUT".equals(requestMethod)) {
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(bodyData.getBytes());
                os.flush();
                os.close();
            }

            int responseCode = con.getResponseCode();
            System.out.println(requestMethod + " Response Code :: " + responseCode);

            if (responseCode >= 200 && responseCode < 300) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                result.setCode(responseCode);
                result.setMessage("成功");
                result.setStatus(true);
                result.setData(response.toString());
                System.out.println(response.toString());
            } else {
                result.setCode(responseCode);
                result.setMessage("失败");
                result.setStatus(false);
                result.setData(null);
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }
        }catch (Exception e)
        {
            result.setCode(-1);
            result.setMessage(e.getMessage());
            result.setStatus(false);
            result.setData(null);
            throw new Exception("请求异常");
        }

        return result;
    }

    /**
     *  只要是当前节点办理了，就要更新上一个节点的NextNodeStatus，即在上一个节点更新当前自己的节点状态。
     * @param runNodeId 工作流执行节点ID
     * @param wfNodeStatus 状态默认是Finish
     * @param currentUser
     */
    private  WfRunNodeEntity updatePreNodeStatus(Long runNodeId,WfNodeStatus wfNodeStatus, MemberIdentity currentUser)
    {
        if (!getMemberIsExist(currentUser.getId()))
        {
            String errorMsg = "当前节点办理人["+currentUser.getId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        //只要是当前节点办理了，就要更新上一个节点的NextNodeStatus，即在上一个节点更新当前自己的节点状态。
        String sql=MessageFormat.format("SELECT * FROM wf_run_node WHERE (run_wf_id,sort)=\n" +
                "(SELECT wrn.run_wf_id,wrn.sort-1 FROM wf_run_node wrn WHERE id={0})",runNodeId.toString());
        List<WfRunNodeEntity> preRunNodeList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(WfRunNodeEntity.class));
        if (preRunNodeList==null||preRunNodeList.size()==0) {
            //throw new ValidationException("未找到上一办理节点");
            return null;
        }
        WfRunNodeEntity preRunNode = preRunNodeList.get(0);
        WfRunNodeFormDTO lastNode = new WfRunNodeFormDTO();
        lastNode.setNextNodeStatus(wfNodeStatus.getId());
        return wfRunNodeService.updateWfRunNode(preRunNode.getId(), lastNode, currentUser);
    }

    private WfRunNodeEntity writeRunNodeInfo(WfDealDTO dealNode, MemberIdentity currentUser, Long runId, WfRunNodeEntity curRunNode, LocalDateTime curDateTime, Integer dealResult, Integer nextNodeType, Long nextNodeId, Long nextUserId, Integer sort) {
        WfRunNodeEntity wfNewRunNode = new WfRunNodeEntity();
        wfNewRunNode.setId(0L);
        //默认为空表示最后一个节点

        //更新当前节点的办理数据
        WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
        curNode.setIsCurMark(0);
        curNode.setCurStatus(WfNodeStatus.Finish.getId());
        curNode.setApproveResult(dealNode.getResult());
        curNode.setApproveComments(dealNode.getComment());
        if (nextNodeType == WfNodeType.Deal.getId()) {
            curNode.setNextNodeId(nextNodeId);
            curNode.setNextNodeUserId(nextUserId);
            curNode.setNextNodeStatus(WfNodeStatus.Dealing.getId());

        } else if (nextNodeType == WfNodeType.Finish.getId()) {
            curNode.setNextNodeStatus(WfNodeStatus.Finish.getId());
        }
        if (curNode.getCurReadTime() == null)
            curNode.setCurReadTime(curDateTime);
        curDateTime = curDateTime.plusSeconds(1);
        curNode.setCurDealTime(curDateTime);
        curDateTime = curDateTime.plusSeconds(1);
        curNode.setCurSendTime(curDateTime);
        WfRunNodeEntity curRunNodeEntity = wfRunNodeService.updateWfRunNode(curRunNode.getId(), curNode, currentUser);

        if (nextNodeType == WfNodeType.Deal.getId()) {
            //写下一节点的办理数据
            wfNewRunNode  = writeNextRunNode(currentUser, curRunNode, curDateTime, nextNodeId, nextUserId, sort, WfNodeStatus.Dealing);
            //更新主表状态时间
            curDateTime = curDateTime.plusSeconds(1);
            WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
            wfRunWfFormDTO.setCurStatus(WfNodeStatus.Dealing.getId());
            wfRunWfFormDTO.setCurNodeId(nextNodeId);
            wfRunWfFormDTO.setCurNodeUserId(nextUserId);//'当前节点办理人ID'
            wfRunWfFormDTO.setCurArriveTime(curDateTime);
            wfRunWfFormDTO.setCurApproveResult(dealNode.getResult());
            wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
            wfRunWfFormDTO.setApproveResult(dealResult);
            wfRunWfFormDTO.setApproveComments(dealNode.getComment());
            wfRunWfFormDTO.setWfStatus(WfNodeStatus.Dealing.getId());
            wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
            wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
            wfRunWfFormDTO.setPreRunNodeId(runId);
            wfRunWfFormDTO.setCurRunNodeId(wfNewRunNode.getId());
            WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);

            // 更新上一执行节点的NextRunNodeId
            curRunNodeEntity.setNextRunNodeId(wfRun.getId());
            wfRunNodeService.updateById(curRunNodeEntity);

        } else if (nextNodeType == WfNodeType.Finish.getId()) //结束节点
        {
            if(!getMemberIsExist(currentUser.getId()))
            {
                String errorMsg = "当前节点办理人["+currentUser.getId()+"]不存在!";
                throw new ValidationException(errorMsg);
            }
            //如果下一节点是结束节点，更新主表结束，更新当前节点信息结束。工作流结束。
            //更新当前节点流转节点表
            WfRunNodeFormDTO wrRunNextDealNode = new WfRunNodeFormDTO();
            wrRunNextDealNode.setIsCurMark(0);
            wrRunNextDealNode.setCurStatus(WfNodeStatus.Finish.getId());
            curDateTime = curDateTime.plusSeconds(1);
            wrRunNextDealNode.setCurDealTime(curDateTime);
            curDateTime = curDateTime.plusSeconds(1);
            wrRunNextDealNode.setCurArriveTime(curDateTime);
            wfRunNodeService.updateWfRunNode(runId, wrRunNextDealNode, currentUser);
            //更新主表状态为结束，时间
            WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
            wfRunWfFormDTO.setCurStatus(WfNodeStatus.Finish.getId());
            wfRunWfFormDTO.setCurApproveResult(dealResult);
            wfRunWfFormDTO.setCurApproveComments(dealNode.getComment());
            wfRunWfFormDTO.setApproveResult(dealResult);
            wfRunWfFormDTO.setApproveComments(dealNode.getComment());
            curDateTime = curDateTime.plusSeconds(1);
            wfRunWfFormDTO.setEndTime(curDateTime);
            wfRunWfFormDTO.setWfStatus(WfStatus.Finish.getId());
            wfRunWfFormDTO.setPreNodeId(curRunNode.getCurNodeId());
            wfRunWfFormDTO.setPreNodeUserId(curRunNode.getCurNodeUserId());
//            wfRunWfFormDTO.setPreRunNodeId(runId);  结束保留上一次数据
//            wfRunWfFormDTO.setCurRunNodeId(wfNewRunNode.getId());
            WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(curRunNode.getRunWfId(), wfRunWfFormDTO, currentUser);
        }
        return wfNewRunNode;
    }

    private WfRunNodeEntity writedWithdrawRunNode(WfDealDTO dealNode, MemberIdentity currentUser, WfRunNodeEntity curRunNode, LocalDateTime curDateTime, int nextSort,
                                                  Long pNextNodeId, Long pNextDealUserId, WfNodeStatus nodeStatus) {
        if (!getMemberIsExist(pNextDealUserId))
        {
            String errorMsg = "下一节点办理人["+currentUser.getId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        if(!getMemberIsExist(curRunNode.getCurNodeUserId()))
        {
            String errorMsg = "当前点办理人["+curRunNode.getCurNodeUserId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        WfRunNodeFormDTO wrWithdrawNode = new WfRunNodeFormDTO();
        wrWithdrawNode.setCompanyId(currentUser.getOrganizationId());
        wrWithdrawNode.setRunWfId(curRunNode.getRunWfId());
        wrWithdrawNode.setWfId(curRunNode.getWfId());
        wrWithdrawNode.setSort(nextSort);
        wrWithdrawNode.setIsCurMark(0);
        wrWithdrawNode.setCurStatus(WfNodeStatus.Finish.getId());
        wrWithdrawNode.setApproveResult(WfApproveResult.Withdraw.getId());
        wrWithdrawNode.setApproveComments(dealNode.getComment());
        wrWithdrawNode.setCurSendTime(curDateTime);
        wrWithdrawNode.setCurNodeId(curRunNode.getCurNodeId());
        wrWithdrawNode.setCurNodeUserId(curRunNode.getCurNodeUserId());
        wrWithdrawNode.setCurDealTime(curDateTime);
        wrWithdrawNode.setCurArriveTime(curDateTime);
        wrWithdrawNode.setCurReadTime(curDateTime);
        wrWithdrawNode.setNextNodeId(pNextNodeId);
        wrWithdrawNode.setNextNodeUserId(pNextDealUserId);
        wrWithdrawNode.setNextNodeStatus(nodeStatus.getId());

        wrWithdrawNode.setPreRunNodeId(curRunNode.getId());
        wrWithdrawNode.setPreNodeId(curRunNode.getCurNodeId());
        wrWithdrawNode.setPreNodeUserId(curRunNode.getCurNodeUserId());
        wrWithdrawNode.setPreNodeStatus(3);
        return wfRunNodeService.createWfRunNode(wrWithdrawNode, currentUser);
    }

    private WfRunNodeEntity writeNextRunNode(MemberIdentity currentUser, WfRunNodeEntity curRunNode, LocalDateTime curDateTime, Long nextNodeId, Long nextUserId, Integer nextSort,
                                             WfNodeStatus wfNodeStatus) {
        if (!getMemberIsExist(currentUser.getId()))
        {
            String errorMsg = "当前节点办理人["+currentUser.getId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        if (!getMemberIsExist(nextUserId))
        {
            String errorMsg = "下一节点办理人["+nextUserId+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        WfRunNodeFormDTO wrRunNextDealNode = new WfRunNodeFormDTO();
        wrRunNextDealNode.setCompanyId(currentUser.getOrganizationId());
        wrRunNextDealNode.setRunWfId(curRunNode.getRunWfId());
        wrRunNextDealNode.setWfId(curRunNode.getWfId());
        wrRunNextDealNode.setSort(nextSort);
        wrRunNextDealNode.setIsCurMark(1);
        wrRunNextDealNode.setCurStatus(wfNodeStatus.getId());
        wrRunNextDealNode.setCurNodeId(nextNodeId);
        wrRunNextDealNode.setCurNodeUserId(nextUserId);
        wrRunNextDealNode.setCurArriveTime(curDateTime);

        wrRunNextDealNode.setPreRunNodeId(curRunNode.getId());
        wrRunNextDealNode.setPreNodeId(curRunNode.getCurNodeId());
        wrRunNextDealNode.setPreNodeUserId(curRunNode.getCurNodeUserId());
        wrRunNextDealNode.setPreNodeStatus(3);
        return wfRunNodeService.createWfRunNode(wrRunNextDealNode, currentUser);
    }

    /**
     * 获取转换后的表达式
     *
     * @param srcExp
     */
    private String getTransExp(String srcExp) {
        //系统变量
        if(srcExp==null||srcExp.isEmpty())
            return  "";
        //预处理
        srcExp=getTransExpPre(srcExp);
        String newExp = srcExp;
        Pattern p = Pattern.compile("\\#\\{(\\w+)\\}");
        Matcher m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //如果存在则直接替换，如果不存在则替换为null
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }
        //行变量工作流
        p = Pattern.compile("\\?wf_row\\[(\\w+)\\]");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }
        //执行节点变量
        p = Pattern.compile("\\?node_row\\[(\\w+)\\]");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }

        //执行下一节点变量
        p = Pattern.compile("\\?next_node_row\\[(\\w+)\\]");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            if (varFindDTO!=null&&varFindDTO.getExist())
                newExp = newExp.replace(m.group(), varFindDTO.getVarValue());
        }

        //外部变量替换，外部变量$开头,组件变量
        p = Pattern.compile("\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);

        }

        //外部变量替换，外部变量$开头，行变量
        p = Pattern.compile("\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}\\[(\\w+)]");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }

        //外部变量，业务表变量
        p = Pattern.compile("\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}\\[(\\w+)]");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            //替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }

        // 外部变量替换，外部变量@开头
        p = Pattern.compile("\\@\\w+");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            // 查找变量值
            WfVarFindDTO varFindDTO = findVarValue(varCode);
            // 替换变量
            newExp = dealVarReplaceNull(newExp, m, varFindDTO);
        }

        return newExp;
    }

    private String dealVarReplaceNull(String newExp, Matcher m, WfVarFindDTO varFindDTO) {
        if (varFindDTO !=null&& varFindDTO.getExist()&& varFindDTO.getVarValue()!=null)
        {
            newExp = newExp.replace(m.group(), varFindDTO.getVarValue());
        }
        else
        {
            newExp = newExp.replace(m.group(), "null");
        }
        return newExp;
    }

    /**
     * 预处理单引号变量
     * 用于查找带单引号引起的变量，如果找不到连单引号一起替换为null。如果找到就保留单引号并且给赋值。
     * @param srcExp
     * @return
     */
    private String getTransExpPre(String srcExp) {
        //系统变量
        if(srcExp==null||srcExp.isEmpty())
            return  "";

        String newExp = srcExp;
        Pattern p = Pattern.compile("'\\#\\{(\\w+)\\}'");
        Matcher m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);

        }
        //行变量工作流
        p = Pattern.compile("'\\?wf_row\\[(\\w+)\\]'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);
        }
        //执行节点变量
        p = Pattern.compile("'\\?node_row\\[(\\w+)\\]'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);
        }

        //执行下一节点变量
        p = Pattern.compile("'\\?next_node_row\\[(\\w+)\\]'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);
        }

        //外部变量替换，外部变量$开头,组件变量
        p = Pattern.compile("'\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);

        }

        //外部变量替换，外部变量$开头，行变量
        p = Pattern.compile("'\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}\\[(\\w+)]'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);
        }

        //外部变量，业务表变量
        p = Pattern.compile("'\\$\\#\\w+\\.\\w+\\.\\{(\\w+)\\}\\[(\\w+)]'");
        m = p.matcher(srcExp);
        while (m.find()) {
            String varCode = m.group();
            //查找变量值
            WfVarFindDTO varFindDTO = findVarValuePre(varCode);
            //替换变量
            newExp = dealVarChangeSingleQuote(newExp, m, varFindDTO);
        }

        return newExp;
    }

    /**
     * 处理带单引号的变量替换
     * @param newExp
     * @param m
     * @param varFindDTO
     * @return
     */
    private String dealVarChangeSingleQuote(String newExp, Matcher m, WfVarFindDTO varFindDTO) {
        if (varFindDTO !=null&& varFindDTO.getExist()&&varFindDTO.getVarValue()!=null)
        {
            //如果找到保留单引号并且替换内容
            newExp = newExp.replace(m.group(),"'"+ varFindDTO.getVarValue()+"'");
        }else
        {
            //如果找不到去掉单引号变为null
            newExp = newExp.replace(m.group(),"null");
        }
        return newExp;
    }

    private String getTransSql(String srcSql) {
        return getTransExp(srcSql);
    }


    private WfVarFindDTO findVarValuePre(String pVarCode)
    {
        //如果变量以单引号开头，则去掉变量开头的第一个单引号
        if(pVarCode.startsWith("'"))
        {
            pVarCode=pVarCode.substring(1);
        }
        //如果变量以单引号结束，则去掉变量结尾的单引号
        if(pVarCode.endsWith("'"))
        {
            pVarCode=pVarCode.substring(0,pVarCode.length()-1);
        }
        return findVarValue(pVarCode);
    }
    /**
     * 查找变量值
     *
     * @param pVarCode
     * @return
     */
    private WfVarFindDTO findVarValue(String pVarCode) {
        WfVarFindDTO varFindDTO = new WfVarFindDTO();
        varFindDTO.setExist(false);

        for (int i = 0; i < wfVarDTOList.size(); i++) {
            WfVarDTO varDTO = wfVarDTOList.get(i);
            if (varDTO.getVarCode().equals(pVarCode)) {
                varFindDTO.setVarValue(varDTO.getVarValue());
                varFindDTO.setExist(true);
                break;
            }
        }
        return varFindDTO;
    }


    /**
     * 变量初始化，每次使用前需要调用
     *
     * @param wfId        工作流ID
     * @param nodeId      节点ID
     * @param runWfId     工作流办理ID wf_run_wf
     * @param runNodeId   工作流当前办理节点ID wf_run_node 主键
     * @param nextRunNodeId 工作流下一个办理节点ID wf_run_node 主键
     * @param outId
     * @param applyUserId
     * @param isApplyNode
     * @param currentUser
     * @return
     */
    private List<WfVarDTO> initVars(Integer platform,Integer deal, Long wfId, Long nodeId, Long runWfId,
                                    Long runNodeId,Long nextRunNodeId, Long outId, Long applyUserId, boolean isApplyNode, MemberIdentity currentUser) {


        //region 申请人信息转换
        String applyUserName = "";
        Long applyDeptId = 0L;
        String applydeptName = "";

        if (isApplyNode) {
            String sql = "SELECT b.id AS apply_dept_id, b.name AS apply_dept_name " +
                    "FROM ark_uims_org_employee e " +
                    "LEFT JOIN ark_uims_org_department b ON b.id = e.department_id " +
                    "WHERE e.member_id = ? AND e.is_deleted = 0";
            String userNameSql = MessageFormat.format("SELECT f_get_user_name({0}) as apply_user_name", applyUserId.toString());

            try {
                //获取sql查询的结果
                String userName = jdbcTemplate.queryForObject(userNameSql, String.class);
                if (userName != null) {
                    applyUserName = userName;
                }

                List<Map<String, Object>> map1 = jdbcTemplate.queryForList(sql, applyUserId);
                if (map1 != null && !map1.isEmpty()) {
                    Map<String, Object> firstMap = map1.get(0);
                    String deptIdStr = firstMap.get("apply_dept_id") == null ? null : firstMap.get("apply_dept_id").toString();
                    applyDeptId = deptIdStr != null && !deptIdStr.isEmpty() ? Long.parseLong(deptIdStr) : null;
                    applydeptName = firstMap.get("apply_dept_name") == null ? null : firstMap.get("apply_dept_name").toString();
                }
            } catch (Exception e) {
                throw new ValidationException("获取工作流办理人员出错：" + e.getMessage());
            }
        }
        //endregion

        List<WfVarDTO> wfVarList = new ArrayList<>();
        WfVarDTO wfVarDTOPlatForm=new WfVarDTO();
        wfVarDTOPlatForm.setVarValue(platform.toString());
        wfVarDTOPlatForm.setVarCode("#{platform}");
        wfVarDTOPlatForm.setVarId("platform");
        wfVarDTOPlatForm.setVarName("platform");
        wfVarList.add(wfVarDTOPlatForm);


        //添加办理类型
        /**
         *     Start("发起",0),
         *     Agree("同意",1),
         *     Refuse("拒绝",2),
         *     Return("退回",3),
         *     Invalid("作废",4),
         *     Withdraw("撤回",5),
         *     Deal("办理",6),
         *     Revoke("撤销",7),
         *     Repulse("打回",8)
         */
        WfVarDTO wfVarDTODealType=new WfVarDTO();
        wfVarDTODealType.setVarValue(deal.toString());
        wfVarDTODealType.setVarCode("#{deal}");
        wfVarDTODealType.setVarId("deal");
        wfVarDTODealType.setVarName("deal");
        wfVarList.add(wfVarDTODealType);

        //region 通过wfid获取对应的工作流的信息。
        String sqlWf = MessageFormat.format("SELECT d.wf_code, d.apply_user_id,d.apply_user_name,\n" +
                "d.apply_dept_id,d.apply_dept_name,d.create_user_id,\n" +
                "d.create_user_name,e.var_value AS out_id,  d.wf_code,c.table_name, b.node_name, wdd.name wf_name,wdd.wf_type,\n" +
                "f_sys_code_name(''wf_def_define_wf_type'',wf_type) wf_type_name\n" +
                "from wf_def_define wdd LEFT JOIN \n" +
                "(SELECT wdn.name node_name FROM wf_def_node wdn WHERE wdn.wf_id={0} AND id={1}) b ON 1=1\n" +
                "LEFT JOIN (SELECT wdm.table_name from wf_def_map wdm WHERE wdm.wf_id={0}) c ON 1=1 \n" +
                "LEFT JOIN (SELECT wrw.wf_code, wrw.begin_user_id apply_user_id,f_get_user_name(begin_user_id) apply_user_name,\n" +
                "wrw.begin_dept_id apply_dept_id,f_get_dept_name(begin_dept_id) apply_dept_name,wrw.creator_id create_user_id,\n" +
                "f_get_user_name(wrw.creator_id) create_user_name\n" +
                "from wf_run_wf wrw WHERE wrw.id={2}) d ON 1=1 \n" +
                "LEFT JOIN (SELECT wrv.var_value FROM wf_run_var wrv WHERE wrv.run_wf_id={2} AND wrv.var_id=''out_id'') e ON 1=1 \n" +
                "WHERE id={0} ", wfId.toString(), nodeId.toString(), runWfId.toString());
        List<Map<String, Object>> wfMaps = jdbcTemplate.queryForList(sqlWf);
        if (wfMaps != null && wfMaps.size() > 0) {
            Map<String, Object> map = wfMaps.get(0);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String row_tile = (String) iterator.next();
                String row_vale = map.get(row_tile) == null ? "" : map.get(row_tile).toString();
                if (row_tile.equals("out_id")) {
                    //如果outId取不到参数就使用外部参数。
                    if (row_vale == "" && outId != null && outId != 0L) row_vale = outId.toString();
                }
                if (isApplyNode) {
                    if (row_tile.equals("apply_user_id")) {
                        row_vale =applyUserId==null?"0":applyUserId.toString();
                    } else if (row_tile.equals("apply_user_name")) {
                        row_vale = applyUserName;
                    } else if (row_tile.equals("apply_dept_id")) {
                        row_vale =applyDeptId==null?"0": applyDeptId.toString();
                    } else if (row_tile.equals("apply_dept_name")) {
                        row_vale = applydeptName;
                    }
                }
                WfVarDTO wfVarDTO = new WfVarDTO();
                wfVarDTO.setVarId(row_tile);
                wfVarDTO.setVarCode("#{" + row_tile + "}");
                wfVarDTO.setVarValue(row_vale);
                wfVarDTO.setVarName(row_tile);
                wfVarList.add(wfVarDTO);
            }
        }
        //endregion
        //region 查询源数据表
        String sqlVar = "SELECT wv.var_type, wv.var_id var_id,var_name,wv.var_tag var_code,'''' val_value from wf_var wv WHERE var_type=1 and  wv.status=1 AND wv.is_deleted=0 ORDER BY wv.sort";
        List<WfVarDTO> vars = jdbcTemplate.query(sqlVar, BeanPropertyRowMapper.newInstance(WfVarDTO.class));

        for (int var = 0; var < vars.size(); var++) {

            WfVarDTO wfVarDTO = vars.get(var);
            String varId = wfVarDTO.getVarId();

            if (varId.equals("login_user_id")) {
                wfVarDTO.setVarValue(currentUser.getId().toString());
            } else if (varId == "login_user_name") {
                wfVarDTO.setVarValue(currentUser.getName());
            }
            if (varId.equals("wf_id")) {
                wfVarDTO.setVarValue(wfId.toString());
            } else if (varId.equals("node_id")) {
                wfVarDTO.setVarValue(nodeId.toString());
            } else if (varId.equals("sys_time")) {
                wfVarDTO.setVarValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else if (varId.equals("run_wf_id")) {
                wfVarDTO.setVarValue(runWfId.toString());
            } else if (varId.equals("run_wf_node_id")) {
                wfVarDTO.setVarValue(runNodeId.toString());
            }else if (varId.equals("run_wf_next_node_id")) {
                wfVarDTO.setVarValue(nextRunNodeId.toString());
            }
            wfVarList.add(wfVarDTO);
        }
        //endregion
        //region 行变量
        //生成nodeRow行变量，格式 ?node_row[字段名]
        String nodeRowSql = MessageFormat.format("SELECT * FROM wf_run_node wrn WHERE wrn.id={0}", runNodeId.toString());
        //获取行数转为键值对放在列表里面
        List<Map<String, Object>> listMaps = jdbcTemplate.queryForList(nodeRowSql);
        if (listMaps != null && listMaps.size() > 0) {
            Map<String, Object> map = listMaps.get(0);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String row_tile = (String) iterator.next();
                String row_vale = map.get(row_tile) == null ? "" : map.get(row_tile).toString();
                WfVarDTO wfVarDTO = new WfVarDTO();
                wfVarDTO.setVarId("?node_row[" + row_tile + "]");
                wfVarDTO.setVarValue(row_vale);
                wfVarDTO.setVarCode("?node_row[" + row_tile + "]");
                wfVarList.add(wfVarDTO);
            }
        }

        //获取下一个节点的数据
        String nextNodeRowSql = MessageFormat.format("SELECT * FROM wf_run_node wrn WHERE wrn.id={0}", nextRunNodeId.toString());
        //获取行数转为键值对放在列表里面
        List<Map<String, Object>> nextListMaps = jdbcTemplate.queryForList(nextNodeRowSql);
        if (nextListMaps != null && nextListMaps.size() > 0) {
            Map<String, Object> map = nextListMaps.get(0);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String row_tile = (String) iterator.next();
                String row_vale = map.get(row_tile) == null ? "" : map.get(row_tile).toString();
                WfVarDTO wfVarDTO = new WfVarDTO();
                wfVarDTO.setVarId("?next_node_row[" + row_tile + "]");
                wfVarDTO.setVarValue(row_vale);
                wfVarDTO.setVarCode("?next_node_row[" + row_tile + "]");
                wfVarList.add(wfVarDTO);
            }
        }

        //生成wfRow行变量，格式 ?wf_row[字段名]
        String wfRowSql = MessageFormat.format("SELECT * FROM wf_run_wf wrn WHERE wrn.id={0}", runWfId.toString());
        //获取行数转为键值对放在列表里面
        List<Map<String, Object>> wfMapRows = jdbcTemplate.queryForList(wfRowSql);
        if (wfMapRows != null && wfMapRows.size() > 0) {
            Map<String, Object> map = wfMapRows.get(0);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String row_tile = (String) iterator.next();
                String row_vale = map.get(row_tile) == null ? "" : map.get(row_tile).toString();
                WfVarDTO wfVarDTO = new WfVarDTO();
                wfVarDTO.setVarId("?wf_row[" + row_tile + "]");
                wfVarDTO.setVarValue(row_vale);
                wfVarDTO.setVarCode("?wf_row[" + row_tile + "]");
                wfVarList.add(wfVarDTO);
            }
        }

        if (wfVarList.size()>0)
        {

        }
        //endregion
        return wfVarList;
    }


    /**
     * 获取办理节点和办理人
     *
     * @param wfId
     * @param nodeId 节点ID
     * @param userId 如果是开始节点传applyUserId 必须是申请人，其他节点用当前登录人
     * @return
     */
    private List<WfNextNodeDTO> findRelationNextNodesAndUsers(Long wfId, Long nodeId, Long userId) {
        //用于手动选择下一个节点
        //查找开始节点之后的关联线，可能是多个
        String realationSql = MessageFormat.format("SELECT a.rule relationrule,a.rule_exp relationruleexp, b.* FROM wf_def_node_relation a LEFT JOIN wf_def_node b\n" +
                "ON a.wf_id=b.wf_id AND a.to_node_id=b.id\n" +
                "WHERE a.wf_id={0} AND a.from_node_id={1} and b.node_type >1  ORDER BY a.sort,b.node_type", wfId.toString(), nodeId.toString());

        List<WfDefNodeAndRelationEntity> nodeEntities = jdbcTemplate.query(realationSql, BeanPropertyRowMapper.newInstance(WfDefNodeAndRelationEntity.class));
        if (nodeEntities == null || nodeEntities.size() == 0) {
            return null;
        }
        List<WfNextNodeDTO> nextNodeDTOS = new ArrayList<>();
        for (int n = 0; n < nodeEntities.size(); n++) {

            WfDefNodeAndRelationEntity nodeEntity = nodeEntities.get(n);
            //计算节点逻辑
            Integer relationRule = nodeEntity.getRelationRule();
            String relationRuleExp = nodeEntity.getRelationRuleExp();
            String relationResult = "1";
            if (relationRule.equals(1)) {
                relationResult = "1";
            } else if (relationRule.equals(2)) {
                String ruleExpResult = getTransExp(relationRuleExp);
                relationResult = getSqlValue(ruleExpResult);
            } else {
                String ruleExpResult = relationRuleExp;
                relationResult = "1";
            }

            if (relationResult.equals("1")) {
                //组装节点信息给前端使用
                WfNextNodeDTO nextNodeDTO = new WfNextNodeDTO();
                nextNodeDTO.setNodeId(nodeEntity.getId());
                nextNodeDTO.setNodeName(nodeEntity.getName());
                nextNodeDTO.setNodeType(nodeEntity.getNodeType());
                nextNodeDTO.setWfId(wfId);
                nextNodeDTOS.add(nextNodeDTO);
                if (nodeEntity.getNodeType() == 2) {
                    //获取每个节点的人员信息
                    List<WfNodeUserDTO> wfNodeUserDTOList = findNodeDealUsers(nodeEntity.getSelectUserRule(),nodeEntity.getLeaderAuto(), wfId,
                            nextNodeDTO.getNodeId(), userId);
                    nextNodeDTO.setUsers(wfNodeUserDTOList);
                } else if (nodeEntity.getNodeType() == 3) {
                    //模拟结束节点数据的人
                    List<WfNodeUserDTO> wfNodeUserDTOList = new ArrayList<>();
                    WfNodeUserDTO userDTO = new WfNodeUserDTO();
                    userDTO.setUserId(0L);
                    userDTO.setUserName("结束");
                    wfNodeUserDTOList.add(userDTO);
                    nextNodeDTO.setUsers(wfNodeUserDTOList);
                }
            }

        }
        return nextNodeDTOS;
    }

    private List<WfNextNodeDTO> findRelationPreNodesAndUsers(Long wfId, Long nodeId, Long userId)
    {
        return null;
    }
    /**
     * 判断是否为开始节点
     *
     * @param pReturnNodeId
     * @param runWfId
     * @return
     */
    private boolean isStartNode(Long pReturnNodeId, Long runWfId) {
        //获取开始节点,需要判断退回节点是不是开始节点如果是开始节点状态需要变更
        WfRunWfEntity startNodeInfo = getStartNode(runWfId);
        Long startNodeId = startNodeInfo.getBeginNodeId();
        //Long startUserId=startNodeInfo.getBeginUserId();
        boolean isStartNode = false;
        if (startNodeId.longValue() == pReturnNodeId.longValue()) {
            isStartNode = true;
        }
        return isStartNode;
    }

    private Long findStartNode(Long wfId) {
        //用于手动选择下一个节点
        //查找开始节点之后的关联线，可能是多个
        String nodeSql = MessageFormat.format("SELECT wdn.id FROM wf_def_node wdn WHERE wdn.wf_id={0} AND wdn.node_type=1", wfId.toString());
        String nodeValue = getSqlValue(nodeSql);
        if (nodeValue == null) nodeValue = "0";
        return Long.parseLong(nodeValue);
    }


    /**
     * 根据选人规则获取办理人
     *
     * @param select_user_rule
     * @param wf_id
     * @param node_id
     * @param applyUserId
     * @return
     */
    private List<WfNodeUserDTO> findNodeDealUsers(Integer select_user_rule,Integer leaderAuto, Long wf_id, Long node_id, Long applyUserId) {
//        1指定人员
//        2部门主管
//        3直属主管
//        4角色
//        5发起人自选
//        6发起人自己
//        7表单内的联系人 20表达式
        try {
            List<WfNodeUserDTO> findUsers = new ArrayList<>();//查找目标的用户ID
            //region 1 指定人员
            if (select_user_rule == WfSelectUserRuleEnums.ToUser.getId()) {
                //获取列表的中所有人
                String sql = MessageFormat.format("SELECT auoe.member_id userId, auoe.name userName FROM wf_def_node_user wdnu JOIN ark_uims_org_employee auoe ON wdnu.user_id = auoe.member_id and auoe.is_deleted = 0 \n" +
                        "WHERE wdnu.wf_id={0} AND wdnu.node_id={1} AND wdnu.type=1 and wdnu.status=1 AND wdnu.is_deleted=0", wf_id.toString(), node_id.toString());
                List<WfNodeUserDTO> userDTOS = jdbcTemplate.query(sql,
                        BeanPropertyRowMapper.newInstance(WfNodeUserDTO.class));
                if (userDTOS != null || userDTOS.size() > 0) {
                    for (int i = 0; i < userDTOS.size(); i++) {
                        WfNodeUserDTO userDTO = userDTOS.get(i);
                        if (userDTO.getUserId().equals(-1L) || userDTO.getUserId().equals(0L) || userDTO.getUserName() == "-1")
                            continue;
                        findUsers.add(userDTO);
                    }
                }
            }
            //endregion
            //region 2部门主管
            else if (select_user_rule == WfSelectUserRuleEnums.DeptManager.getId()) {
                //获取主管Id  f_wf_get_leader_id  f_wf_get_pleader_by_member_id 为旧的函数
                String sql = MessageFormat.format("select  userId,f_get_user_name(userId) userName\n" +
                        "FROM (SELECT f_wf_get_leader_by_member_id({0}) userId) AS w", applyUserId.toString());
                List<WfNodeUserDTO> userDTOS = jdbcTemplate.query(sql,
                        BeanPropertyRowMapper.newInstance(WfNodeUserDTO.class));
                if (userDTOS==null||userDTOS.size()==0)
                {
                    if (leaderAuto.equals(1)) {
                        sql = MessageFormat.format("select userId, f_get_user_name(userId) userName\n" +
                                "FROM (SELECT f_wf_get_pleader_by_member_id({0}) userId) AS w", applyUserId.toString());
                    }
                    userDTOS = jdbcTemplate.query(sql,
                            BeanPropertyRowMapper.newInstance(WfNodeUserDTO.class));
                }
                if (userDTOS != null || userDTOS.size() > 0) {
                    for (int i = 0; i < userDTOS.size(); i++) {
                        WfNodeUserDTO userDTO = userDTOS.get(i);
                        if (userDTO.getUserId().equals(-1L) || userDTO.getUserId().equals(0L) || userDTO.getUserName() == "-1")
                            continue;
                        findUsers.add(userDTO);
                    }
                }
            }
            //endregion
            //region 6发起人自己
            else if (select_user_rule == WfSelectUserRuleEnums.ApplyUser.getId()) {

                String sql = MessageFormat.format("select {0} userId,f_get_user_name({0}) userName  from dual", applyUserId.toString());
                List<WfNodeUserDTO> userDTOS = jdbcTemplate.query(sql,
                        BeanPropertyRowMapper.newInstance(WfNodeUserDTO.class));
                if (userDTOS != null || userDTOS.size() > 0) {
                    for (int i = 0; i < userDTOS.size(); i++) {
                        WfNodeUserDTO userDTO = userDTOS.get(i);
                        if (userDTO.getUserId().equals(-1L) || userDTO.getUserId().equals(0L) || userDTO.getUserName() == "-1")
                            continue;
                        findUsers.add(userDTO);
                    }
                }
            }
            //endregion
            //region 20表达式
            else if (select_user_rule == WfSelectUserRuleEnums.Exp.getId()) {

                //获取工作流配置表达式,注意列名称必须是userId,userName
                String sql = MessageFormat.format("SELECT select_user_rule_exp from wf_def_node wdn \n" +
                        "WHERE wdn.wf_id={0} AND wdn.id={1} and wdn.is_deleted=0 and wdn.status=1", wf_id.toString(), node_id.toString());
                String expSql = getSqlValue(sql);
                if (expSql != null) {
                    //表达式变量替换
                    String newExpSql=getTransSql(expSql);
                    List<WfNodeUserDTO> userDTOS = jdbcTemplate.query(newExpSql, BeanPropertyRowMapper.newInstance(WfNodeUserDTO.class));
                    if (userDTOS != null || userDTOS.size() > 0) {
                        for (int i = 0; i < userDTOS.size(); i++) {
                            WfNodeUserDTO userDTO = userDTOS.get(i);
                            if (userDTO.getUserId().equals(-1L) || userDTO.getUserId().equals(0L) || userDTO.getUserName() == "-1")
                                continue;
                            findUsers.add(userDTO);
                        }
                    }
                }
            }
            //endregion

            else {

                // select_user_rule == 3 || select_user_rule == 4 || select_user_rule == 5
            }
            return findUsers;
        } catch (Exception e) {
            throw new ValidationException("获取工作流办理人员出错：" + e.getMessage());
        }
    }

    private void doEvent(boolean afterDeal, Long wfId, Long startNodeId) {
        String newSql = MessageFormat.format("SELECT  t.trigger_mode,t.trigger_exp,  t.trigger_type,t.rule,t.rule_exp  FROM wf_def_node_event t \n" +
                "WHERE t.wf_id={0} and t.node_id={1} AND t.is_deleted=0 AND t.status=1", wfId.toString(), startNodeId.toString());
        //获取表查询数据
        // trigger_mode int DEFAULT 1 COMMENT '1直接执行默认，2SQL表达式',
        // trigger_exp varchar(1000) DEFAULT NULL COMMENT '条件表达式',

        List<Map<String, Object>> map1 = jdbcTemplate.queryForList(newSql);
        if (map1 != null && map1.size() > 0) {
            for (int i = 0; i < map1.size(); i++) {
                String trigger_mode = map1.get(i).get("trigger_mode").toString();
                String trigger_exp = map1.get(i).get("trigger_exp").toString();

                //先判断条件
                String TriggerExpValue="1";
                if (trigger_mode!=null&& trigger_mode.equals("2")) {
                    //计算表达式
                    String newTrigger_exp = getTransSql(trigger_exp);
                    //计算SQL表达式结果
                    TriggerExpValue = getSqlValue(newTrigger_exp);
                }

                if (!TriggerExpValue.equals("1")) continue;

                String rule = map1.get(i).get("rule").toString();
                String trigger_type = map1.get(i).get("trigger_type").toString();
                String rule_exp = map1.get(i).get("rule_exp").toString();
                String newRule_expSql = getTransSql(rule_exp);
                //触发时机1到达前，2办理后
                if (afterDeal && trigger_type.equals("2")) {
                    if (rule.equals("2")) {
                        jdbcTemplate.execute(newRule_expSql);
                    }
                } else if (!afterDeal && trigger_type.equals("1")) {
                    if (rule.equals("2")) {
                        jdbcTemplate.execute(newRule_expSql);
                    }
                } else {
                    continue;
                }
            }

        }
    }

    private void doEvent2(boolean afterDeal, Long wfId, Long startNodeId,HttpRequestDTO data) throws Exception {

        try {
            String outServerUrl=data.getServerUrl();
            String outToken=data.getToken();

            List<DefNodeEventEntity> eventEntityList =defNodeEventMapper.getDefNodeEventCanUseList(wfId,startNodeId);
            if (eventEntityList==null||eventEntityList.size()==0) return;
            //循环
            for (DefNodeEventEntity eventEntity:eventEntityList){

                String trigger_mode = eventEntity.getTriggerMode().toString();
                String trigger_exp = eventEntity.getTriggerExp();

                //先判断条件
                String TriggerExpValue="1";
                if (trigger_mode!=null&& trigger_mode.equals(TriggerModeEnum.SQLExp.getIdString())) {
                    //计算表达式
                    String newTrigger_exp = getTransSql(trigger_exp);
                    //计算SQL表达式结果
                    TriggerExpValue = getSqlValue(newTrigger_exp);
                }
                //不等于1则进行下一个循环
                if (!TriggerExpValue.equals("1")) continue;

                String rule = eventEntity.getRule().toString();
                Integer trigger_type = eventEntity.getTriggerType();
                String rule_exp =eventEntity.getRuleExp();
                if (rule.equals(RuleEnum.SQL.getIdString()))
                {
                    String newRule_expSql = getTransSql(rule_exp);
                    //触发时机1到达前，2办理后
                    if (afterDeal && trigger_type.equals(TriggerTypeEnum.AfterEvent.getId())) {
                        jdbcTemplate.execute(newRule_expSql);
                    } else if (!afterDeal && trigger_type.equals(TriggerTypeEnum.BeforeEvent.getId())) {
                        jdbcTemplate.execute(newRule_expSql);
                    } else {
                        continue;
                    }
                }
                else if (rule.equals(RuleEnum.Http.getIdString())) {
                    String exeUrl=eventEntity.getUrl();
                    exeUrl = getTransSql(exeUrl);

                    String exeParams=eventEntity.getParams();
                    Integer exeUrlType=eventEntity.getUrlType();
                    RequestMethodEnum exeUrlRequestMethod=
                            executionMethodEnumFromId(eventEntity.getUrlRequestMethod());
                    Integer exeBodyType=eventEntity.getBodyType();
                    String exeBody=eventEntity.getBody();
                    AuthenticationTypeEnum exeAuthType=AuthenticationTypeEnumFromId(eventEntity.getAuthType());
                    String exeBearertoken="";
                    if (exeUrlType.equals(HttpUrlTypeEnum.Relative.getId()))
                    {
                        exeUrl=outServerUrl+exeUrl;
                        exeBearertoken=outToken;
//                    WriteLog(isLog, triggerId,uuid,sort,"执行"+eventEntity.getName()+" 相对地址 exeUrl:"+exeUrl+" token:"+exeBearertoken);
//                    sort++;
                    }else
                    {
                        exeBearertoken=eventEntity.getBearertoken();
                        exeBearertoken=outToken;
//                    WriteLog(isLog, triggerId,uuid,sort,"执行"+eventEntity.getName()+"绝对地址 exeUrl:"+exeUrl+" token:"+exeBearertoken);
//                    sort++;
                    }
                    String exeBasicAuthUserName=eventEntity.getBasicAuthUserName();
                    String exeBasicAuthPassWord=eventEntity.getBasicAuthPassWord();

                    Map<String, String> exeHeaders=new HashMap<>();
                    // Custom headers
                    if (exeBodyType.equals(HttpBodyTypeEnum.Xml.getId()))
                    {
                        exeHeaders.put("Content-Type", "application/xml");
                    }else
                    {
                        exeHeaders.put("Content-Type", "application/json");
                    }

                    String newExeBody =getTransSql(exeBody);
                    String newExeParams = getTransSql(exeParams);
                    sendRequest(exeUrlRequestMethod.getName(), exeUrl,newExeParams, newExeBody, exeHeaders,
                            exeAuthType, exeBearertoken);
                }

            }

        }
        catch (Exception e){
            throw new Exception("doEvent2出错："+e.getMessage());
        }


    }

    public AuthenticationTypeEnum AuthenticationTypeEnumFromId(int id) {
        for (AuthenticationTypeEnum method : AuthenticationTypeEnum.values()) {
            if (method.getId() == id) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown ID: " + id);
    }
    public RequestMethodEnum executionMethodEnumFromId(int id) {
        for (RequestMethodEnum method : RequestMethodEnum.values()) {
            if (method.getId() == id) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown ID: " + id);
    }


    /**
     * 判断会员ID是否存在
     * @param memberId
     * @return
     */
    private boolean getMemberIsExist(Long memberId) {
        //判断人员是否存在会员表
//        String sql = MessageFormat.format("SELECT COUNT(*) from ark_uims_org_employee auoe WHERE auoe.member_id={0} and auoe.is_deleted=0", memberId.toString());
//        String value = getSqlValue(sql);
//        if (value.equals("1"))
//        {
//            return true;
//        } else
//        {
//            return  false;
//        }
        return true;
    }

    /**
     * 启动工作流
     *
     * @param wfStartDTO
     * @param currentUser
     */

    @Override
    public WfRunWfEntity startWF(WfStartDTO wfStartDTO, HttpRequestDTO data, MemberIdentity currentUser) {
        try
        {
            //错误信息提示
            String errorMsg = "";
            if(!getMemberIsExist(wfStartDTO.getApplyUserId()))
            {
                errorMsg = "申请人["+wfStartDTO.getApplyUserId()+"]不存在!";
                throw new ValidationException(errorMsg);
            }

            //获取开始节点
            WfDefNodeEntity startNode = getStartNode(wfStartDTO);

            if (startNode == null) {
                errorMsg = "未找到开始节点";
                throw new ValidationException(errorMsg);
            }

            Long wfId = startNode.getWfId();
            Long nodeId = startNode.getId();
            String wfTitle = "#{apply_user_name}的#{wf_type_name}";
            String wFNote = "#{apply_user_name}的#{wf_type_name}";
            String wfCode = "";
            Long outId = wfStartDTO.getOutId();
            Long applyUserId = wfStartDTO.getApplyUserId();
            Integer result=0;//表示发起
            //region 初始化变量表并赋值
            List<WfVarDTO> tmpWfVarList = initVars(wfStartDTO.getPlatform(),result,wfId, nodeId, 0L, 0L,0L, wfStartDTO.getOutId(), wfStartDTO.getApplyUserId(), true, currentUser);
            wfVarDTOList =AppendOutVars(wfStartDTO.getOutVars(),tmpWfVarList);
            wfVarDTOList=AppendIdeVars(wfStartDTO.getIdeVars(),wfVarDTOList);
            //直接获取下一节点和节点办理人
            List<WfNextNodeDTO> nextNodeDTOList = findRelationNextNodesAndUsers(wfId, nodeId, applyUserId);
            if (nextNodeDTOList == null || nextNodeDTOList.size() == 0) {
                errorMsg = "未找到下一办理节点";
                throw new ValidationException(errorMsg);
            }

            Integer wfType = 1;//获取工作流表定义的类型 需要去主表查询
            String sqlDefine = MessageFormat.format("SELECT t.name,t.wf_type,t.wf_title_config,t.wf_title_exp,t.wf_note_config,t.wf_note_exp,f_sys_code_name(''wf_def_define_wf_type'',wf_type) wf_type_name FROM wf_def_define t WHERE id={0}", wfId.toString());
            List<WfDefDefineDTO> wfDefDefineDTOS = jdbcTemplate.query(sqlDefine, BeanPropertyRowMapper.newInstance(WfDefDefineDTO.class));
            WfDefDefineDTO wfDef = new WfDefDefineDTO();
            if (wfDefDefineDTOS == null || wfDefDefineDTOS.size() < 1) {
                errorMsg = "找不到工作流设置";
                throw new ValidationException(errorMsg);
            }
            wfDef = wfDefDefineDTOS.get(0);
            wfTitle = wfDef.getWfTitleExp()==null?wfTitle:wfDef.getWfTitleExp();
            wFNote = wfDef.getWfNoteExp()==null?wFNote:wfDef.getWfNoteExp();
            Integer wfTitleConfig = wfDef.getWfTitleConfig();
            Integer wfNoteConfig = wfDef.getWfNoteConfig();
            wfType = wfDef.getWfType();

            wfTitle = wfDef.getWfTitleExp();
            if (wfTitleConfig == 1) {
                wfTitle = getTransExp(wfTitle);
            } else if (wfTitleConfig == 2) {
                wfTitle = getSqlTransSqlResult(wfTitle);
            } else {
                wfTitle = getTransExp(wfTitle);
            }
            if (wfNoteConfig == 1) {
                wFNote = getTransExp(wFNote);
            } else if (wfNoteConfig == 2) {
                wFNote = getSqlTransSqlResult(wFNote);
            } else {
                wFNote = getTransExp(wFNote);
            }
            //endregion

            Boolean isFind = false;
            //判断下一节点和下一结点人是不是在列表中。
            Long pNextNodeId = wfStartDTO.getNextNodeId();
            Long pNextDealUserId = wfStartDTO.getNextUserId();
            Integer nextNodeType = 0;

            //region 手工选择节点处理方式
            if (pNextNodeId != 0L || pNextDealUserId != 0L) {
                for (int i = 0; i < nextNodeDTOList.size(); i++) {
                    WfNextNodeDTO relation = nextNodeDTOList.get(i);
                    Long nextNodeId = relation.getNodeId();
                    pNextNodeId = nextNodeId;
                    if (!nextNodeId.equals(pNextNodeId)) continue;
                    nextNodeType = relation.getNodeType();
                    if (relation.getUsers() == null || relation.getUsers().size() == 0) continue;
                    for (int user = 0; user < relation.getUsers().size(); user++) {
                        WfNodeUserDTO userDTO = relation.getUsers().get(user);
                        if (userDTO.getUserId().equals(pNextDealUserId)) {
                            pNextDealUserId = userDTO.getUserId();
                            isFind = true;
                            break;
                        }
                    }
                }
                if (!isFind) {
                    errorMsg = "您选择的节点或者节点办理人匹配失败请重试";
                    throw new ValidationException(errorMsg);
                }
                WfRunWfEntity wfRunWfEntity = writeStartNode(data, wfStartDTO.getPlatform(), currentUser, nodeId, applyUserId, wfId, wfTitle, wFNote, wfType, nextNodeType, pNextNodeId, pNextDealUserId, outId,wfStartDTO.getOutVars(),wfStartDTO.getIdeVars());
                return wfRunWfEntity;
            }
            //endregion
            //region 自动节点选择
            else {
                for (int i = 0; i < nextNodeDTOList.size(); i++) {
                    WfNextNodeDTO relation = nextNodeDTOList.get(i);
                    Long nextNodeId = relation.getNodeId();
                    pNextNodeId = nextNodeId;
                    nextNodeType = relation.getNodeType();
                    for (int user = 0; user < relation.getUsers().size(); user++) {
                        WfNodeUserDTO userDTO = relation.getUsers().get(user);
                        pNextDealUserId = userDTO.getUserId();
                        isFind = true;
                    }
                }
                if (!isFind) {
                    throw new ValidationException("未找到下一节点办理人");
                }
                WfRunWfEntity wfRunWfEntity = writeStartNode(data, wfStartDTO.getPlatform(), currentUser, nodeId, applyUserId, wfId, wfTitle, wFNote, wfType, nextNodeType, pNextNodeId, pNextDealUserId, outId,wfStartDTO.getOutVars(),wfStartDTO.getIdeVars());
                return wfRunWfEntity;
            }
            //endregion

        }catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("工作流启动出错："+e.getMessage());
        }
    }


    public WfRunWfEntity reStartWF(WfStartDTO wfStartDTO,HttpRequestDTO requestDTO, MemberIdentity currentUser) throws Exception {
        String errorMsg = "";
        if (!getMemberIsExist(wfStartDTO.getApplyUserId()))
        {
            errorMsg = "申请人["+wfStartDTO.getApplyUserId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }
        //重新发起工作流，更新当前节点为发起。
        //更新主表信息
        //获取开始节点
        WfDefNodeEntity startNode = getStartNode(wfStartDTO);
        //错误信息提示
        if (startNode == null) {
            errorMsg = "未找到开始节点";
            throw new ValidationException(errorMsg);
        }

        Long wfId = startNode.getWfId();
        Long nodeId = startNode.getId();
        Long outId = wfStartDTO.getOutId();
        Long applyUserId = wfStartDTO.getApplyUserId();
        Long wfRunId = wfStartDTO.getWfRunId();
        Integer result = 0;//表示发起
        WfRunNodeEntity curRunNode = getCurRunNodeByRunWfId(wfRunId);
        if (curRunNode == null) throw new ValidationException("当前节点不存在");

        //region 初始化变量表并赋值
        List<WfVarDTO> tmpWfVarList = initVars(wfStartDTO.getPlatform(),result,wfId, nodeId, wfRunId, curRunNode.getId(),0L, wfStartDTO.getOutId(),
                wfStartDTO.getApplyUserId(), true, currentUser);
        wfVarDTOList = AppendOutVars(wfStartDTO.getOutVars(),tmpWfVarList);
        wfVarDTOList = AppendIdeVars(wfStartDTO.getIdeVars(),wfVarDTOList);

        //直接获取下一节点和节点办理人
        List<WfNextNodeDTO> nextNodeDTOList = findRelationNextNodesAndUsers(wfId, nodeId, applyUserId);
        if (nextNodeDTOList == null || nextNodeDTOList.size() == 0) {
            errorMsg = "未找到下一办理节点";
            throw new ValidationException(errorMsg);
        }

        Boolean isFind = false;
        //判断下一节点和下一结点人是不是在列表中。
        Long pNextNodeId = wfStartDTO.getNextNodeId();
        Long pNextDealUserId = wfStartDTO.getNextUserId();
        Integer nextNodeType = 0;

        //region 手工选择节点处理方式
        if (pNextNodeId != 0L || pNextDealUserId != 0L) {
            for (int i = 0; i < nextNodeDTOList.size(); i++) {
                WfNextNodeDTO relation = nextNodeDTOList.get(i);
                Long nextNodeId = relation.getNodeId();
                pNextNodeId = nextNodeId;
                if (!nextNodeId.equals(pNextNodeId)) continue;
                nextNodeType = relation.getNodeType();
                if (relation.getUsers() == null || relation.getUsers().size() == 0) continue;
                for (int user = 0; user < relation.getUsers().size(); user++) {
                    WfNodeUserDTO userDTO = relation.getUsers().get(user);
                    if (userDTO.getUserId().equals(pNextDealUserId)) {
                        pNextDealUserId = userDTO.getUserId();
                        isFind = true;
                        break;
                    }
                }
            }
            if (!isFind) {
                errorMsg = "您选择的节点或者节点办理人匹配失败请重试";
                throw new ValidationException(errorMsg);
            }
            if (pNextDealUserId == -1L)
                throw new ValidationException("未找到下一节点办理人");
        }
        //endregion
        //region 自动节点选择
        else {
            for (int i = 0; i < nextNodeDTOList.size(); i++) {
                WfNextNodeDTO relation = nextNodeDTOList.get(i);
                Long nextNodeId = relation.getNodeId();
                pNextNodeId = nextNodeId;
                nextNodeType = relation.getNodeType();
                for (int user = 0; user < relation.getUsers().size(); user++) {
                    WfNodeUserDTO userDTO = relation.getUsers().get(user);
                    pNextDealUserId = userDTO.getUserId();
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                throw new ValidationException("未找到下一节点办理人");
            }
            if (pNextDealUserId == -1L)
                throw new ValidationException("未找到下一节点办理人");

        }
        //endregion
        return writeReStartNode(requestDTO, wfStartDTO.getPlatform(), currentUser, wfId, curRunNode.getRunWfId(), curRunNode.getId(), nodeId, applyUserId, pNextNodeId, pNextDealUserId, curRunNode.getSort() + 1, outId,wfStartDTO.getOutVars());
    }


    /**
     * 写开始节点信息
     *
     * @param currentUser
     * @param startNodeID
     * @param applyUserId
     * @param wfId
     * @param wfTitle
     * @param wFNote
     * @param wfType
     * @param nextNodeType
     * @param nextNodeId
     * @param nextUserId
     * @param outId
     */
    private WfRunWfEntity writeStartNode(HttpRequestDTO data,Integer platform, MemberIdentity currentUser, Long startNodeID, Long applyUserId, Long wfId, String wfTitle, String wFNote, Integer wfType,
                                Integer nextNodeType, Long nextNodeId, Long nextUserId, Long outId, List<WfGlobalVarDTO> outVars, List<IdeCtrlValueDTO> ideVars) throws Exception {
        String errorMsg = "";
        if(!getMemberIsExist(nextUserId))
        {
            errorMsg = "下一节点办理人["+nextUserId+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        //通过ID找部门
        String deptSql = MessageFormat.format("SELECT department_id FROM ark_uims_org_employee WHERE is_deleted=0 and member_id={0}", applyUserId.toString());
        String deptValue = getSqlValue(deptSql);
        if (deptValue == null)
        {
            //throw new ValidationException("未找到申请人部门");
            deptValue="0";
        }
        Long applyDeptId = Long.parseLong(deptValue);

        String wfCode;
        //写入工作流执行表，节点执行表
        LocalDateTime curDateTime = LocalDateTime.now();
        Long cur_node_user_id = currentUser.getId();
        String companyId = currentUser.getOrganizationId().toString();


        //获取工作流编号
        String sqlWfCode = MessageFormat.format("SELECT f_sys_getno2(''wf_order'',{0},{1})", companyId, cur_node_user_id.toString());
        wfCode = getSqlValue(sqlWfCode);
        //wfCode异常
        if (wfCode == null) //3表示结束节点
        {
            errorMsg = "工作流编号不能为空";
            throw new ValidationException(errorMsg);
        }

        WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
        wfRunWfFormDTO.setCompanyId(currentUser.getOrganizationId());
        wfRunWfFormDTO.setWfType(wfType);
        wfRunWfFormDTO.setWfCode(wfCode);
        wfRunWfFormDTO.setWfId(wfId);
        wfRunWfFormDTO.setWfTitle(wfTitle);
        wfRunWfFormDTO.setWfNote(wFNote);
        wfRunWfFormDTO.setBeginTime(curDateTime);
        wfRunWfFormDTO.setStatus(WfStatus.Start.getId());
        wfRunWfFormDTO.setWfStatus(WfStatus.Start.getId());
        wfRunWfFormDTO.setBeginNodeId(startNodeID);
        wfRunWfFormDTO.setBeginUserId(applyUserId);
        wfRunWfFormDTO.setPreNodeId(startNodeID);
        wfRunWfFormDTO.setPreNodeUserId(currentUser.getId());
        wfRunWfFormDTO.setBeginDeptId(applyDeptId);
        wfRunWfFormDTO.setCurNodeId(nextNodeId);
        wfRunWfFormDTO.setCurNodeUserId(nextUserId);
        wfRunWfFormDTO.setCurArriveTime(curDateTime);
        wfRunWfFormDTO.setCurApproveResult(WfApproveResult.Start.getId());
        wfRunWfFormDTO.setApproveResult(WfApproveResult.Start.getId());//发起
        WfRunWfEntity wfRun = wfRunWfService.createWfRunWf(wfRunWfFormDTO, currentUser);
        //获取主表id，写入子表 wf_run_node 先写发起人的，再写办理人的。
        Long runWfId = wfRun.getId();
        int runSort = 1;

        //写发起人的节点执行
        WfRunNodeFormDTO wrRunStartNode = new WfRunNodeFormDTO();
        wrRunStartNode.setCompanyId(currentUser.getOrganizationId());
        wrRunStartNode.setRunWfId(runWfId);
        wrRunStartNode.setWfId(wfId);
        wrRunStartNode.setSort(runSort);
        wrRunStartNode.setCurStatus(WfNodeStatus.Start.getId());//当前发起
        wrRunStartNode.setCurNodeId(startNodeID);
        wrRunStartNode.setCurNodeUserId(applyUserId);//申请人
        wrRunStartNode.setCurArriveTime(curDateTime);
        wrRunStartNode.setCurSendTime(curDateTime);
        wrRunStartNode.setNextNodeId(nextNodeId);
        wrRunStartNode.setNextNodeUserId(nextUserId);
        wrRunStartNode.setNextNodeStatus(WfNodeStatus.Dealing.getId());//下一个办理
        WfRunNodeEntity curNode = wfRunNodeService.createWfRunNode(wrRunStartNode, currentUser);

        // 更新工作流的执行开始节点
        wfRun.setBeginRunNodeId(curNode.getId());
        wfRunWfService.updateById(wfRun);

        runSort++;//顺序加1
        //写下一节的节点执行
        WfRunNodeEntity runNode = null;
        if (nextNodeType == 2 || nextNodeType == 3) {
            WfRunNodeFormDTO wrRunNextDealNode = new WfRunNodeFormDTO();
            wrRunNextDealNode.setCompanyId(currentUser.getOrganizationId());
            wrRunNextDealNode.setRunWfId(runWfId);
            wrRunNextDealNode.setWfId(wfId);
            wrRunNextDealNode.setSort(runSort);
            wrRunNextDealNode.setIsCurMark(1);
            wrRunNextDealNode.setCurStatus(WfStatus.Deal.getId());
            wrRunNextDealNode.setCurNodeId(nextNodeId);
            wrRunNextDealNode.setCurNodeUserId(nextUserId);
            wrRunNextDealNode.setPreRunNodeId(curNode.getId());
            wrRunNextDealNode.setPreNodeId(startNodeID);
            wrRunNextDealNode.setPreNodeUserId(applyUserId);
            wrRunNextDealNode.setPreNodeStatus(WfNodeStatus.Finish.getId());
            curDateTime = curDateTime.plusSeconds(1);
            wrRunNextDealNode.setCurArriveTime(curDateTime);
            runNode = wfRunNodeService.createWfRunNode(wrRunNextDealNode, currentUser);

            // 更新上一节点的next_run_node_id
            curNode.setNextRunNodeId(runNode.getId());
            wfRunNodeService.updateById(curNode);
        } else {
            errorMsg = "下一节点类型错误";
            throw new ValidationException(errorMsg);
        }

        //更新主表当前执行ID，和下一个ID
        WfRunWfFormDTO newWFRun = new WfRunWfFormDTO();
        newWFRun.setPreRunNodeId(curNode.getId());
        newWFRun.setCurRunNodeId(runNode.getId());
        wfRunWfService.updateWfRunWf(runWfId, newWFRun, currentUser);

        //把out_id写入工作流变量表
        WfRunVarFormDTO wfRunVarDTO = new WfRunVarFormDTO();
        wfRunVarDTO.setCompanyId(currentUser.getOrganizationId());
        wfRunVarDTO.setWfId(wfId);
        wfRunVarDTO.setRunWfId(runWfId);
        wfRunVarDTO.setVarId("out_id");
        wfRunVarDTO.setVarValue(outId.toString());
        wfRunVarService.createWfRunVar(wfRunVarDTO, currentUser);

        Integer result = 0;//表示发起
        //这里写执行事件
        List<WfVarDTO> tmpWfVarList = initVars(platform,result, wfId, startNodeID,
                runWfId, curNode.getId(), runNode.getId(), outId, applyUserId, true, currentUser);
        wfVarDTOList = AppendOutVars(outVars, tmpWfVarList);
        wfVarDTOList = AppendIdeVars(ideVars, wfVarDTOList);
        doEvent2(true, wfId, startNodeID,data);
        sendMessageApplyBegin(wfId, runWfId, startNodeID, WfStatus.Start, WfApproveResult.Start, "", applyUserId, currentUser.getId(), runWfId, curNode.getId());
        sendMessageDealUser(wfId, runWfId, startNodeID, nextUserId, WfStatus.Start, WfApproveResult.Start,"", runWfId, curNode.getId(), currentUser);
        sendMessageCcUser(wfId, runWfId, startNodeID, WfStatus.Start, WfApproveResult.Start, "", runWfId, curNode.getId());
        return wfRun;
    }


    private List<WfVarDTO> AppendOutVars(List<WfGlobalVarDTO> outVars, List<WfVarDTO> wfVars)
    {
        if (outVars==null)
        {
            return wfVars;
        }

        // 通过循环把outVars变量转换并赋值给wfVars
        for (int i = 0; i < outVars.size(); i++) {
            WfGlobalVarDTO globalVar = outVars.get(i);
            // 假设存在一个转换方法 convertToWfVarDTO，它将 WfGlobalVarDTO 转换为 WfVarDTO
            WfVarDTO varDTO = convertToWfVarDTO(globalVar);
            wfVars.add(varDTO);
        }

        return wfVars;
    }

    private List<WfVarDTO> AppendIdeVars(List<IdeCtrlValueDTO> ideVars, List<WfVarDTO> wfVars)
    {
        if (ideVars == null) {
            return wfVars;
        }

        List<WfGlobalVarDTO> newVarList = IdeVarDeal(ideVars);
        List<WfVarDTO> mergedList = new ArrayList<>(wfVars.size() + newVarList.size());

        // 添加wfVars
        mergedList.addAll(wfVars);

        // 将WfGlobalVarDTO转换为WfVarDTO并添加到mergedList
        for (WfGlobalVarDTO globalVar : newVarList) {
            WfVarDTO varDto = new WfVarDTO();
            varDto.setVarId(globalVar.getVarId());
            varDto.setVarName(globalVar.getVarId());
            varDto.setVarCode(globalVar.getVarId());
            varDto.setVarValue(globalVar.getVarValue());
            mergedList.add(varDto);
        }

        return mergedList;
    }

    // 假设的转换方法，根据实际情况实现
    private WfVarDTO convertToWfVarDTO(WfGlobalVarDTO globalVar) {
        // 实现转换逻辑，这里仅为示例
        WfVarDTO wfVar = new WfVarDTO();
        wfVar.setVarId(globalVar.getVarId());
        wfVar.setVarValue(globalVar.getVarValue());
        wfVar.setVarCode("@"+globalVar.getVarId());//外部变量统一增加@做为标识。
        return wfVar;
    }


    /**
     * 处理IDE变量
     * @param postCtrlValues
     * @return
     */
    @Override
    public List<WfGlobalVarDTO> IdeVarDeal(List<IdeCtrlValueDTO> postCtrlValues)
    {
        List<WfGlobalVarDTO> wfGlobalVarDTOS = new ArrayList<>();

        // 处理formVars
        for (IdeCtrlValueDTO ctrlValue : postCtrlValues) {
            if (ctrlValue.getFormVars() != null) {
                for (ExeFunFormVarDTO formVar : ctrlValue.getFormVars()) {
                    if (formVar.getVarId() != null) {
                        WfGlobalVarDTO varDTO = new WfGlobalVarDTO();
                        varDTO.setVarId("$#"+ctrlValue.getFunId()+"."+ctrlValue.getFormId()+".{" + formVar.getVarId() + "}");
                        varDTO.setVarValue(formVar.getVarValue());
                        wfGlobalVarDTOS.add(varDTO);
                    }
                }
            }

            // 处理rowVars
            if (ctrlValue.getRowVars() != null) {
                for (ExeFunFormRowObjectDTO table : ctrlValue.getRowVars()) {
                    List<ExeFunFormRowVarDTO> rowVars = table.getRow();
                    for (ExeFunFormRowVarDTO row : rowVars) {
                        WfGlobalVarDTO varDTO = new WfGlobalVarDTO();
                        varDTO.setVarId("$#"+ctrlValue.getFunId()+"."+ctrlValue.getFormId()+".{"
                                + table.getTableName()
                                + "}["
                                + row.getCol()
                                + "]");
                        varDTO.setVarValue(row.getValue());
                        wfGlobalVarDTOS.add(varDTO);
                    }
                }
            }

            if (ctrlValue.getCollectionObjects()!=null)
            {
                //循环变量，ctrlValue.getCollectionObjects()
                for (IdeCollectionObjectDTO collectionObject : ctrlValue.getCollectionObjects()) {
                    for (int i = 0; i < collectionObject.getVarValue().size(); i++) {
                        List<ExeFunFormVarDTO> varValueList = collectionObject.getVarValue().get(i);
                        for (int j = 0; j < varValueList.size(); j++) {
                            ExeFunFormVarDTO var = varValueList.get(j);
                            WfGlobalVarDTO varDTO = new WfGlobalVarDTO();
                            varDTO.setVarId("$#" + ctrlValue.getFunId() + "." + ctrlValue.getFormId() + ".{"
                                    + collectionObject.getVarId() + "}["
                                    + i + "]." // 使用i作为第一层级的下标
                                    + var.getVarId());
                            // 这里可以继续处理varDTO，比如添加到某个集合中等
                            varDTO.setVarValue(var.getVarValue());
                            wfGlobalVarDTOS.add(varDTO);
                        }
                    }
                }

            }
        }

        return wfGlobalVarDTOS;
    }


    private WfRunNodeEntity getCurRunNodeById(Long id) {
        LambdaQueryWrapper<WfRunNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
        wrapper.eq(WfRunNodeEntity::getId, id);
        WfRunNodeEntity curRunNode = wfRunNodeService.getOne(wrapper, false);
        return curRunNode;
    }

    private WfRunNodeEntity getCurRunNodeByRunWfId(Long runWfId) {
        Long id = 0L;
        String sql = MessageFormat.format("SELECT * from wf_run_node wrn WHERE wrn.run_wf_id={0} AND wrn.is_cur_mark=1", runWfId.toString());
        List<WfRunNodeEntity> tables = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(WfRunNodeEntity.class));
        if (tables == null || tables.size() < 1) return null;
        id = tables.get(0).getId();
        WfRunNodeEntity curRunNode = getCurRunNodeById(id);
        return curRunNode;
    }

    /**
     * @param currentUser 当前用户
     * @param wfId        工作流ID
     * @param runId       执行ID
     * @param startNodeID 开始节点
     * @param applyUserId 开始人
     * @param nextNodeId  下一个节点
     * @param nextUserId  下一个人
     * @param nextSort    下一个顺序
     */
    private WfRunWfEntity writeReStartNode(HttpRequestDTO data,Integer platform, MemberIdentity currentUser, Long wfId, Long runWfId, Long runId, Long startNodeID, Long applyUserId,
                                  Long nextNodeId, Long nextUserId, int nextSort, Long outId,List<WfGlobalVarDTO> outVars) throws Exception {
        String errorMsg = "";
        if (!getMemberIsExist(applyUserId))
        {
            errorMsg = "申请人["+applyUserId+"]不存在!";
            throw new ValidationException(errorMsg);
        }
        if (!getMemberIsExist(currentUser.getId()))
        {
            errorMsg = "当前用户["+currentUser.getId()+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        if (!getMemberIsExist(nextUserId))
        {
            errorMsg = "下一节点办理人["+nextUserId+"]不存在!";
            throw new ValidationException(errorMsg);
        }

        //写入工作流执行表，节点执行表
        LocalDateTime curDateTime = LocalDateTime.now();
        Long cur_node_user_id = currentUser.getId();
        Long messageUserId = 0L;

        WfRunWfFormDTO wfRunWfFormDTO = new WfRunWfFormDTO();
        wfRunWfFormDTO.setStatus(WfStatus.Start.getId());
        wfRunWfFormDTO.setWfStatus(WfStatus.Start.getId());
        wfRunWfFormDTO.setPreNodeId(startNodeID);
        wfRunWfFormDTO.setPreNodeUserId(currentUser.getId());
        wfRunWfFormDTO.setCurNodeId(nextNodeId);
        wfRunWfFormDTO.setCurNodeUserId(nextUserId);
        wfRunWfFormDTO.setCurArriveTime(curDateTime);
        wfRunWfFormDTO.setCurApproveResult(WfApproveResult.Start.getId());
        wfRunWfFormDTO.setApproveResult(WfApproveResult.Start.getId());
        wfRunWfFormDTO.setPreRunNodeId(runId);
        WfRunWfEntity wfRun = wfRunWfService.updateWfRunWf(runWfId, wfRunWfFormDTO, currentUser);


        //更新当前节点的状态数据
        WfRunNodeFormDTO curNode = new WfRunNodeFormDTO();
        if (curNode.getCurReadTime() == null)
            curNode.setCurReadTime(curDateTime);
        curNode.setCurStatus(WfNodeStatus.Finish.getId());
        curDateTime = curDateTime.plusSeconds(1);
        curNode.setCurDealTime(curDateTime);
        curNode.setIsCurMark(0);
        wfRunNodeService.updateWfRunNode(runId, curNode, currentUser);

        //获取主表id，写入子表 wf_run_node 先写发起人的，再写办理人的。
        WfRunNodeFormDTO wrRunNextDealNode = new WfRunNodeFormDTO();
        wrRunNextDealNode.setCompanyId(currentUser.getOrganizationId());
        wrRunNextDealNode.setRunWfId(runWfId);
        wrRunNextDealNode.setWfId(wfId);
        wrRunNextDealNode.setSort(nextSort);
        wrRunNextDealNode.setIsCurMark(1);
        wrRunNextDealNode.setCurStatus(WfStatus.Deal.getId());
        wrRunNextDealNode.setCurNodeId(nextNodeId);
        wrRunNextDealNode.setCurNodeUserId(nextUserId);
        wrRunNextDealNode.setCurArriveTime(curDateTime);
        WfRunNodeEntity wfRunNode=  wfRunNodeService.createWfRunNode(wrRunNextDealNode, currentUser);
        Long nextRunNodeId=wfRunNode.getId();

        WfRunWfFormDTO newWfRunWfFormDTO = new WfRunWfFormDTO();
        newWfRunWfFormDTO.setCurRunNodeId(nextRunNodeId);
        WfRunWfEntity newWfRun = wfRunWfService.updateWfRunWf(runWfId, newWfRunWfFormDTO, currentUser);
        Integer result=0;//表示发起
        //这里写执行事件
        List<WfVarDTO> tmpWfVarList = initVars(platform,result,wfId, startNodeID, runWfId, runId,nextRunNodeId, outId, applyUserId, true, currentUser);
        wfVarDTOList =AppendOutVars(outVars, tmpWfVarList);
        doEvent2(true, wfId, startNodeID,data);
        sendMessageApplyBegin(wfId, runWfId,startNodeID,WfStatus.Start,WfApproveResult.Start, "",applyUserId,currentUser.getId(),runWfId,runId);
        sendMessageDealUser(wfId, runWfId,startNodeID,nextUserId,WfStatus.Start,WfApproveResult.Start,"",runWfId,runId, currentUser);
        sendMessageCcUser(wfId, runWfId,startNodeID,WfStatus.Start,WfApproveResult.Start, "",runWfId,runId);

        return wfRunWfService.getWfRunWf(runWfId, currentUser);
    }

    @Override
    public void  testApplyUserBegin(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments,
                                    Long applyUserId,Long createUserId)
    {
        WfStatus wfStatus=WfStatus.getInstance(wf_status);
        WfApproveResult wfApproveResult=WfApproveResult.getInstance(approve_result);
        sendMessageApplyBegin(wfId, wf_run_id,node_id,wfStatus,wfApproveResult, approve_comments,applyUserId,createUserId,wf_run_id,wf_run_id);
    }

    @Override
    public void  testApplyUserProcess(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments,
                                      Long applyUserId,Long createUserId)
    {
        WfStatus wfStatus=WfStatus.getInstance(wf_status);
        WfApproveResult wfApproveResult=WfApproveResult.getInstance(approve_result);
        sendMessageApplyProcess(wfId, wf_run_id,node_id,wfStatus,wfApproveResult, approve_comments,applyUserId,createUserId,wf_run_id,wf_run_id);
    }

    @Override
    public void  testApplyUserEnd(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments,
                                  Long applyUserId,Long createUserId)
    {
        WfStatus wfStatus=WfStatus.getInstance(wf_status);
        WfApproveResult wfApproveResult=WfApproveResult.getInstance(approve_result);
        sendMessageApplyEnd(wfId, wf_run_id,node_id,wfStatus,wfApproveResult, approve_comments,applyUserId,createUserId,wf_run_id,wf_run_id);
    }


    @Override
    public void testSendMessageDealUser(Long wfId,Long wf_run_id,Long node_id,Long dealUserId,int wf_status,int approve_result,String approve_comments, MemberIdentity currentUser)
    {
        WfStatus wfStatus = WfStatus.getInstance(wf_status);
        WfApproveResult wfApproveResult = WfApproveResult.getInstance(approve_result);
        sendMessageDealUser(wfId, wf_run_id, node_id, dealUserId, wfStatus, wfApproveResult, approve_comments, wf_run_id, wf_run_id, currentUser);
    }

    @Override
    public void testSendMessageCcUser(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments)
    {
        WfStatus wfStatus=WfStatus.getInstance(wf_status);
        WfApproveResult wfApproveResult=WfApproveResult.getInstance(approve_result);
        sendMessageCcUser(wfId, wf_run_id,node_id,wfStatus,wfApproveResult, approve_comments,wf_run_id,wf_run_id);
    }

    /**
     * 获取表达式的整数值
     *
     * @param srcSql
     * @return
     */
    private Integer getSqlTransIntResult(String srcSql) {
        String newSql = getTransExp(srcSql);
        String value = getSqlValue(newSql);
        return Integer.parseInt(value);
    }


    /**
     * 获取首行首列的值
     *
     * @param sql
     * @return
     */
    private String getSqlValue(String sql) {
        try {
//            List<String> userDTOS = jdbcTemplate.queryForList(sql, String.class);
//            if (userDTOS == null || userDTOS.size() < 1) {
//                return null;
//            } else {
//                return userDTOS.get(0);
//            }
            return  queryForValue(jdbcTemplate,sql);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }


    }

    /**
     * 获取表达式的字符串结果值
     *
     * @param srcSql
     * @return
     */
    private String getSqlTransSqlResult(String srcSql) {
        String newSql = getTransExp(srcSql);
        String value = getSqlValue(newSql);
        return value;
    }

    /**
     * 获取转换后的表达式的值
     *
     * @param srcExp
     * @return
     */
    private String getSqlTransExpResult(String srcExp) {
        String newExp = getTransExp(srcExp);
        return newExp;
    }

    private List<String> getSqlColumn(String sql) {

        String newSql = MessageFormat.format("SELECT * FROM ({0}) AS w LIMIT 0", sql);
        List<String> columns = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(newSql);
        SqlRowSetMetaData rowSetMetaData = rowSet.getMetaData();
        int columnCount = rowSetMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(rowSetMetaData.getColumnLabel(i));
        }
        return columns;
    }


    public void testExcel(HttpServletResponse response) {

        List<WfRunQueryTableDTO> runQueryTableDTOList = new ArrayList<>();
        String sqlTable = "SELECT * FROM wf_def_node_table wdnt";
        List<WfRunTableEntity> tables = jdbcTemplate.query(sqlTable, BeanPropertyRowMapper.newInstance(WfRunTableEntity.class));
        if (tables == null || tables.size() < 1) return;

        // 表头数据
        List<Object> head = Arrays.asList("姓名", "年龄", "性别", "头像", ExcelUtils.COLUMN_MERGE, ExcelUtils.COLUMN_MERGE, "地址");
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("诸葛亮");
        user1.add(60);
        user1.add("男");
        user1.add("https://profile.csdnimg.cn/A/7/3/3_sunnyzyq");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(28);
        user2.add("女");
        user2.add("https://profile.csdnimg.cn/6/1/9/0_m0_48717371");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);

        Map<Integer, List<String>> selectMap = new HashMap<>(0);
        selectMap.put(2, Arrays.asList("男", "女"));
        selectMap.put(3, Arrays.asList("北京", "上海", "成都", "重庆"));

        Map<String, List<List<Object>>> sheets = new LinkedHashMap<>();
        sheets.put("文化课", sheetDataList);
        sheets.put("艺术课", sheetDataList);

        // 导出数据
        ExcelUtils.export(response, "ceshi", sheetDataList, selectMap);

        // com.qy.app.app.utils.excel.ExcelUtils.export(response, "学生成绩表", sheets);

    }

    @Override
    public List<WfRunQueryTableDTO> getWFTablesByFunName(WfGetTableByFunNameDTO wfGetTableDTO,MemberIdentity currentUser)
    {
        WfGetTableDTO wf=new WfGetTableDTO();
        wf.setIsStartNode(1);
        wf.setPlatform(wfGetTableDTO.getPlatform());
        wf.setShowType(wfGetTableDTO.getShowType());
        wf.setWfRunId(0L);
        wf.setWfRunNodeId(0L);
        wf.setDealResult(0);
        //通过funname后去wfid
        String sql=MessageFormat.format("SELECT wdm.wf_id FROM wf_def_map wdm WHERE wdm.fun_name=''{0}'' AND wdm.is_deleted=0 AND wdm.status=1",wfGetTableDTO.getFunName());
        String wfId=queryForValue(jdbcTemplate,sql);
        if (wfId==null)
        {
            throw new ValidationException("funname对应的wfid不存在");
        }
        wf.setWfId(Long.parseLong(wfId));
        return getWFTables(wf, currentUser);
    }


    @Override
    public List<WfFilterTable> getFilterTables(Long wfId, String platFormId,MemberIdentity currentUser)
    {
        String platForm= " 'all' ";
        if (platFormId.equals("3") || platFormId.equals("4") || platFormId.equals("5"))
        {
            platForm += ",'uniapp'";
        } else
        {
            platForm += ",'pc'";
        }

        String sql=MessageFormat.format("SELECT * FROM (\n" +
                "SELECT id,name,sort,create_time FROM wf_def_query_table t \n" +
                "WHERE t.is_deleted=0 and t.status=1 AND t.wf_id=''{0}''\n" +
                "AND t.table_type!=4 \n" +
                "UNION \n" +
                "SELECT id,name,sort,create_time FROM wf_def_query_table wdqt \n" +
                "WHERE wdqt.is_deleted=0 and wdqt.status=1 AND wdqt.wf_id=''{0}''\n" +
                "AND wdqt.table_type=4 \n" +
                "AND wdqt.ide_fun_id IN (\n" +
                "SELECT pdif.fun_id from plat_dev_ide_fun pdif WHERE pdif.plat_id in ({1}))) as w\n" +
                "ORDER BY sort ,name,create_time DESC", wfId.toString(), platForm);
        List<WfFilterTable> tables = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(WfFilterTable.class));
        return tables;
    }
    @Override
    public List<WfRunQueryTableDTO> getWFTables(WfGetTableDTO wfGetTableDTO, MemberIdentity currentUser) {

        if (wfGetTableDTO.getIsStartNode().equals(1))
        {
            return getTables(wfGetTableDTO.getWfId(), wfGetTableDTO.getIsStartNode(),
                    0L,wfGetTableDTO.getPlatform(),wfGetTableDTO.getShowType(),wfGetTableDTO.getDealResult(), currentUser);
        }else
        {
            if (wfGetTableDTO.getType() == 2) {
                //主表使用 工作流整体查询显示，而且只能用来查询。
                return getTables(wfGetTableDTO.getWfId(),0, wfGetTableDTO.getWfRunNodeId(),wfGetTableDTO.getPlatform(),wfGetTableDTO.getShowType(),
                        wfGetTableDTO.getDealResult(),
                        currentUser);
            } else {
                //计算子表的第一条记录 工作流节点使用
                LambdaQueryWrapper<WfRunNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
                wrapper.eq(WfRunNodeEntity::getRunWfId, wfGetTableDTO.getWfRunId())
                        .eq(WfRunNodeEntity::getWfId, wfGetTableDTO.getWfId())
                        .orderByDesc(WfRunNodeEntity::getSort).last(" limit 1");
                WfRunNodeEntity curRunNode = wfRunNodeService.getOne(wrapper, false);
                if (curRunNode == null) throw new ValidationException("当前办理节点不存在");
                return getTables(wfGetTableDTO.getWfId(), wfGetTableDTO.getIsStartNode(),
                        curRunNode.getId(),wfGetTableDTO.getPlatform(),wfGetTableDTO.getShowType(),wfGetTableDTO.getDealResult(),
                        currentUser);
            }
        }

    }

    public static String queryForValue(JdbcTemplate jdbcTemplate, String sql)
    {
        String value=null;
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
        if (map!=null&&map.size()>0)
        {
            Map<String, Object> obj=map.get(0);
            for (String key : obj.keySet()) {
                value= obj.get(key).toString();
                break;
            }
        }

        return value;
    }

    /**
     * 获取查询表
     *
     * @param wfRunNodeId
     * @param currentUser
     * @return
     */
    @Override
    public List<WfRunQueryTableDTO> getTables(Long wfWfId, Integer wfIsStartNode, Long wfRunNodeId,Integer platform,
                                              Integer showType,Integer dealResult, MemberIdentity currentUser) {

        try {
            //是否启动节点
            boolean isStartNode=wfIsStartNode==1;
            Long wfId = wfWfId;
            Long curNodeId = 0L;
            Long runNodeId = 0L;
            Long runWfId = 0L;
            Long nextRunNodeId=0L;
            Integer result=0;//表示发起

            if (isStartNode)
            {
                if (wfId == null) {
                    throw new ValidationException("工作流ID不能为空");
                }

                //验证wfid和启动节点id
                String sql=MessageFormat.format("SELECT id FROM wf_def_node wdn WHERE wdn.wf_id={0} AND wdn.node_type=1 AND wdn.status=1 AND wdn.is_deleted=0",wfId.toString());
                //获取sql查询的结果
                String vStardNodeId= jdbcTemplate.queryForObject(sql,String.class);
                if (vStardNodeId==null)
                {
                    throw new ValidationException("工作流ID不存在启动节点");
                }
                curNodeId=Long.parseLong(vStardNodeId);

            }else
            {
                //解析wfId,curNodeId
                LambdaQueryWrapper<WfRunNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
                wrapper.eq(WfRunNodeEntity::getId, wfRunNodeId);
                WfRunNodeEntity runNode = wfRunNodeService.getOne(wrapper, false);
                if (runNode == null) {
                    throw new ValidationException("当前工作流执行节点不存在");
                }
                wfId = runNode.getWfId();
                curNodeId = runNode.getCurNodeId();
                runNodeId = runNode.getId();
                runWfId = runNode.getRunWfId();
                nextRunNodeId = getNextRunNodeId(runWfId);
                result=runNode.getApproveResult();
            }


            //初始化变量表并赋值
            List<WfVarDTO> tmpWfVarList = initVars(platform,result,wfId, curNodeId, runWfId, runNodeId, nextRunNodeId, 0L, 0L,false, currentUser);
            wfVarDTOList = tmpWfVarList;

            List<WfRunQueryTableDTO> runQueryTableDTOList = new ArrayList<>();
            String sqlPlatform="";
            if (platform==WfPlatformEnums.ALL.getId())
            {
               // sqlPlatform=" and t.platform= 1";
            } else if (platform == WfPlatformEnums.PC.getId()) {
                sqlPlatform=" and t.platform in(1,2) ";
            }else if (platform == WfPlatformEnums.WeixinApp.getId()) {
                sqlPlatform=" and t.platform in(1,3) ";
            }else if (platform == WfPlatformEnums.App.getId()) {
                sqlPlatform=" and t.platform in(1,4) ";
            }else if (platform == WfPlatformEnums.WeixinMP.getId()) {
                sqlPlatform=" and t.platform in(1,5) ";
            }

            if (dealResult!=null&&dealResult.compareTo(0)>=0)
            {
                sqlPlatform+=MessageFormat.format(" and ( LENGTH(IFNULL(t.deal_result,''''))=0 OR INSTR(deal_result,''{0}'') )",
                        dealResult.toString());
            }

            String sqlTable = MessageFormat.format("SELECT t.platform,t.show_type,t.show_mode,t.table_id,t.deal_result,q.name,q.table_type,q.style,q.column_count,q.data_rule,q.data_sql,q.url,q.url_body,q.ide_fun_id,q.ide_form_id\n" +
                    "FROM wf_def_node_table t \n" +
                    "JOIN wf_def_query_table q ON q.wf_id=t.wf_id AND t.table_id=q.id\n" +
                    "WHERE t.wf_id={0} and t.node_id={1} {2} and t.show_type={3}  AND t.is_deleted=0 and t.status=1\n" +
                    "AND q.is_deleted=0 AND q.status=1 ORDER BY t.sort,t.create_time ", wfId.toString(), curNodeId.toString(), sqlPlatform, showType);
            List<WfRunQueryTableDTO> tables = jdbcTemplate.query(sqlTable,
                    BeanPropertyRowMapper.newInstance(WfRunQueryTableDTO.class));
            if (tables == null || tables.size() < 1) return runQueryTableDTOList;

            for (int t = 0; t < tables.size(); t++) {
                WfRunQueryTableDTO tableEntity = tables.get(t);
                Integer tableType = tableEntity.getTableType();

                if (tableType == WfTableTypeEnums.QueryTable.getId()) {
                    String srcSql = tableEntity.getDataSql();
                    if(srcSql==null||srcSql.isEmpty())
                    {
                        throw new ValidationException("查询SQL不能为空");
                    }
                    String newSql = getTransSql(srcSql);
                    List<String> columns = getSqlColumn(newSql);
                    tableEntity.setColumnNames(columns);
                    //获取表查询数据
                    List<Map<String, Object>> dataList = jdbcTemplate.queryForList(newSql);
                    if (dataList != null) {
                        tableEntity.setDataList(dataList);
                    }

                } else if (tableType == WfTableTypeEnums.WFTable.getId()) {
                    //工作流表单 暂时不支持
                    String newUrl = getTransExp(tableEntity.getUrl());
                    String newBody = getTransExp(tableEntity.getUrlBody());
                    tableEntity.setUrl(newUrl);
                    tableEntity.setUrlBody(newBody);

                } else if (tableType == WfTableTypeEnums.SystemFun.getId()) {
                    //系统开发功能功能 暂时不支持
                    String newUrl = getTransExp(tableEntity.getUrl());
                    String newBody = getTransExp(tableEntity.getUrlBody());
                    tableEntity.setUrl(newUrl);
                    tableEntity.setUrlBody(newBody);

                } else if (tableType == WfTableTypeEnums.IDEFun.getId()) {
                    //IDE功能 取IDE的ide_fun_id 参数，由前端调用ide的运行接口。并且把参数传递。
                    //处理系统变量

                } else if (tableType == WfTableTypeEnums.Link.getId()) {

                    //跳转链接
                    String newUrl = getTransExp(tableEntity.getUrl());
                    String newBody = getTransExp(tableEntity.getUrlBody());
                    tableEntity.setUrl(newUrl);
                    tableEntity.setUrlBody(newBody);
                } else if (tableType == WfTableTypeEnums.Menu.getId()){
                    //调用菜单
                    String newUrl = getTransExp(tableEntity.getUrl());
                    String newBody = getTransExp(tableEntity.getUrlBody());
                    tableEntity.setUrl(newUrl);
                    tableEntity.setUrlBody(newBody);
                }
            }

            return tables;
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private Long getNextRunNodeId(Long runWfId) {
        //获取下一个RUnNodeId的主键
        String sqlNextRunNode=MessageFormat.format("SELECT id FROM wf_run_node WHERE (run_wf_id,sort) =\n" +
                "(SELECT run_wf_id, sort+1  FROM wf_run_node WHERE  id={0})", runWfId.toString());
        String nextRunNode=queryForValue(jdbcTemplate,sqlNextRunNode);
        Long nextRunNodeId=nextRunNode==null?0L:Long.parseLong(nextRunNode);
        return nextRunNodeId;
    }

    private boolean applyRole(Long wfId, Long curNodeId, Long applyUserId) {
        return true;
    }

    private List<Map<String, Object>> getTable(String sql) {

        List<Map<String, Object>> wfMaps = jdbcTemplate.queryForList(sql);
        return wfMaps;

    }

    public class Access<T> {
        //用泛型设置成员变量
        private T info;

        //用泛型设置方法返回值
        public T getInfo() {
            return info;
        }

        //用泛型设置参数
        public void setInfo(T info) {
            this.info = info;
        }
    }


    private void executeSql(String sql) {
        jdbcTemplate.execute(sql);
    }

    public List<WfInvalidRunNodeDTO> getInvalidNode(Long run_wf_id, Long user_id, Long wf_run_node_id)
    {
        String sql=MessageFormat.format("SELECT r.id run_node_id,n.name,r.sort,DATE_FORMAT(r.cur_arrive_time, ''%Y-%m-%d %H:%i:%s'') arrive_time_name,\n" +
                "f_get_ark_code_table_name(''wf_approve_result'', r.approve_result),r.approve_comments from wf_run_node r\n" +
                "LEFT JOIN wf_def_node n ON r.cur_node_id=n.id\n" +
                "WHERE r.run_wf_id={0} AND r.cur_node_user_id={1}\n" +
                "ORDER BY  r.sort",run_wf_id.toString(),user_id.toString());
        List<WfInvalidRunNodeDTO> nodetables = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(WfInvalidRunNodeDTO.class));
        return nodetables;
    }

    @Override
    public List<WfNodeSimpleDTO>  getPreNode(Long wf_id, Long node_id)
    {
        String sql=MessageFormat.format("with recursive tb (from_node_id,  to_node_id) as(\n" +
                        "  SELECT from_node_id, to_node_id  from wf_def_node_relation \n" +
                        "\twhere wf_id={0} AND to_node_id={1}\n" +
                        "  union all\n" +
                        "  select c.from_node_id, c.to_node_id from wf_def_node_relation c \n" +
                        "\tjoin tb t ON t.from_node_id =  c.to_node_id )\n" +
                        "SELECT DISTINCT tb.from_node_id id,n.name\n" +
                        "FROM tb LEFT JOIN wf_def_node n ON n.id=tb.from_node_id WHERE n.node_type=!=3",
                wf_id.toString(),node_id.toString());
        List<WfNodeSimpleDTO> nodetables = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(WfNodeSimpleDTO.class));
        return nodetables;
    }

    @Override
    public List<WfNodeSimpleDTO>  getPreNodeBycode(Long wf_id, String node_code)
    {
        String sql=MessageFormat.format("with recursive tb (from_node_id,  to_node_id) as(\n" +
                        "  SELECT from_node_id, to_node_id  from wf_def_node_relation \n" +
                        "\twhere wf_id={0} AND to_node_id=(SELECT id FROM wf_def_node wdn " +
                        "WHERE wdn.node_code=''{1}'')\n" +
                        "  union all\n" +
                        "  select c.from_node_id, c.to_node_id from wf_def_node_relation c \n" +
                        "\tjoin tb t ON t.from_node_id =  c.to_node_id )\n" +
                        "SELECT DISTINCT tb.from_node_id id,n.name\n" +
                        "FROM tb LEFT JOIN wf_def_node n ON n.id=tb.from_node_id WHERE n.node_type!=3",
                wf_id.toString(),node_code);
        List<WfNodeSimpleDTO> nodetables = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(WfNodeSimpleDTO.class));
        return nodetables;
    }

    public WfDealNodeInfoDTO getdealinfo(Integer platform, Integer type, String funName, Long wfRunNodeId, Long applyUserId, MemberIdentity currentUser) {
        //Long wfId,Long curNodeId,
        Long wfId = 0L;
        Long nodeId = 0L;
        Long runWfId = 0L;
        Long runNodeId = 0L;
        Integer result=0;//默认表示发起
        //region 如果是启动节点，自动查找开始节点
        if (type == 1) {
            //先找funname
            String mapSql = MessageFormat.format("SELECT wf_id as value FROM wf_def_map WHERE fun_name=''{0}'' AND is_deleted=0 AND status=1 ", funName);
            String wfIdString = getSqlValue(mapSql);
            if (wfIdString == null) {
                throw new ValidationException("功能名称不存在");
            }
            wfId = Long.parseLong(wfIdString);
            nodeId = findStartNode(wfId);
            if (nodeId == 0L) throw new ValidationException("未找到开始节点");
        }
        //endregion
        //region 如果不是启动节点
        else {
            //通过runNodeId查找信息
            LambdaQueryWrapper<WfRunNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
            wrapper.eq(WfRunNodeEntity::getId, wfRunNodeId);
            WfRunNodeEntity runNodeEntity = wfRunNodeService.getOne(wrapper, false);
            if (runNodeEntity == null) throw new ValidationException("当前执行节点不存在");
            wfId = runNodeEntity.getWfId();
            nodeId = runNodeEntity.getCurNodeId();
            runWfId = runNodeEntity.getRunWfId();
            runNodeId = runNodeEntity.getId();
            result=runNodeEntity.getApproveResult();
        }
        //endregion

        //如果当前申请人为空需要验证
        if (applyUserId == null) throw new ValidationException("申请人不能为空");
        //获取申请人的权限
        boolean haveRole = applyRole(wfId, nodeId, applyUserId);
        //获取申请人的部门，主管信息
        //初始化变量表并赋值
        boolean isApplyNode = type == 1;
        if (isApplyNode) {
            List<WfVarDTO> tmpWfVarList = initVars(platform,result, wfId, nodeId, 0L, 0L,0L, 0L, applyUserId, isApplyNode, currentUser);
            wfVarDTOList = tmpWfVarList;
        }
        else {
            List<WfVarDTO> tmpWfVarList = initVars(platform,result, wfId, nodeId, runWfId, runNodeId, 0L, 0L, applyUserId, isApplyNode, currentUser);
            wfVarDTOList = tmpWfVarList;
        }



        //获取当前节点的办理权限
        WfDefNodeEntity getCurNode = getNode(wfId, nodeId);
        if (getCurNode == null) {
            throw new ValidationException("当前节点不存在");
        }

        WfDealNodeInfoDTO wfNodeInfoDTO = new WfDealNodeInfoDTO();
        Integer auto_next_user = getCurNode.getIsAutoNextUser();
        Integer can_continue = 0;
        Integer node_type = 0;
        if (getCurNode.getNodeType() == 1) {
            //如果是结束节点则不显示下一个节点
            node_type = 1;
        } else if (getCurNode.getNodeType() == 2) {
            node_type = 2;
        }

        String sqlTable = MessageFormat.format("SELECT t.table_id, t.table_type, t.table_path,q.ide_fun_id,q.ide_form_id FROM wf_def_node_table t\n" +
                "LEFT JOIN wf_def_query_table q ON q.id=t.table_id WHERE t.wf_id={0} AND t.node_id={1}", wfId.toString(), nodeId.toString());
        List<WfWfDealTableDTO> nodetables = jdbcTemplate.query(sqlTable, BeanPropertyRowMapper.newInstance(WfWfDealTableDTO.class));
        wfNodeInfoDTO.setTables(nodetables);

        //查找关联表信息
        Integer can_deal = getCurNode.getIsCanDeal();
        Integer can_agree = getCurNode.getIsCanAgree();
        Integer can_refuse = getCurNode.getIsCanRefuse();
        Integer can_return = getCurNode.getIsCanReturn();
        Integer can_withdraw = getCurNode.getIsCanWithdraw();
        Integer can_invaild = getCurNode.getIsCanInvalid();
        Integer can_revoke = getCurNode.getIsCanRevoke();
        Integer can_repulse = getCurNode.getIsRepulse();
        //设置权限
        wfNodeInfoDTO.setIsAutoNextUser(auto_next_user);
        wfNodeInfoDTO.setCanDeal(can_deal);
        wfNodeInfoDTO.setCanAgree(can_agree);
        wfNodeInfoDTO.setCanRefuse(can_refuse);
        wfNodeInfoDTO.setCanReturn(can_return);
        wfNodeInfoDTO.setCanWithdraw(can_withdraw);
        wfNodeInfoDTO.setCanInvalid(can_invaild);
        wfNodeInfoDTO.setCanRevoke(can_revoke);
        wfNodeInfoDTO.setCanRepulse(can_repulse);
        //设置别名
        wfNodeInfoDTO.setDealAlias(getCurNode.getDealAlias());
        wfNodeInfoDTO.setAgreeAlias(getCurNode.getAgreeAlias());
        wfNodeInfoDTO.setRefuseAlias(getCurNode.getRefuseAlias());
        wfNodeInfoDTO.setReturnAlias(getCurNode.getReturnAlias());
        wfNodeInfoDTO.setRevokeAlias(getCurNode.getRevokeAlias());
        wfNodeInfoDTO.setInvalidAlias(getCurNode.getInvalidAlias());
        wfNodeInfoDTO.setWithdrawAlias(getCurNode.getWithdrawAlias());
        wfNodeInfoDTO.setRepulseAlias(getCurNode.getRepulseAlias());

        //函数传参findRelationNextNodesAndUsers 改为当前人而不是发起人。
        List<WfNextNodeDTO> nextNodeDTOS = findRelationNextNodesAndUsers(wfId, nodeId, applyUserId);
        if (nextNodeDTOS == null) {
            can_continue = 0;
            node_type = 3;
        } else {
            can_continue = 1;
            wfNodeInfoDTO.setNextNodes(nextNodeDTOS);
            if (type != 1) {
                List<WfNextNodeDTO> returnNode = FindReturnNode(wfId, wfRunNodeId);
                wfNodeInfoDTO.setReturnNodes(returnNode);
            } else {
                wfNodeInfoDTO.setReturnNodes(new ArrayList<>());
            }
        }

        //region 处理打回节点和信息 打回就是前面所有流程节点都可以
        //节点信息
        //如果表 wf_def_node_repulse 没有数据则自动获取数据
        List<WfNextNodeDTO> nodeRepulses = getWfRepulesNodeDTOS(wfRunNodeId, wfId, nodeId);

        wfNodeInfoDTO.setRepulseNodes(nodeRepulses);

        //enregion
        wfNodeInfoDTO.setType(node_type);
        wfNodeInfoDTO.setCanContinue(can_continue);
        return wfNodeInfoDTO;

    }

    /**
     * 查找打回节点，可以打回到已经发生的任意节点办理人
     * @param wfRunNodeId
     * @param wfId
     * @param nodeId
     * @return
     */
    private List<WfNextNodeDTO> getWfRepulesNodeDTOS(Long wfRunNodeId, Long wfId, Long nodeId) {
//        String sqlNodeRepulse=MessageFormat.format("SELECT n.wf_id, n.id node_id,n.name node_name,n.node_type FROM \n" +
////                "wf_def_node_repulse  r\n" +
////                "LEFT JOIN wf_def_node n ON r.node_id=n.id\n" +
////                "WHERE r.wf_id={0} AND r.cur_node_id={1}", wfId.toString(), nodeId.toString());
////        List<WfNextNodeDTO> nodeRepulses = jdbcTemplate.query(sqlNodeRepulse,
////                BeanPropertyRowMapper.newInstance(WfNextNodeDTO.class));
//////        if (nodeRepulses==null||nodeRepulses.size()==0)
//////        {
////
//////        }


        String sqlRepulse=MessageFormat.format("SELECT n.wf_id, n.id node_id, n.name node_name,n.node_type from (\n" +
                "SELECT r.wf_id, r.run_wf_id,r.cur_node_id FROM wf_run_node r\n" +
                "WHERE r.is_cur_mark=0 and r.run_wf_id=(\n" +
                "SELECT run_wf_id FROM wf_run_node WHERE id={0}) \n" +
                "AND r.cur_node_id!=(SELECT cur_node_id FROM wf_run_node WHERE id={0})\n" +
                "GROUP BY r.wf_id,r.run_wf_id,r.cur_node_id) AS r \n" +
                "LEFT JOIN wf_def_node n ON r.cur_node_id=n.id\n" +
                "JOIN (SELECT wdnr.node_id FROM wf_def_node_repulse wdnr\n" +
                "WHERE (wdnr.wf_id, wdnr.cur_node_id) IN (SELECT n.wf_id, \n" +
                "n.cur_node_id FROM wf_run_node n WHERE id={0})) AS p ON p.node_id=r.cur_node_id", wfRunNodeId.toString());
        List<WfNextNodeDTO>  nodeRepulses = jdbcTemplate.query(sqlRepulse,
                BeanPropertyRowMapper.newInstance(WfNextNodeDTO.class));
        if(nodeRepulses==null||nodeRepulses.size()==0)
            return null;

        //节点办理人信息
        String sqlRepulseUsers=MessageFormat.format("SELECT node_id,user_id,user_name FROM (\n" +
                "SELECT cur_node_id node_id,cur_node_user_id user_id,f_get_user_name(cur_node_user_id) user_name\n" +
                "FROM wf_run_node WHERE run_wf_id=(SELECT run_wf_id FROM wf_run_node WHERE id={0})\n" +
                "union\n" +
                "SELECT node_id,user_id,f_get_user_name(user_id) user_name FROM wf_def_node_user\n" +
                "WHERE type=1 AND wf_id={1}\n" +
                "ORDER BY node_id,user_name) AS w\n" +
                "GROUP BY node_id,user_id,user_name", wfRunNodeId.toString(),wfId.toString());
        List<Map<String, Object>> nodeRepulseUser = jdbcTemplate.queryForList(sqlRepulseUsers);

        for (int i = 0; i < nodeRepulses.size(); i++) {
            WfNextNodeDTO wfNextNodeDTO=nodeRepulses.get(i);
            if (nodeRepulseUser==null) break;
            Long repulseNodeId=wfNextNodeDTO.getNodeId();
            List<WfNodeUserDTO> repulseUsers=new ArrayList<>();
            for (int j = 0; j < nodeRepulseUser.size(); j++) {
                Map<String, Object> map = nodeRepulseUser.get(j);
                //用户赋值
                Long cur_node_id = Long.parseLong(map.get("node_id").toString());
                if (cur_node_id.equals(repulseNodeId))
                {
                    Long cur_userid = Long.parseLong(map.get("user_id").toString());
                    String cur_username =map.get("user_name")==null?"": map.get("user_name").toString();
                    WfNodeUserDTO nodeUserDTO=new WfNodeUserDTO();
                    nodeUserDTO.setUserId(cur_userid);
                    nodeUserDTO.setUserName(cur_username);
                    repulseUsers.add(nodeUserDTO);
                }
            }
            wfNextNodeDTO.setUsers(repulseUsers);
        }
        return nodeRepulses;
    }

    /**
     * 查找退回节点信息
     * @param wfId
     * @param wf_run_node_id
     * @return
     */
    private List<WfNextNodeDTO> FindReturnNode(Long wfId, Long wf_run_node_id) {
        String returnNodeSql = MessageFormat.format("SELECT a.cur_node_id nodeid,b.name nodename,a.cur_node_user_id userid,f_get_user_name(a.cur_node_user_id) username FROM wf_run_node a\n" +
                "LEFT JOIN wf_def_node b ON a.wf_id=b.wf_id AND a.cur_node_id=b.id\n" +
                "WHERE (a.run_wf_id,a.sort)=\n" +
                "(SELECT wrn.run_wf_id,wrn.sort-1 from wf_run_node wrn WHERE wrn.id={0})", wf_run_node_id.toString());
        List<Map<String, Object>> wfMaps = jdbcTemplate.queryForList(returnNodeSql);
        if (wfMaps != null && wfMaps.size() > 0) {
            Map<String, Object> map = wfMaps.get(0);
            //用户赋值
            WfNodeUserDTO userDTO = new WfNodeUserDTO();
            Long userid = Long.parseLong(map.get("userid").toString());
            String username = map.get("username") != null ? map.get("username").toString() : "";
            userDTO.setUserId(userid);
            userDTO.setUserName(username);
            List<WfNodeUserDTO> nodeUserDTOS = new ArrayList<>();
            nodeUserDTOS.add(userDTO);

            List<WfNextNodeDTO> nextNodeDTOS = new ArrayList<>();
            Long nodeid = Long.parseLong(map.get("nodeid").toString());
            String nodename = map.get("nodename") != null ? map.get("nodename").toString() : "";

            WfNextNodeDTO dto = new WfNextNodeDTO();
            dto.setWfId(wfId);
            dto.setUsers(nodeUserDTOS);
            dto.setNodeId(nodeid);
            dto.setNodeName(nodename);
            nextNodeDTOS.add(dto);
            return nextNodeDTOS;
        }

        return null;

    }

    /**
     * 获取任意节点
     *
     * @param wfId   工作流id
     * @param nodeId 节点id
     * @return 节点
     */
    private WfDefNodeEntity getNode(Long wfId, Long nodeId) {
        return getNodeBase(wfId, nodeId, 0);
    }


    /**
     * 获取办理节点
     *
     * @param wfId   工作流id
     * @param nodeId 节点id
     * @return 节点
     */
    private WfDefNodeEntity getDealNode(Long wfId, Long nodeId) {
        return getNodeBase(wfId, nodeId, 2);
    }

    /**
     * 获取结束节点
     *
     * @param wfId   工作流id
     * @param nodeId 节点id
     * @return 节点
     */
    private WfDefNodeEntity getFinishNode(Long wfId, Long nodeId) {
        return getNodeBase(wfId, nodeId, 3);
    }

    /**
     * 获取单个节点
     *
     * @param wfId   工作流id
     * @param nodeId 节点id
     * @return 节点
     */
    private WfDefNodeEntity getNodeBase(Long wfId, Long nodeId, int nodeType) {

        LambdaQueryWrapper<WfDefNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
        wrapper.eq(WfDefNodeEntity::getWfId, wfId)
                .eq(WfDefNodeEntity::getId, nodeId);
        if (nodeType != 0)
            wrapper.eq(WfDefNodeEntity::getNodeType, nodeType);
        WfDefNodeEntity node = this.getOne(wrapper, false);
        return node;
    }

    /**
     * 获取启动节点
     *
     * @param wfStartDTO
     * @return
     */
    private WfDefNodeEntity getStartNode(WfStartDTO wfStartDTO) {
        //先找funname
        String mapSql = MessageFormat.format("SELECT wf_id as value FROM wf_def_map WHERE fun_name=''{0}''", wfStartDTO.getFunName());
        String wfIdString = getSqlValue(mapSql);
        if (wfIdString == null) {
            throw new ValidationException("功能名称不存在");
        }
        Long wfId = Long.parseLong(wfIdString);

        LambdaQueryWrapper<WfDefNodeEntity> wrapper = RestUtils.getLambdaQueryWrapper();
        wrapper.eq(WfDefNodeEntity::getWfId, wfId)
                .eq(WfDefNodeEntity::getNodeType, 1);
        WfDefNodeEntity node = this.getOne(wrapper, false);
        return node;
    }

    private WfRunWfEntity getStartNode(Long runWfId) {

        LambdaQueryWrapper<WfRunWfEntity> wrapper = RestUtils.getLambdaQueryWrapper();
        wrapper.eq(WfRunWfEntity::getId, runWfId);
        WfRunWfEntity node = wfRunWfService.getOne(wrapper, false);
        return node;
    }

    /**
     * 获取所有节点
     *
     * @param wfId   工作流id
     * @param nodeId 节点id
     * @return 节点
     */
    private List<WfDefNodeEntity> getNodes(Long wfId, Long nodeId) {
        LambdaQueryWrapper<WfDefNodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfDefNodeEntity::getWfId, wfId)
                .eq(WfDefNodeEntity::getId, nodeId);
        List<WfDefNodeEntity> nodes = this.list(wrapper);
        return nodes;
    }

}
