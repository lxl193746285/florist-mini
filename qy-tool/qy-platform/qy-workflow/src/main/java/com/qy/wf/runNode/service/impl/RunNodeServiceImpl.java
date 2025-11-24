package com.qy.wf.runNode.service.impl;

import com.google.common.base.Strings;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.message.api.client.TemplateMessageClient;
import com.qy.message.api.command.MessageLogCommand;
import com.qy.message.api.dto.MessageLogDTO;
import com.qy.organization.api.client.DepartmentClient;
import com.qy.organization.api.client.EmployeeClient;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.DateUtils;
import com.qy.utils.RestUtils;
import com.qy.wf.defDefine.service.DefDefineService;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNode.service.DefNodeService;
import com.qy.wf.runNode.dto.*;
import com.qy.wf.runNode.entity.RunNodeEntity;
import com.qy.wf.runNode.mapper.RunNodeMapper;
import com.qy.wf.runNode.service.RunNodeService;
import com.qy.wf.runWf.entity.RunWfEntity;
import com.qy.wf.runWf.mapper.RunWfMapper;
import com.qy.wf.runWf.service.RunWfService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 工作流_执行_节点 服务实现类
 *
 * @author wch
 * @since 2022-11-17
 */
@Service
public class RunNodeServiceImpl extends ServiceImpl<RunNodeMapper, RunNodeEntity> implements RunNodeService {
    @Autowired
    private RunNodeMapper runNodeMapper;
    @Autowired
    private RunWfMapper runWfMapper;
    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Autowired
    private DepartmentClient departmentClient;

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private OrganizationClient organizationClient;

    @Autowired
    private ArkCodeTableService arkCodeTableService;

    @Autowired
    private DefDefineService defDefineService;

    @Autowired
    private DefNodeService defNodeService;
    @Autowired
    private RunWfService runWfService;
    @Autowired
    private TemplateMessageClient templateMessageClient;

    @Override
    public IPage<RunNodeEntity> getRunNodes(IPage iPage, RunNodeQueryDTO runNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<RunNodeEntity> runNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        runNodeQueryWrapper.eq(runNodeQueryDTO.getStatus() != null, RunNodeEntity::getStatus, runNodeQueryDTO.getStatus());
        runNodeQueryWrapper.orderByDesc(RunNodeEntity::getCreateTime);
        return super.page(iPage, runNodeQueryWrapper);
    }

    @Override
    public List<RunNodeEntity> getRunNodes(RunNodeQueryDTO runNodeQueryDTO, MemberIdentity currentUser) {
        LambdaQueryWrapper<RunNodeEntity> runNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(runNodeQueryWrapper);
    }

    @Override
    public RunNodeEntity getRunNode(Long id, MemberIdentity currentUser) {
        RunNodeEntity runNodeEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (runNodeEntity == null) {
            throw new RuntimeException("未找到 工作流_执行_节点");
        }

        return runNodeEntity;
    }

    @Override
    public RunNodeEntity createRunNode(RunNodeFormDTO runNodeFormDTO, MemberIdentity currentUser) {
        RunNodeEntity runNodeEntity = new RunNodeEntity();
        BeanUtils.copyProperties(runNodeFormDTO, runNodeEntity);
        runNodeEntity.setCreateTime(LocalDateTime.now());
        runNodeEntity.setCreatorId(currentUser.getId());
        runNodeEntity.setCreatorName(currentUser.getName());
        runNodeEntity.setUpdatorId(Long.valueOf(0));
        runNodeEntity.setIsDeleted((int) 0);
        runNodeEntity.setDeletorId(Long.valueOf(0));

        this.save(runNodeEntity);
        return runNodeEntity;
    }

    @Override
    public RunNodeEntity updateRunNode(Long id, RunNodeFormDTO runNodeFormDTO, MemberIdentity currentUser) {
        RunNodeEntity runNodeEntity = getById(id);
        BeanUtils.copyProperties(runNodeFormDTO, runNodeEntity);
        runNodeEntity.setUpdateTime(LocalDateTime.now());
        runNodeEntity.setUpdatorId(currentUser.getId());
        runNodeEntity.setUpdatorName(currentUser.getName());

        this.updateById(runNodeEntity);
        return runNodeEntity;
    }

