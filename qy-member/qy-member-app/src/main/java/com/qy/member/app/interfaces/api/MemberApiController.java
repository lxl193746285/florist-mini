package com.qy.member.app.interfaces.api;

import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.SessionContext;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/api/mbr/members")
public class MemberApiController {
    private MemberQueryService memberQueryService;
    private MemberCommandService memberCommandService;
    private SessionContext sessionContext;

    public MemberApiController(MemberQueryService memberQueryService, MemberCommandService memberCommandService, SessionContext sessionContext) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
        this.sessionContext = sessionContext;
    }

    /**
     * 获取会员
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<SimplePageImpl<MemberDTO>> getMembers(MemberQuery query) {
        Identity identity = sessionContext.getUser();
        Page<MemberDTO> page = memberQueryService.getMembers(query, identity);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }

    /**
     * 根据id获取基本会员信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/basic-members/{id}")
    public ResponseEntity<MemberBasicDTO> getBasicMember(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(memberQueryService.getBasicMember(id));
    }

    /**
     * 批量获取会员的基本信息
     *
     * @param ids 会员id集合
     * @return
     */
    @GetMapping("/basic-members")
    public ResponseEntity<List<MemberBasicDTO>> getBasicMembers(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(memberQueryService.getBasicMembers(ids));
    }

    /**
     * 根据id获取会员详细信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/detail-members/{id}")
    public ResponseEntity<MemberDetailDTO> getDetailMember(
            @PathVariable(value = "id") Long id
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(memberQueryService.getDetailMember(id, identity));
    }

    /**
     * 获取开通会员信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/{id}/open-member-info")
    public ResponseEntity<OpenMemberInfoDTO> getOpenMemberInfo(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(memberQueryService.getOpenMemberInfo(id));
    }

    /**
     * 创建会员
     *
     * @param command
     */
    @PostMapping
    public ResponseEntity<MemberIdDTO> createMember(
            @Valid @RequestBody CreateMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        MemberIdDTO memberIdDTO = memberCommandService.createMember(command);
        return ResponseUtils.created("创建会员成功").body(memberIdDTO);
    }

    /**
     * 更新会员
     *
     * @param command
     */
    @PatchMapping
    public ResponseEntity<Object> updateMember(
            @Valid @RequestBody UpdateMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberCommandService.updateMember(command);
        return ResponseUtils.created("修改会员成功").build();
    }

    /**
     * 开通会员
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/open-member")
    public ResponseEntity<MemberIdDTO> openMember(
            @Valid @RequestBody OpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        return ResponseUtils.ok("开通会员成功").body(memberCommandService.openMember(command));
    }

    /**
     * 修改开通会员
     *
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/open-member")
    public ResponseEntity<Object> updateOpenMember(
            @Valid @RequestBody UpdateOpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberCommandService.updateOpenMember(command);

        return ResponseUtils.ok("修改会员成功").build();
    }

    /**
     * 绑定会员开户组织
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/bind-member-open-account")
    public ResponseEntity<Object> bindMemberOpenAccount(
            @Valid @RequestBody BindMemberOpenAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberCommandService.bindMemberOpenAccount(command);

        return ResponseUtils.ok("绑定会员开户组织成功").build();
    }

    /**
     * 删除会员
     *
     * @param id 会员id
     */
    @DeleteMapping
    public ResponseEntity<Object> deleteMember(
            @RequestParam(value = "id") Long id
    ) {
        memberCommandService.deleteMember(id);
        return ResponseUtils.noContent("删除会员成功").build();
    }

    /**
     * 修改会员的等级
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/{id}/level")
    public ResponseEntity<Object> modifyMemberLevel(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyMemberLevelCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        command.setId(id);
        memberCommandService.modifyMemberLevel(command, null);

        return ResponseUtils.ok("会员等级修改成功").build();
    }

    /**
     * 门店审核
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/store-audit")
    public ResponseEntity<Object> storeAuditMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuditMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        memberCommandService.storeAuditMember(command);

        return ResponseUtils.ok("会员审核成功").build();
    }

    /**
     * 平台审核
     *
     * @param command
     * @return
     */
    @PostMapping("/{id}/platform-audit")
    public ResponseEntity<Object> platformAuditMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuditMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        memberCommandService.platformAuditMember(command);

        return ResponseUtils.ok("会员审核成功").build();
    }



    /**
     * 根据会员系统手机号查询会员
     *
     * @return
     */
    @GetMapping("/basic-members/system-member")
    public ResponseEntity<MemberBasicDTO> getBasicSystemMember(
            @SpringQueryMap MemberQuery query
    ) {
        return ResponseUtils.ok().body(memberQueryService.getMemberByPhoneSystemId(query.getPhone(), query.getSystemId(), query.getOrganizationId()));
    }



    /**
     * 根据会员系统手机号查询会员
     *
     * @return
     */
    @GetMapping("/basic-members/account-system-member")
    public ResponseEntity<MemberBasicDTO> getBasicAccountSystemMember(
            @RequestParam(value = "account_id") Long accountId,
            @RequestParam(value = "system_id") Long systemId,
            @RequestParam(value = "org_id") Long orgId
    ) {
        return ResponseUtils.ok().body(memberQueryService.getMemberByAccountSystemId(accountId, systemId, orgId));
    }

    /**
     * 根据accountId获取会员列表
     *
     * @return
     */
    @GetMapping("/basic-members/member-by-account/{accountId}")
    public ResponseEntity<List<MemberBasicDTO>> getMembersByAccount(
            @PathVariable(value = "accountId") Long accountId

    ) {
        return ResponseUtils.ok().body(memberQueryService.getMembersByAccount(accountId));
    }


    /**
     * 根据accountId获取会员列表
     *
     * @return
     */
    @GetMapping("/basic-members/member-by-account-system")
    public List<MemberBasicDTO> getMembersByAccountAndSystemId(
            @RequestParam(value = "accountId") Long accountId,
            @RequestParam(value = "systemId") Long systemId

    ) {
        return memberQueryService.getMembersByAccountAndSystemId(accountId, systemId);
    }

    /**
     * 授权权限组给会员
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/assign-role")
    public ResponseEntity<Object> assignRoleToMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AssignRoleToMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setMemberId(id);
        memberCommandService.assignRoleToMember(command);

        return ResponseUtils.ok("授权权限组成功").build();
    }

    /**
     * 开通会员
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/open-member-without-account")
    public ResponseEntity<MemberIdDTO> openMemberWithoutAccount(
            @Valid @RequestBody OpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        return ResponseUtils.ok("开通会员成功").body(memberCommandService.openMemberWithoutAccount(command));
    }

    /**
     * 开通会员
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/open-member-account")
    public void openMemberAccount(
            @Valid @RequestBody OpenMemberCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        memberCommandService.memberOpenAccount(command);
    }

}