package com.qy.member.app.interfaces.manage;

import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.dto.AuditLogDTO;
import com.qy.audit.api.query.AuditLogQuery;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.dto.MemberDTO;
import com.qy.member.app.application.dto.MemberDetailDTO;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.member.app.domain.enums.Module;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageQuery;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/mbr/members")
public class MemberController {
    private OrganizationSessionContext sessionContext;
    private MemberQueryService memberQueryService;
    private MemberCommandService memberCommandService;
    private AuditLogClient auditLogClient;

    public MemberController(OrganizationSessionContext sessionContext, MemberQueryService memberQueryService, MemberCommandService memberCommandService, AuditLogClient auditLogClient) {
        this.sessionContext = sessionContext;
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
        this.auditLogClient = auditLogClient;
    }

    /**
     * 获取会员列表
     */
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getMember(MemberQuery query) {
        Identity identity = sessionContext.getUser();
        Page<MemberDTO> memberDTOPage = memberQueryService.getMembers(query, identity);
        return ResponseUtils.ok().body(memberDTOPage.getRecords());
    }

    /**
     * 获取单个会员
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailDTO> getDetailMember(
            @PathVariable(value = "id") Long id
    ) {
        Identity identity = sessionContext.getUser();
        return ResponseUtils.ok().body(memberQueryService.getDetailMember(id, identity));
    }

    /**
     * 修改会员的状态
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> modifyMemberStatus(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyMemberStatusCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        MemberBasicDTO memberDTO = findMember(id);

        command.setId(id);
        memberCommandService.modifyMemberStatus(command, sessionContext.getEmployee(memberDTO.getOrganizationId()));

        return ResponseUtils.ok("会员状态修改成功").build();
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
        MemberBasicDTO memberDTO = findMember(id);

        command.setId(id);
        memberCommandService.modifyMemberLevel(command, sessionContext.getEmployee(memberDTO.getOrganizationId()));

        return ResponseUtils.ok("会员等级修改成功").build();
    }

    /**
     * 重置会员密码
     *
     * @param id
     * @return
     */
    @PatchMapping("/{id}/reset-member-password")
    public ResponseEntity<Object> resetMemberPassword(
            @PathVariable(value = "id") Long id
    ) {
        memberCommandService.resetMemberPassword(id);

        return ResponseUtils.ok("重置会员密码成功").build();
    }

    /**
     * 解除会员微信绑定关系
     *
     * @param id
     * @return
     */
    @PatchMapping("/{id}/unbind-member-weixin")
    public ResponseEntity<Object> unbindMemberWeixin(
            @PathVariable(value = "id") Long id
    ) {
        memberCommandService.unbindMemberWeixin(id);

        return ResponseUtils.ok("会员解绑成功").build();
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
        MemberBasicDTO memberDTO = findMember(id);

        command.setMemberId(id);
        memberCommandService.assignRoleToMember(command);

        return ResponseUtils.ok("授权权限组成功").build();
    }

    /**
     * 授权权限组给会员
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/batch/assign-role")
    public ResponseEntity<Object> assignRoleToMemberBatch(
            @Valid @RequestBody AssignRoleToMemberBatchCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AssignRoleToMemberCommand roleToMemberCommand = new AssignRoleToMemberCommand();
        roleToMemberCommand.setRoleIds(command.getRoleIds());
        if (!CollectionUtils.isEmpty(command.getMemberIds())){
            for (Long id : command.getMemberIds()){
                roleToMemberCommand.setMemberId(id);
                memberCommandService.assignRoleToMember(roleToMemberCommand);
            }
        }


        return ResponseUtils.ok("授权权限组成功").build();
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

        EmployeeIdentity identity = sessionContext.getEmployee();

        command.setId(id);
        if (identity != null) {
            command.setAuditorId(identity.getId());
            command.setAuditorName(identity.getName());
        }
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

        EmployeeIdentity identity = sessionContext.getEmployee();

        command.setId(id);
        if (identity != null) {
            command.setAuditorId(identity.getId());
            command.setAuditorName(identity.getName());
        }
        memberCommandService.platformAuditMember(command);

        return ResponseUtils.ok("会员审核成功").build();
    }

    /**
     * 获取会员审核日志
     *
     * @return
     */
    @GetMapping("/{id}/audit-logs")
    public ResponseEntity<List<AuditLogDTO>> getMemberAuditLogs(
            @PathVariable(value = "id") Long id,
            PageQuery pageQuery
    ) {
        MemberBasicDTO memberDTO = findMember(id);

        AuditLogQuery auditLogQuery = new AuditLogQuery();
        auditLogQuery.setOrganizationId(memberDTO.getOrganizationId());
        auditLogQuery.setModuleId(Module.MEMBER.getId());
        auditLogQuery.setDataId(id);
        auditLogQuery.setPage(pageQuery.getPage());
        auditLogQuery.setPerPage(pageQuery.getPerPage());
        Page<AuditLogDTO> page = auditLogClient.getAuditLogs(auditLogQuery);

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 查找会员
     *
     * @param id
     * @return
     */
    private MemberBasicDTO findMember(Long id) {
        MemberBasicDTO memberDTO = memberQueryService.getBasicMember(id);
        if (memberDTO == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        return memberDTO;
    }
}