package com.qy.jpworkflow.service;

import com.qy.security.session.MemberIdentity;

import java.util.List;

/**
 * 发起工作流 服务类
 *
 * @author syf
 * @since 2024-08-09
 */
public interface StOaWorkflowCaseService {


    void syncWorkflow(MemberIdentity currentUser);
    void syncWorkflowV2(List<Integer> workflowIds, String caseNo, MemberIdentity currentUser);

}
