package com.qy.jpworkflow.service.impl;

import com.qy.jpworkflow.dto.*;
import com.qy.jpworkflow.mapper.StOaWorkflowCaseMapper;
import com.qy.jpworkflow.service.StOaWorkflowCaseService;
import com.qy.security.session.MemberIdentity;
import com.qy.utils.DateUtils;
import com.qy.workflow.entity.WfRunNodeEntity;
import com.qy.workflow.entity.WfRunWfEntity;
import com.qy.workflow.service.WfRunNodeService;
import com.qy.workflow.service.WfRunWfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 发起工作流 服务实现类
 *
 * @author syf
 * @since 2024-08-09
 */
@Service
public class StOaWorkflowCaseServiceImpl implements StOaWorkflowCaseService {
    @Autowired
    private StOaWorkflowCaseMapper stOaWorkflowCaseMapper;
    @Autowired
    private WfRunWfService wfRunWfService;

    @Autowired
    private WfRunNodeService wfRunNodeService;


    @Override
    public void syncWorkflow(MemberIdentity currentUser) {

    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void syncWorkflow(MemberIdentity currentUser) {
//        //st_oa_workflow_case
////        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOS(1122, "T202407310014");
////        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOS(1123, "T202407250005");
//        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOSAll();
//        for (StOaWorkflowCaseDTO workflowCaseDTO : workflowCaseDTOS) {
//            //插入wf_run_wf表
//            WfRunWfEntity wfRunWfEntity = new WfRunWfEntity();
//            wfRunWfEntity.setId(workflowCaseDTO.getId().longValue());
//            wfRunWfEntity.setWfType(2);
//            wfRunWfEntity.setWfCode(workflowCaseDTO.getCaseNo());
//            wfRunWfEntity.setCompanyId(1812728463449444353L);
//            if (workflowCaseDTO.getWorkflowId().equals(1122)) {
//                wfRunWfEntity.setWfId(1809125487585579009L);
//            } else if (workflowCaseDTO.getWorkflowId().equals(1123)) {
//                wfRunWfEntity.setWfId(1809413299723644929L);
//            } else if (workflowCaseDTO.getWorkflowId().equals(1126)) {
//                wfRunWfEntity.setWfId(1809413053652217857L);
//            } else if (workflowCaseDTO.getWorkflowId().equals(1127)) {
//                wfRunWfEntity.setWfId(1809413475813109761L);
//            }
//            wfRunWfEntity.setWfTitle(workflowCaseDTO.getName());
//            Byte status = workflowCaseDTO.getStatus();//旧通过对应新结束
//            if (status == 1) {//通过
//                wfRunWfEntity.setWfStatus(3);
//                wfRunWfEntity.setApproveResult(1);
//            } else {//办理中
//                wfRunWfEntity.setWfStatus(2);
//                wfRunWfEntity.setApproveResult(null);
//            }
//            if (workflowCaseDTO.getFinishedTime() != null && workflowCaseDTO.getFinishedTime() != 0) {
//                wfRunWfEntity.setEndTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getFinishedTime()));
//            }
//            if (workflowCaseDTO.getCreateTime() != null && workflowCaseDTO.getCreateTime() != 0) {
//                wfRunWfEntity.setBeginTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getCreateTime()));
//            }
//            wfRunWfEntity.setCreatorId(workflowCaseDTO.getCreatorId().longValue());
//            wfRunWfEntity.setCreateTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getCreateTime()));
//
//            //获取 st_oa_workflow_case_step
//            List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepDTOS(workflowCaseDTO.getId());
//            if (!CollectionUtils.isEmpty(stOaWorkflowCaseStepDTOS)) {
//                StOaWorkflowCaseStepDTO firstStepDTO = stOaWorkflowCaseStepDTOS.get(0);
//                StOaWorkflowCaseStepDTO lastStepDTO = stOaWorkflowCaseStepDTOS.get(stOaWorkflowCaseStepDTOS.size() - 1);
//                wfRunWfEntity.setBeginRunNodeId(firstStepDTO.getId().longValue());
//                wfRunWfEntity.setBeginUserId(firstStepDTO.getCreatorId().longValue());
//                wfRunWfEntity.setCurRunNodeId(lastStepDTO.getId().longValue());
//                wfRunWfEntity.setCurNodeUserId(lastStepDTO.getCreatorId().longValue());
//                StOaWorkflowCaseStepDTO stOaWorkflowCaseStepDTOPre = getPreStOaWorkflowCaseStepDTO(stOaWorkflowCaseStepDTOS);//获取上一节点数据
//                if (stOaWorkflowCaseStepDTOPre != null) {//上一节点赋值
//                    wfRunWfEntity.setPreRunNodeId(stOaWorkflowCaseStepDTOPre.getId().longValue());
//                    wfRunWfEntity.setPreNodeUserId(stOaWorkflowCaseStepDTOPre.getCreatorId().longValue());
//                    if (stOaWorkflowCaseStepDTOPre.getStatus() == 1) {
//                        wfRunWfEntity.setPreNodeStatus(3);
//                    } else if (stOaWorkflowCaseStepDTOPre.getStatus() == 0) {
//                        wfRunWfEntity.setPreNodeStatus(2);
//                    }
//                }
//
//                StOaWorkflowCaseStepDTO preStOaWorkflowCaseStepDTOByStep = getPreStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, lastStepDTO.getStep());
//                if (preStOaWorkflowCaseStepDTOByStep != null) {
//                    StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTOPre = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(preStOaWorkflowCaseStepDTOByStep.getId());
//                    if (stOaWorkflowCaseStepNodeDTOPre != null) {
//                        wfRunWfEntity.setPreNodeId(stOaWorkflowCaseStepNodeDTOPre.getNodeId().longValue());
//                    }
//                }
//
//                //st_oa_workflow_case_step_node
//                StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTO = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(lastStepDTO.getId());
//                if (stOaWorkflowCaseStepNodeDTO.getStatus() == 1) {
//                    wfRunWfEntity.setCurStatus(3);
//                } else if (stOaWorkflowCaseStepNodeDTO.getStatus() == 0) {
//                    wfRunWfEntity.setCurStatus(2);
//                }
//                wfRunWfEntity.setCurArriveTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getCreateTime()));
//                wfRunWfEntity.setCurDealTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getHandleTime()));
//                if (stOaWorkflowCaseStepNodeDTO.getIsAgree() != null && stOaWorkflowCaseStepNodeDTO.getIsAgree() == 1) {
//                    wfRunWfEntity.setCurApproveResult(1);
//                } else {
//                    wfRunWfEntity.setCurApproveResult(0);
//                }
//
//            }
//            //获取 st_oa_workflow_node
//            List<StOaWorkflowNodeDTO> stOaWorkflowNodeDTOS = stOaWorkflowCaseMapper.getStOaWorkflowNodeDTOS(workflowCaseDTO.getWorkflowId());
//            if (!CollectionUtils.isEmpty(stOaWorkflowNodeDTOS)) {
//                StOaWorkflowNodeDTO firstStOaWorkflowNodeDTO = stOaWorkflowNodeDTOS.get(0);
//                StOaWorkflowNodeDTO LastStOaWorkflowNodeDTO = stOaWorkflowNodeDTOS.get(stOaWorkflowNodeDTOS.size() - 1);
//                wfRunWfEntity.setBeginNodeId(firstStOaWorkflowNodeDTO.getId().longValue());
//                wfRunWfEntity.setCurNodeId(LastStOaWorkflowNodeDTO.getId().longValue());
////                StOaWorkflowNodeReverseDTO stOaWorkflowNodeReverseDTO = stOaWorkflowCaseMapper.getStOaWorkflowNodeReverseDTO(null, LastStOaWorkflowNodeDTO.getId());//获取上一节点
//
//
//
//
////                StOaWorkflowNodeReverseDTO stOaWorkflowNodeReverseDTONext = stOaWorkflowCaseMapper.getStOaWorkflowNodeReverseDTO(LastStOaWorkflowNodeDTO.getId(), null);//获取下一节点
////                if (stOaWorkflowNodeReverseDTONext != null) {
////                    wfRunWfEntity.setNextNodeId(stOaWorkflowNodeReverseDTONext.getNextNodeId().longValue());
////                }
//            }
//
//            wfRunWfService.save(wfRunWfEntity);
//
//            //子表处理
//            for (StOaWorkflowCaseStepDTO stOaWorkflowCaseStepDTO : stOaWorkflowCaseStepDTOS) {
//                WfRunNodeEntity wfRunNodeEntity = new WfRunNodeEntity();
//                wfRunNodeEntity.setId(stOaWorkflowCaseStepDTO.getId().longValue());
//                wfRunNodeEntity.setRunWfId(wfRunWfEntity.getId());
//                wfRunNodeEntity.setWfId(wfRunWfEntity.getWfId());
//                wfRunNodeEntity.setSort(stOaWorkflowCaseStepDTO.getStep());
//                if (stOaWorkflowCaseStepDTO.getStep() == stOaWorkflowCaseStepDTOS.size()) {
//                    wfRunNodeEntity.setIsCurMark(1);
//                    //不是最后一条3，是最后一条，如果主流程已结束，则是3，未结束则是2
//                    if (status == 1) {//通过
//                        wfRunNodeEntity.setCurStatus(3);
//                    } else {//办理中
//                        wfRunNodeEntity.setCurStatus(2);
//                    }
//                } else {
//                    wfRunNodeEntity.setIsCurMark(0);
//                    wfRunNodeEntity.setCurStatus(3);
//                }
//                StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTO = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(stOaWorkflowCaseStepDTO.getId());
//                wfRunNodeEntity.setCurNodeId(stOaWorkflowCaseStepNodeDTO.getNodeId().longValue());
//                wfRunNodeEntity.setCurNodeUserId(stOaWorkflowCaseStepDTO.getCreatorId().longValue());
//                wfRunNodeEntity.setCurArriveTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getCreateTime()));
//                wfRunNodeEntity.setCurDealTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getHandleTime()));
//
//                if (stOaWorkflowCaseStepNodeDTO.getIsAgree() != null && stOaWorkflowCaseStepNodeDTO.getIsAgree() == 1) {
//                    wfRunNodeEntity.setApproveResult(1);
//                } else {
//                    wfRunNodeEntity.setApproveResult(0);
//                }
//                StOaWorkflowCaseStepDTO nextStOaWorkflowCaseStepDTOByStep = getNextStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, stOaWorkflowCaseStepDTO.getStep());//获取下一节点
//                if (nextStOaWorkflowCaseStepDTOByStep != null) {
//                    wfRunNodeEntity.setNextRunNodeId(nextStOaWorkflowCaseStepDTOByStep.getId().longValue());
//                    //获取下一节点nodeid
//
//                    wfRunNodeEntity.setNextNodeUserId(nextStOaWorkflowCaseStepDTOByStep.getCreatorId().longValue());
//                    StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTONext = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(nextStOaWorkflowCaseStepDTOByStep.getId());
//                    if (stOaWorkflowCaseStepNodeDTONext != null) {
//                        wfRunNodeEntity.setNextNodeId(stOaWorkflowCaseStepNodeDTONext.getNodeId().longValue());
//                    }
//                    if (stOaWorkflowCaseStepNodeDTONext.getStatus() == 1) {
//                        wfRunNodeEntity.setNextNodeStatus(3);
//                    } else if (stOaWorkflowCaseStepNodeDTONext.getStatus() == 0) {
//                        wfRunNodeEntity.setNextNodeStatus(2);
//                    }
//                }
//
//                //pre 值获取
//                StOaWorkflowCaseStepDTO preStOaWorkflowCaseStepDTOByStep = getPreStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, stOaWorkflowCaseStepDTO.getStep());//获取上一节点
//                if (preStOaWorkflowCaseStepDTOByStep != null) {
//                    wfRunNodeEntity.setPreRunNodeId(preStOaWorkflowCaseStepDTOByStep.getId().longValue());
//                    wfRunNodeEntity.setPreNodeUserId(preStOaWorkflowCaseStepDTOByStep.getCreatorId().longValue());
//
//                    StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTOPre = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(preStOaWorkflowCaseStepDTOByStep.getId());
//                    //获取上一节点nodeid
//                    if (stOaWorkflowCaseStepNodeDTOPre != null) {
//                        wfRunNodeEntity.setPreNodeId(stOaWorkflowCaseStepNodeDTOPre.getNodeId().longValue());
//                    }
//                    if (stOaWorkflowCaseStepNodeDTOPre != null) {
//                        if (stOaWorkflowCaseStepNodeDTOPre.getStatus() == 1) {
//                            wfRunNodeEntity.setPreNodeStatus(3);
//                        } else if (stOaWorkflowCaseStepNodeDTOPre.getStatus() == 0) {
//                            wfRunNodeEntity.setPreNodeStatus(2);
//                        }
//                    }
//                }
//
//
//
//
//                wfRunNodeEntity.setCreatorId(stOaWorkflowCaseStepDTO.getCreatorId().longValue());
//                wfRunNodeEntity.setCreateTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepDTO.getCreateTime()));
//                wfRunNodeService.save(wfRunNodeEntity);
//
//            }
//
//        }
//    }


    @Override
    public void syncWorkflowV2(List<Integer> workflowIds, String caseNo, MemberIdentity currentUser) {
        //st_oa_workflow_case
//        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOS(1122, "T202407310014");
//        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOS(1123, "T202407250005");
        List<StOaWorkflowCaseDTO> workflowCaseDTOS = stOaWorkflowCaseMapper.getWorkflowCaseDTOSAll(workflowIds, caseNo);

        //获取 st_oa_workflow_case_step
        List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOSAll = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepDTOSAllV2(workflowIds, caseNo);
        //获取 st_oa_workflow_case_step_node
        List<StOaWorkflowCaseStepNodeDTO> stOaWorkflowCaseStepNodeDTOListAll = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTOAllV2(workflowIds, caseNo);
        //获取 st_oa_workflow_case_step_user
        List<StOaWorkflowCaseStepUserDTO> stepUserDTOSAll = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepUserDTOAll(workflowIds, caseNo);
        //获取 st_oa_workflow_node
        List<StOaWorkflowNodeDTO> stOaWorkflowNodeDTOSAll = stOaWorkflowCaseMapper.getStOaWorkflowNodeDTOSV2();



        for (StOaWorkflowCaseDTO workflowCaseDTO : workflowCaseDTOS) {
            //插入wf_run_wf表
            WfRunWfEntity wfRunWfEntity = new WfRunWfEntity();
            wfRunWfEntity.setId(workflowCaseDTO.getId().longValue());
            wfRunWfEntity.setWfType(2);
            wfRunWfEntity.setWfCode(workflowCaseDTO.getCaseNo());
            wfRunWfEntity.setCompanyId(1812728463449444353L);
            if (workflowCaseDTO.getWorkflowId().equals(1122)) {
                wfRunWfEntity.setWfId(1809125487585579009L);
            } else if (workflowCaseDTO.getWorkflowId().equals(1123)) {
                wfRunWfEntity.setWfId(1809413299723644929L);
            } else if (workflowCaseDTO.getWorkflowId().equals(1126)) {
                wfRunWfEntity.setWfId(1809413053652217857L);
            } else if (workflowCaseDTO.getWorkflowId().equals(1127)) {
                wfRunWfEntity.setWfId(1809413475813109761L);
            }
            wfRunWfEntity.setWfTitle(workflowCaseDTO.getName());
            Byte status = workflowCaseDTO.getStatus();//旧通过对应新结束
            if (status == 1) {//通过
                wfRunWfEntity.setWfStatus(3);
                wfRunWfEntity.setApproveResult(1);
            } else {//办理中
                wfRunWfEntity.setWfStatus(2);
                wfRunWfEntity.setApproveResult(null);
            }
            if (workflowCaseDTO.getFinishedTime() != null && workflowCaseDTO.getFinishedTime() != 0) {
                wfRunWfEntity.setEndTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getFinishedTime()));
            }
            if (workflowCaseDTO.getCreateTime() != null && workflowCaseDTO.getCreateTime() != 0) {
                wfRunWfEntity.setBeginTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getCreateTime()));
            }
            wfRunWfEntity.setCreatorId(workflowCaseDTO.getCreatorId().longValue());
            wfRunWfEntity.setCreateTime(DateUtils.timeStampToLocalDateTime(workflowCaseDTO.getCreateTime()));