    @Override
    public RunNodeEntity deleteRunNode(Long id, MemberIdentity currentUser) {
        RunNodeEntity runNodeEntity = getRunNode(id, currentUser);
        runNodeEntity.setDeleteTime(LocalDateTime.now());
        runNodeEntity.setDeletorId(currentUser.getId());
        runNodeEntity.setDeletorName(currentUser.getName());

        runNodeEntity.setIsDeleted((int) 1);
        this.updateById(runNodeEntity);
        return runNodeEntity;
    }

    @Override
    public RunNodeDTO mapperToDTO(RunNodeEntity runNodeEntity, MemberIdentity currentUser) {
        RunNodeDTO runNodeDTO = new RunNodeDTO();
        BeanUtils.copyProperties(runNodeEntity, runNodeDTO);
        return runNodeDTO;
    }

    @Override
    public List<RunNodeDTO> mapperToDTO(List<RunNodeEntity> runNodeEntityList, MemberIdentity currentUser) {
        List<RunNodeDTO> runNodeDTOs = new ArrayList<>();
        for (RunNodeEntity runNodeEntity : runNodeEntityList) {
            RunNodeDTO runNodeDTO = mapperToDTO(runNodeEntity, currentUser);
            runNodeDTOs.add(runNodeDTO);
        }


        return runNodeDTOs;
    }

    @Override
    public List<RunNodeDTO> getListById(Long wfId) {
        List<RunNodeDTO> runNodeDTOS = runNodeMapper.getRunNodeListByWfRunId(wfId);
        return runNodeDTOS;
    }

