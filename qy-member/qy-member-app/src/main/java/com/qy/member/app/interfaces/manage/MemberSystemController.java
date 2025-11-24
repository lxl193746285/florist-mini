package com.qy.member.app.interfaces.manage;

import com.qy.member.app.application.command.CreateMemberSystemCommand;
import com.qy.member.app.application.command.UpdateMemberSystemCommand;
import com.qy.member.app.application.dto.MemberSystemDTO;
import com.qy.member.app.application.dto.MemberSystemDetailDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.member.app.application.service.MemberSystemCommandService;
import com.qy.member.app.application.service.MemberSystemQueryService;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.RoleDTO;
import com.qy.organization.api.query.RoleQuery;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员系统
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/mbr/member-systems")
public class MemberSystemController {
    private OrganizationSessionContext sessionContext;
    private MemberSystemSessionContext memberSystemSessionContext;

    private MemberSystemCommandService memberSystemCommandService;
    private MemberSystemQueryService memberSystemQueryService;
    private RoleManageClient roleManageClient;

    public MemberSystemController(OrganizationSessionContext sessionContext, MemberSystemCommandService memberSystemCommandService, MemberSystemQueryService memberSystemQueryService, RoleManageClient roleManageClient, MemberSystemSessionContext memberSystemSessionContext) {
        this.sessionContext = sessionContext;
        this.memberSystemCommandService = memberSystemCommandService;
        this.memberSystemQueryService = memberSystemQueryService;
        this.roleManageClient = roleManageClient;
        this.memberSystemSessionContext = memberSystemSessionContext;
    }

    /**
     * 获取会员系统列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MemberSystemDTO>> getMemberSystems(MemberSystemQuery query) {
        query.setOrganizationId(memberSystemSessionContext.getMember().getOrganizationId());
        Page<MemberSystemDTO> page = memberSystemQueryService.getMemberSystems(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个会员系统
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberSystemDetailDTO> getMemberSystem(
        @PathVariable(value = "id") Long id
    ) {
        MemberSystemDetailDTO memberSystemDTO = memberSystemQueryService.getMemberSystemDetailById(id, sessionContext.getUser());
        return ResponseUtils.ok().body(memberSystemDTO);
    }

    /**
     * 创建单个会员系统
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createMemberSystem(
            @Valid @RequestBody CreateMemberSystemCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberSystemCommandService.createMemberSystem(command, sessionContext.getEmployee(command.getOrganizationId()));

        return ResponseUtils.ok("会员系统创建成功").build();
    }

    /**
     * 修改单个会员系统
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateMemberSystem(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateMemberSystemCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        memberSystemCommandService.updateMemberSystem(command, sessionContext.getEmployee(command.getOrganizationId()));

        return ResponseUtils.ok("会员系统修改成功").build();
    }

    /**
     * 删除单个会员系统
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMemberSystem(
        @PathVariable(value = "id") Long id
    ) {
        MemberSystemDTO memberSystemDTO = findMemberSystem(id, null);

        memberSystemCommandService.deleteMemberSystem(id, sessionContext.getEmployee(memberSystemDTO.getOrganizationId()));

        return ResponseUtils.noContent("删除会员系统成功").build();
    }

    /**
     * 获取会员系统权限组列表
     *
     * @return
     */
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleDTO>> getRoles(
            @PathVariable(value = "id") Long id,
            RoleQuery query
    ) {
        MemberSystemDTO memberSystemDTO = findMemberSystem(id, null);
        query.setOrganizationId(memberSystemDTO.getOrganizationId());
        query.setContext("member");
        query.setContextId(id.toString());
        Page<RoleDTO> page = roleManageClient.getRoles(query);

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 查找会员系统
     *
     * @param id
     * @return
     */
    private MemberSystemDTO findMemberSystem(Long id, Identity identity) {
        MemberSystemDTO memberSystemDTO = memberSystemQueryService.getMemberSystemById(id, identity);
        if (memberSystemDTO == null) {
            throw new NotFoundException("未找到指定的会员系统");
        }
        return memberSystemDTO;
    }
}