            //获取 st_oa_workflow_case_step
//            List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepDTOS(workflowCaseDTO.getId());
            List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS = stOaWorkflowCaseStepDTOSAll.stream().filter(o -> o.getWorkflowCaseId().equals(workflowCaseDTO.getId())).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(stOaWorkflowCaseStepDTOS)) {
                StOaWorkflowCaseStepDTO firstStepDTO = stOaWorkflowCaseStepDTOS.get(0);
                StOaWorkflowCaseStepDTO lastStepDTO = stOaWorkflowCaseStepDTOS.get(stOaWorkflowCaseStepDTOS.size() - 1);
                //获取当前节点nodeUserId
                Optional<StOaWorkflowCaseStepUserDTO> first1 = stepUserDTOSAll.stream().filter(o -> o.getCaseStepId().equals(lastStepDTO.getId())).findFirst();
                if (first1.isPresent()) {
                    StOaWorkflowCaseStepUserDTO stOaWorkflowCaseStepUserDTO = first1.get();
                    wfRunWfEntity.setCurNodeUserId(stOaWorkflowCaseStepUserDTO.getUserId().longValue());
                }


                wfRunWfEntity.setBeginRunNodeId(firstStepDTO.getId().longValue());
                wfRunWfEntity.setBeginUserId(firstStepDTO.getCreatorId().longValue());
                wfRunWfEntity.setCurRunNodeId(lastStepDTO.getId().longValue());
                StOaWorkflowCaseStepDTO stOaWorkflowCaseStepDTOPre = getPreStOaWorkflowCaseStepDTO(stOaWorkflowCaseStepDTOS);//获取上一节点数据
                if (stOaWorkflowCaseStepDTOPre != null) {//上一节点赋值
                    wfRunWfEntity.setPreRunNodeId(stOaWorkflowCaseStepDTOPre.getId().longValue());
                    //获取上一节点nodeUserId
                    Optional<StOaWorkflowCaseStepUserDTO> firstPre = stepUserDTOSAll.stream().filter(o -> o.getCaseStepId().equals(stOaWorkflowCaseStepDTOPre.getId())).findFirst();
                    if (firstPre.isPresent()) {
                        StOaWorkflowCaseStepUserDTO stOaWorkflowCaseStepUserDTO = firstPre.get();
                        wfRunWfEntity.setPreNodeUserId(stOaWorkflowCaseStepUserDTO.getUserId().longValue());
                    }

                    if (stOaWorkflowCaseStepDTOPre.getStatus() == 1) {
                        wfRunWfEntity.setPreNodeStatus(3);
                    } else if (stOaWorkflowCaseStepDTOPre.getStatus() == 0) {
                        wfRunWfEntity.setPreNodeStatus(2);
                    }
                }

                StOaWorkflowCaseStepDTO preStOaWorkflowCaseStepDTOByStep = getPreStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, lastStepDTO.getStep());
                if (preStOaWorkflowCaseStepDTOByStep != null) {
                    Optional<StOaWorkflowCaseStepNodeDTO> first = stOaWorkflowCaseStepNodeDTOListAll.stream().filter(o -> o.getCaseStepId().equals(preStOaWorkflowCaseStepDTOByStep.getId())).findFirst();
                    if (first.isPresent()) {
                        StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTOPre = first.get();
                        if (stOaWorkflowCaseStepNodeDTOPre != null) {
                            wfRunWfEntity.setPreNodeId(stOaWorkflowCaseStepNodeDTOPre.getNodeId().longValue());
                        }

                    }
                }

                //st_oa_workflow_case_step_node
                Optional<StOaWorkflowCaseStepNodeDTO> first = stOaWorkflowCaseStepNodeDTOListAll.stream().filter(o -> o.getCaseStepId().equals(lastStepDTO.getId())).findFirst();
                if (first.isPresent()) {
                    StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTO = first.get();
                    if (stOaWorkflowCaseStepNodeDTO != null) {
                        if (stOaWorkflowCaseStepNodeDTO.getStatus() == 1) {
                            wfRunWfEntity.setCurStatus(3);
                        } else if (stOaWorkflowCaseStepNodeDTO.getStatus() == 0) {
                            wfRunWfEntity.setCurStatus(2);
                        }
                        wfRunWfEntity.setCurArriveTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getCreateTime()));
                        wfRunWfEntity.setCurDealTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getHandleTime()));
