package com.qy.jpworkflow.mapper;

import com.qy.jpworkflow.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发起工作流 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2024-08-09
 */
@Mapper
public interface StOaWorkflowCaseMapper {

   List<StOaWorkflowCaseDTO> getWorkflowCaseDTOSAll(@Param("workflowIds") List<Integer> workflowIds, @Param("caseNo") String caseNo);

   List<StOaWorkflowCaseDTO> getWorkflowCaseDTOS(@Param("workflowId") Integer workflowId, @Param("caseNo") String caseNo);

   List<StOaWorkflowCaseStepDTO> getStOaWorkflowCaseStepDTOS(Integer caseId);

   List<StOaWorkflowCaseStepDTO> getStOaWorkflowCaseStepDTOSAllV2(@Param("workflowIds") List<Integer> workflowIds, @Param("caseNo") String caseNo);

   List<StOaWorkflowNodeDTO> getStOaWorkflowNodeDTOS(Integer workflowId);

   List<StOaWorkflowNodeDTO> getStOaWorkflowNodeDTOSV2();

   StOaWorkflowCaseStepNodeDTO getStOaWorkflowCaseStepNodeDTO(Integer caseStepId);

   List<StOaWorkflowCaseStepNodeDTO> getStOaWorkflowCaseStepNodeDTOAllV2(@Param("workflowIds") List<Integer> workflowIds, @Param("caseNo") String caseNo);

   List<StOaWorkflowCaseStepUserDTO> getStOaWorkflowCaseStepUserDTOAll(@Param("workflowIds") List<Integer> workflowIds, @Param("caseNo") String caseNo);

   StOaWorkflowNodeReverseDTO getStOaWorkflowNodeReverseDTO(@Param("curNodeId") Integer curNodeId, @Param("nextNodeId") Integer nextNodeId);


}