    @Override
    public List<RunNodeDetail> getDetailListById(Long wfId, MemberIdentity currentUser) {
        LambdaQueryWrapper<RunNodeEntity> runNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        runNodeQueryWrapper.eq(wfId != null, RunNodeEntity::getRunWfId, wfId);
        runNodeQueryWrapper.ne(RunNodeEntity::getCurStatus, 5);
        runNodeQueryWrapper.orderByAsc(RunNodeEntity::getSort);
        List<RunNodeEntity> list = super.list(runNodeQueryWrapper);
        List<RunNodeDetail> runNodeDetails = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (RunNodeEntity runNodeEntity : list) {
                RunNodeDetail runNodeDetail = new RunNodeDetail();
                runNodeDetail.setWfRunNodeId(runNodeEntity.getId());
                runNodeDetail.setCurNodeId(runNodeEntity.getCurNodeId());
                runNodeDetail.setNextNodeStatus(runNodeEntity.getNextNodeStatus());
                DefNodeEntity defNodeEntity = defNodeService.getDefNodeById(runNodeEntity.getCurNodeId(), runNodeEntity.getWfId());
                if (defNodeEntity != null) {
                    if (!Strings.isNullOrEmpty(defNodeEntity.getName())) {
                        runNodeDetail.setCurNodeName(defNodeEntity.getName());
                    }
                }
                Long employeeId = runWfMapper.getEmployeeId(runNodeEntity.getCurNodeUserId());
                if (employeeId!=null){
                    runNodeDetail.setCurNodeUserId(employeeId);
                }
                String employeeName = runWfMapper.getEmployeeName(runNodeEntity.getCurNodeUserId());
                if (!Strings.isNullOrEmpty(employeeName)){
                    runNodeDetail.setCurNodeUserName(employeeName);
                }
//                EmployeeBasicDTO employeeBasicDTO = employeeClient.getBasicEmployeeById(runNodeEntity.getCurNodeUserId());
//                if (employeeBasicDTO != null) {
//                    if (!Strings.isNullOrEmpty(employeeBasicDTO.getName())) {
//                        runNodeDetail.setCurNodeUserName(employeeBasicDTO.getName());
//                    }
//                }
                if (runNodeEntity.getIsCurMark() != null) {
                    runNodeDetail.setIsCurMark(runNodeEntity.getIsCurMark());
                    if (runNodeEntity.getIsCurMark() == 1) {
                        runNodeDetail.setIsCurMarkName("*");
                    }
                }
                runNodeDetail.setCurStatus(runNodeEntity.getCurStatus());
                runNodeDetail.setCurStatusName(arkCodeTableService.getNameBySystem("wf_status", runNodeEntity.getCurStatus()));
                runNodeDetail.setNextNodeStatusName(arkCodeTableService.getNameBySystem("wf_status", runNodeEntity.getNextNodeStatus()));
                runNodeDetail.setCurSendTime(DateUtils.localDateTimeToDateStr(runNodeEntity.getCurSendTime(), DateUtils.dateTimeFormat));
                runNodeDetail.setApproveResult(runNodeEntity.getApproveResult());
                runNodeDetail.setApproveResultName(arkCodeTableService.getNameBySystem("wf_approve_result", runNodeEntity.getApproveResult()));
                runNodeDetail.setApproveComments(runNodeEntity.getApproveComments());
                runNodeDetail.setCurArriveTime(DateUtils.localDateTimeToDateStr(runNodeEntity.getCurArriveTime(), DateUtils.dateTimeFormat));
                runNodeDetail.setCurReadTime(DateUtils.localDateTimeToDateStr(runNodeEntity.getCurReadTime(), DateUtils.dateTimeFormat));
                /**计算停留时长  办理时间-到达时间*/
                if (runNodeEntity.getCurDealTime() != null && runNodeEntity.getCurArriveTime() != null) {
                    Date curDealTime = Date.from(runNodeEntity.getCurDealTime().atZone(ZoneId.systemDefault()).toInstant());
                    Date curArriveTime = Date.from(runNodeEntity.getCurArriveTime().atZone(ZoneId.systemDefault()).toInstant());
                    String datePoor = DateUtils.getDatePoor(curDealTime, curArriveTime);
                    runNodeDetail.setStayTime(datePoor);
                } else if (runNodeEntity.getCurDealTime() == null && runNodeEntity.getCurArriveTime() != null) {
                    /**办理时间为null的时候取当前时间**/
                    Date curArriveTime = Date.from(runNodeEntity.getCurArriveTime().atZone(ZoneId.systemDefault()).toInstant());
                    String datePoor = DateUtils.getDatePoor(new Date(), curArriveTime);
                    runNodeDetail.setStayTime(datePoor);
                }
                if (Strings.isNullOrEmpty(runNodeDetail.getStayTime())) {
                    runNodeDetail.setStayTime("0");
                }
                /**消息推送list   List<MessageLogDTO>*/
                MessageLogCommand messageLogCommand = new MessageLogCommand();
                if (runNodeEntity.getRunWfId() != null) {
                    messageLogCommand.setSrcId(String.valueOf(runNodeEntity.getRunWfId()));
                }
                messageLogCommand.setSrcRowId(runNodeEntity.getId());
                List<MessageLogDTO> messageLogDTOList =
                        templateMessageClient.getMessageLogs(null,null,null,messageLogCommand.getSrcId(),messageLogCommand.getSrcRowId());
                if (!CollectionUtils.isEmpty(messageLogDTOList)) {
                    runNodeDetail.setMessageSendList(messageLogDTOList);
                } else {
                    runNodeDetail.setMessageSendList(new ArrayList<>());
                }

//                if (currentUser != null) {
//                    if (currentUser.hasPermission(RunWfAction.VIEW.getPermission())) {
//                        runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfAction.VIEW));
//                    }
//                    if (runNodeEntity.getRunWfId() != null) {
//                        RunWfEntity byId = runWfService.getById(runNodeEntity.getRunWfId());
//                        if (byId != null) {
//                            WfDefNodeEntity defNode = runWfMapper.getDefNode(runNodeEntity.getCurNodeId());
//                            if (byId.getWfStatus() != 3 && currentUser.getId().equals(byId.getCreatorId()) && runNodeEntity.getCurStatus() == 1
//                            && defNode.getIsCanRevoke() == 1) {
//                                if (currentUser.hasPermission(RunWfV2Action.SQUASHER.getPermission())) {
//                                    runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfV2Action.SQUASHER));
//                                }
//                            }
//
//                            if (byId.getWfStatus() != null && defNode != null) {
//                                if (runNodeDetail.getIsCurMark() != null) {
//                                    if (runNodeDetail.getCurNodeUserId().equals(currentUser.getId())) {
//                                        if (currentUser.hasPermission(RunWfAction.TRANSACT.getPermission()) && runNodeDetail.getCurStatus() == 2
//                                                && runNodeDetail.getIsCurMark() == 1 && byId.getWfStatus() != 3
////                                                && defNode.getIsCanDeal() == 1
//                                        ) {
//                                            runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfAction.TRANSACT));
//                                        }
//                                        if (currentUser.hasPermission(RunWfAction.VOIDPF.getPermission()) && runNodeDetail.getCurStatus() == 2
//                                                && runNodeDetail.getIsCurMark() == 1 && byId.getWfStatus() != 3 && defNode.getIsCanInvalid() == 1) {
//                                            runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfAction.VOIDPF));
//                                        }
//                                    }
//                                }
//
//                                if (runNodeDetail.getCurNodeUserId().equals(currentUser.getId())) {
//                                    if (currentUser.hasPermission(RunWfV2Action.SQUASHER.getPermission()) && byId.getWfStatus() != 3
//                                            && currentUser.getId().equals(byId.getBeginUserId()) && defNode.getIsCanRevoke() == 1) {
//                                        runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfV2Action.SQUASHER));
//                                    }
//
//                                    if (runNodeEntity.getNextNodeStatus() != null) {
//                                        if (currentUser.hasPermission(RunWfAction.WITHDRAW.getPermission()) && runNodeEntity.getNextNodeStatus() == 2
//                                                && byId.getWfStatus() != 3 && defNode.getIsCanWithdraw() == 1) {
//                                            runNodeDetail.getActions().add(ArkOperation.fromIEnumAction(RunWfAction.WITHDRAW));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
                runNodeDetails.add(runNodeDetail);
            }
        }
        return runNodeDetails;
    }

    @Override
    public Integer getNextNodeStatus(Long curNodeId, Long curNodeUserId, Long id) {
        Integer nextNodeStatus = runNodeMapper.getNextNodeStatus(curNodeId, curNodeUserId, id);
        return nextNodeStatus;
    }

    @Override
    public List<RunNodeEntity> getRunNodeId(Long id, Long curNodeId, Long curNodeUserId) {
        LambdaQueryWrapper<RunNodeEntity> runNodeQueryWrapper = RestUtils.getLambdaQueryWrapper();
        runNodeQueryWrapper.eq(id != null, RunNodeEntity::getRunWfId, id);
        runNodeQueryWrapper.eq(curNodeId != null, RunNodeEntity::getCurNodeId, curNodeId);
        runNodeQueryWrapper.eq(curNodeUserId != null, RunNodeEntity::getCurNodeUserId, curNodeUserId);
        runNodeQueryWrapper.orderByAsc(RunNodeEntity::getSort);
        return super.list(runNodeQueryWrapper);
    }

    @Override
    public List<RunNodeEntity> getAllNode() {
        return super.list();
    }

    @Override
    public RunNodeEntity getRunNodeByWfRunId(Long wfRunId) {
        if (wfRunId==null){
            return null;
        }
        return runNodeMapper.getRunNodeByWfRunId(wfRunId);
    }

    @Override
    public void replaceRunNodeApprover(ReplaceApproverFormDTO approverFormDTO, MemberIdentity currentUser) {
        // 更换工作流的办理人
        RunWfEntity runWfEntity = runWfService.getRunWf(approverFormDTO.getRunWfId(), currentUser);
        runWfEntity.setCurNodeUserId(approverFormDTO.getCurNodeUserId());
        runWfEntity.setUpdateTime(LocalDateTime.now());
        runWfEntity.setUpdatorId(currentUser.getId());
        runWfEntity.setUpdatorName(currentUser.getName());
        runWfMapper.updateById(runWfEntity);

        // 更换工作流节点的办理人
        RunNodeEntity runNodeEntity = this.getRunNode(approverFormDTO.getCurRunNodeId(), currentUser);
        runNodeEntity.setCurNodeUserId(approverFormDTO.getCurNodeUserId());
        runNodeEntity.setUpdateTime(LocalDateTime.now());
        runNodeEntity.setUpdatorId(currentUser.getId());
        runNodeEntity.setUpdatorName(currentUser.getName());
        this.updateById(runNodeEntity);
    }
}