//                        if (stOaWorkflowCaseStepNodeDTO.getAction() != null) {
//                            if (stOaWorkflowCaseStepNodeDTO.getAction() == 1) {
//                                wfRunWfEntity.setCurApproveResult(1);
//                            } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 2) {
//                                wfRunWfEntity.setCurApproveResult(3);
//                            } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 3) {
//                                wfRunWfEntity.setCurApproveResult(5);
//                            } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 4) {
//                                wfRunWfEntity.setCurApproveResult(4);
//                            }
//                        }

                        if (stOaWorkflowCaseStepNodeDTO.getIsAgree() != null && stOaWorkflowCaseStepNodeDTO.getIsAgree() == 1) {
                            wfRunWfEntity.setCurApproveResult(1);
                        } else {
                            wfRunWfEntity.setCurApproveResult(0);
                        }

                    }
                }


            }
            //获取 st_oa_workflow_node
            List<StOaWorkflowNodeDTO> stOaWorkflowNodeDTOS = stOaWorkflowNodeDTOSAll.stream().filter(o -> o.getWorkflowId().equals((workflowCaseDTO.getWorkflowId()))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stOaWorkflowNodeDTOS)) {
                StOaWorkflowNodeDTO firstStOaWorkflowNodeDTO = stOaWorkflowNodeDTOS.get(0);
                StOaWorkflowNodeDTO LastStOaWorkflowNodeDTO = stOaWorkflowNodeDTOS.get(stOaWorkflowNodeDTOS.size() - 1);
                wfRunWfEntity.setBeginNodeId(firstStOaWorkflowNodeDTO.getId().longValue());
                wfRunWfEntity.setCurNodeId(LastStOaWorkflowNodeDTO.getId().longValue());
            }

            wfRunWfService.save(wfRunWfEntity);

            //子表处理
            List<WfRunNodeEntity> entityList = new ArrayList<>();
            for (StOaWorkflowCaseStepDTO stOaWorkflowCaseStepDTO : stOaWorkflowCaseStepDTOS) {
                WfRunNodeEntity wfRunNodeEntity = new WfRunNodeEntity();
                wfRunNodeEntity.setId(stOaWorkflowCaseStepDTO.getId().longValue());
                wfRunNodeEntity.setRunWfId(wfRunWfEntity.getId());
                wfRunNodeEntity.setWfId(wfRunWfEntity.getWfId());
                wfRunNodeEntity.setSort(stOaWorkflowCaseStepDTO.getStep());
                if (stOaWorkflowCaseStepDTO.getStep() == stOaWorkflowCaseStepDTOS.size()) {
                    wfRunNodeEntity.setIsCurMark(1);
//                    //不是最后一条3，是最后一条，如果主流程已结束，则是3，未结束则是2
//                    if (status == 1) {//通过
//                        wfRunNodeEntity.setCurStatus(3);
//                    } else {//办理中
//                        wfRunNodeEntity.setCurStatus(2);
//                    }
                } else {
                    wfRunNodeEntity.setIsCurMark(0);
//                    wfRunNodeEntity.setCurStatus(3);
                }
                // curStatus 赋值
//                如果主流程已结束，都是3；
//                主流程未结束，则最后一条是2，其余都是3。
                if (wfRunWfEntity.getWfStatus() == 3) {//主流程结束
                    wfRunNodeEntity.setCurStatus(3);
                } else {
                    if (stOaWorkflowCaseStepDTOS.size() == stOaWorkflowCaseStepDTO.getStep()) {//最后一条
                        wfRunNodeEntity.setCurStatus(2);
                    } else {
                        wfRunNodeEntity.setCurStatus(3);
                    }
                }

                Optional<StOaWorkflowCaseStepNodeDTO> first = stOaWorkflowCaseStepNodeDTOListAll.stream().filter(o -> o.getCaseStepId().equals(stOaWorkflowCaseStepDTO.getId())).findFirst();
                if (first.isPresent()) {
                    StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTO = first.get();
                    wfRunNodeEntity.setCurNodeId(stOaWorkflowCaseStepNodeDTO.getNodeId().longValue());
//                    wfRunNodeEntity.setCurNodeUserId(stOaWorkflowCaseStepDTO.getCreatorId().longValue());
                    // CurNodeUserId 赋值
                    Optional<StOaWorkflowCaseStepUserDTO> firstCur = stepUserDTOSAll.stream().filter(o -> o.getCaseStepId().equals(stOaWorkflowCaseStepDTO.getId())).findFirst();
                    if (firstCur.isPresent()) {
                        StOaWorkflowCaseStepUserDTO stOaWorkflowCaseStepUserDTO = firstCur.get();
                        wfRunNodeEntity.setCurNodeUserId(stOaWorkflowCaseStepUserDTO.getUserId().longValue());
                    }

                    wfRunNodeEntity.setCurArriveTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getCreateTime()));
                    wfRunNodeEntity.setCurDealTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepNodeDTO.getHandleTime()));

