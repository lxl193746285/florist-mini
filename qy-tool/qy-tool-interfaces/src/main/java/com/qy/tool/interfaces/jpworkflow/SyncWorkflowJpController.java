package com.qy.tool.interfaces.jpworkflow;

import com.qy.common.BaseController;
import com.qy.jpworkflow.service.StOaWorkflowCaseService;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发起工作流
 *
 * @author syf
 * @since 2024-08-09
 */
@RestController
@Validated
@RequestMapping("/v4/platform/st-oa-workflow-cases")
@Api(tags = "发起工作流")
public class SyncWorkflowJpController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private StOaWorkflowCaseService stOaWorkflowCaseService;

    @GetMapping
    public void syncWorkflow(
            @RequestParam(value = "workflow_ids") List<Integer> workflowIds,
            @RequestParam(value = "case_no", required = false) String caseNo
    ) {
        MemberIdentity currentUser = context.getMember();
        stOaWorkflowCaseService.syncWorkflowV2(workflowIds, caseNo, currentUser);
    }


}

