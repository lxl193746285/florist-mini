package com.qy.member.app.interfaces.client;

import com.qy.member.app.application.command.ModifyMemberInfoCommand;
import com.qy.member.app.application.command.SubmitMemberInfoAuditCommand;
import com.qy.member.app.application.dto.MemberAuditInfoDTO;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.dto.MemberDetailDTO;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.MemberSystemSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证会员
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/mbr/member")
public class IdentityMemberController {
    private MemberSystemSessionContext sessionContext;
    private MemberQueryService memberQueryService;
    private MemberCommandService memberCommandService;

    public IdentityMemberController(MemberSystemSessionContext sessionContext, MemberQueryService memberQueryService, MemberCommandService memberCommandService) {
        this.sessionContext = sessionContext;
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    /**
     * 获取当前登录的会员
     */
    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

    /**
     * 获取当前登录的会员
     */
    @GetMapping
    public ResponseEntity<MemberBasicDTO> getMember() {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(memberQueryService.getBasicMember(identity.getId()));
    }

    /**
     * 获取当前登录的会员详细信息
     */
    @GetMapping("/detail")
    public ResponseEntity<MemberDetailDTO> getMemberDetail() {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(memberQueryService.getDetailMember(identity.getId(), null));
    }

    /**
     * 获取当前登录的会员的审核信息
     */
    @GetMapping("/audit-info")
    public ResponseEntity<MemberAuditInfoDTO> getAuditInfo() {
        MemberIdentity identity = sessionContext.getMember();
        return ResponseUtils.ok().body(memberQueryService.getMemberAuditInfo(identity.getId()));
    }

    /**
     * 提交会员信息审核
     */
    @PatchMapping("/submit-info-audit")
    public ResponseEntity<Object> submitMemberInfoAudit(
            @Valid @RequestBody SubmitMemberInfoAuditCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        command.setMemberId(identity.getId());
        memberCommandService.submitMemberInfoAudit(command);

        return ResponseUtils.ok("会员信息提交审核成功，请等待审核").build();
    }

    /**
     * 修改会员信息
     */
    @PatchMapping("/info")
    public ResponseEntity<Object> modifyMemberInfo(
            @Valid @RequestBody ModifyMemberInfoCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdentity identity = sessionContext.getMember();
        memberCommandService.modifyMemberInfo(command, identity);

        return ResponseUtils.ok("修改会员信息成功").build();
    }
}