//                    if (stOaWorkflowCaseStepNodeDTO.getIsAgree() != null && stOaWorkflowCaseStepNodeDTO.getIsAgree() == 1) {
//                        wfRunNodeEntity.setApproveResult(1);
//                    } else {
//                        wfRunNodeEntity.setApproveResult(0);
//                    }

                    // ApproveResult 赋值
                    if (stOaWorkflowCaseStepNodeDTO.getAction() != null) {
                        if (stOaWorkflowCaseStepNodeDTO.getAction() == 1) {
                            wfRunNodeEntity.setApproveResult(1);
                        } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 2) {
                            wfRunNodeEntity.setApproveResult(3);
                        } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 3) {
                            wfRunNodeEntity.setApproveResult(5);
                        } else if (stOaWorkflowCaseStepNodeDTO.getAction() == 4) {
                            wfRunNodeEntity.setApproveResult(4);
                        }
                    }
                }
//                StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTO = stOaWorkflowCaseMapper.getStOaWorkflowCaseStepNodeDTO(stOaWorkflowCaseStepDTO.getId());

                StOaWorkflowCaseStepDTO nextStOaWorkflowCaseStepDTOByStep = getNextStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, stOaWorkflowCaseStepDTO.getStep());//获取下一节点
                if (nextStOaWorkflowCaseStepDTOByStep != null) {
                    wfRunNodeEntity.setNextRunNodeId(nextStOaWorkflowCaseStepDTOByStep.getId().longValue());
                    //获取下一节点nodeid

//                    wfRunNodeEntity.setNextNodeUserId(nextStOaWorkflowCaseStepDTOByStep.getCreatorId().longValue());

                    // NextNodeUserId 赋值
                    Optional<StOaWorkflowCaseStepUserDTO> firstNext = stepUserDTOSAll.stream().filter(o -> o.getCaseStepId().equals(nextStOaWorkflowCaseStepDTOByStep.getId())).findFirst();
                    if (firstNext.isPresent()) {
                        StOaWorkflowCaseStepUserDTO stOaWorkflowCaseStepUserDTO = firstNext.get();
                        wfRunNodeEntity.setNextNodeUserId(stOaWorkflowCaseStepUserDTO.getUserId().longValue());
                    }

                    Optional<StOaWorkflowCaseStepNodeDTO> first1 = stOaWorkflowCaseStepNodeDTOListAll.stream().filter(o -> o.getCaseStepId().equals(nextStOaWorkflowCaseStepDTOByStep.getId())).findFirst();
                    if (first1.isPresent()) {
                        StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTONext = first1.get();
                        if (stOaWorkflowCaseStepNodeDTONext != null) {
                            wfRunNodeEntity.setNextNodeId(stOaWorkflowCaseStepNodeDTONext.getNodeId().longValue());
                        }
                        if (stOaWorkflowCaseStepNodeDTONext.getStatus() == 1) {
                            wfRunNodeEntity.setNextNodeStatus(3);
                        } else if (stOaWorkflowCaseStepNodeDTONext.getStatus() == 0) {
                            wfRunNodeEntity.setNextNodeStatus(2);
                        }
                    }

                }

                //pre 值获取
                StOaWorkflowCaseStepDTO preStOaWorkflowCaseStepDTOByStep = getPreStOaWorkflowCaseStepDTOByStep(stOaWorkflowCaseStepDTOS, stOaWorkflowCaseStepDTO.getStep());//获取上一节点
                if (preStOaWorkflowCaseStepDTOByStep != null) {
                    wfRunNodeEntity.setPreRunNodeId(preStOaWorkflowCaseStepDTOByStep.getId().longValue());
//                    wfRunNodeEntity.setPreNodeUserId(preStOaWorkflowCaseStepDTOByStep.getCreatorId().longValue());

                    // PreNodeUserId 赋值
                    Optional<StOaWorkflowCaseStepUserDTO> firstPre = stepUserDTOSAll.stream().filter(o -> o.getCaseStepId().equals(preStOaWorkflowCaseStepDTOByStep.getId())).findFirst();
                    if (firstPre.isPresent()) {
                        StOaWorkflowCaseStepUserDTO stOaWorkflowCaseStepUserDTO = firstPre.get();
                        wfRunNodeEntity.setPreNodeUserId(stOaWorkflowCaseStepUserDTO.getUserId().longValue());
                    }

                    Optional<StOaWorkflowCaseStepNodeDTO> first1 = stOaWorkflowCaseStepNodeDTOListAll.stream().filter(o -> o.getCaseStepId().equals(preStOaWorkflowCaseStepDTOByStep.getId())).findFirst();
                    if (first1.isPresent()) {
                        StOaWorkflowCaseStepNodeDTO stOaWorkflowCaseStepNodeDTOPre = first1.get();
                        //获取上一节点nodeid
                        if (stOaWorkflowCaseStepNodeDTOPre != null) {
                            wfRunNodeEntity.setPreNodeId(stOaWorkflowCaseStepNodeDTOPre.getNodeId().longValue());
                        }
                        if (stOaWorkflowCaseStepNodeDTOPre != null) {
                            if (stOaWorkflowCaseStepNodeDTOPre.getStatus() == 1) {
                                wfRunNodeEntity.setPreNodeStatus(3);
                            } else if (stOaWorkflowCaseStepNodeDTOPre.getStatus() == 0) {
                                wfRunNodeEntity.setPreNodeStatus(2);
                            }
                        }
                    }

                }

                wfRunNodeEntity.setCreatorId(stOaWorkflowCaseStepDTO.getCreatorId().longValue());
                wfRunNodeEntity.setCreateTime(DateUtils.timeStampToLocalDateTime(stOaWorkflowCaseStepDTO.getCreateTime()));
                entityList.add(wfRunNodeEntity);
            }
            wfRunNodeService.saveBatch(entityList);

        }
    }

    private StOaWorkflowCaseStepDTO getPreStOaWorkflowCaseStepDTO(List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS) {
        int currentIndex = stOaWorkflowCaseStepDTOS.size() - 1; // 假设当前元素的索引是2

        // 获取上一条记录
        StOaWorkflowCaseStepDTO previousItem = null;
        if (currentIndex > 0) {
            previousItem = stOaWorkflowCaseStepDTOS.get(currentIndex - 1);
        }
        return previousItem;
    }


    private StOaWorkflowCaseStepDTO getNextStOaWorkflowCaseStepDTOByStep(List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS, Integer step) {
        // 获取上一条记录
        StOaWorkflowCaseStepDTO nextItem = null;
//        List<StOaWorkflowCaseStepDTO> collect = stOaWorkflowCaseStepDTOS.stream().filter(o -> o.getStep().equals(step)).collect(Collectors.toList());

        if (stOaWorkflowCaseStepDTOS.size() != step) {
            int currentIndex = step - 1; // 假设当前元素的索引是2

            // 获取下一条记录
            if (currentIndex < stOaWorkflowCaseStepDTOS.size() - 1) {
                nextItem = stOaWorkflowCaseStepDTOS.get(currentIndex + 1);
            }
        }


        return nextItem;
    }

    private StOaWorkflowCaseStepDTO getPreStOaWorkflowCaseStepDTOByStep(List<StOaWorkflowCaseStepDTO> stOaWorkflowCaseStepDTOS, Integer step) {
        // 获取上一条记录
        StOaWorkflowCaseStepDTO previousItem = null;
//        List<StOaWorkflowCaseStepDTO> collect = stOaWorkflowCaseStepDTOS.stream().filter(o -> o.getStep().equals(step)).collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(collect)) {
            int currentIndex = step - 1; // 假设当前元素的索引是2
            // 获取上一条记录
            if (currentIndex > 0) {
                previousItem = stOaWorkflowCaseStepDTOS.get(currentIndex - 1);
            }

//        }


        return previousItem;
    }














}
