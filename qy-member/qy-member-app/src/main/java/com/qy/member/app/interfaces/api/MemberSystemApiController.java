package com.qy.member.app.interfaces.api;

import com.qy.member.app.application.command.CreateMemberSystemCommand;
import com.qy.member.app.application.command.MemberSystemAuthorizationCommand;
import com.qy.member.app.application.command.UpdateMemberSystemCommand;
import com.qy.member.app.application.dto.MemberSystemBasicDTO;
import com.qy.member.app.application.dto.MemberSystemBasicExtendDTO;
import com.qy.member.app.application.dto.MemberSystemDTO;
import com.qy.member.app.application.dto.MemberSystemIdDTO;
import com.qy.member.app.application.service.MemberSystemCommandService;
import com.qy.member.app.application.service.MemberSystemOrganizationCommandService;
import com.qy.member.app.application.service.MemberSystemQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员系统内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/api/mbr/member-systems")
public class MemberSystemApiController {
    private OrganizationSessionContext sessionContext;
    private MemberSystemQueryService memberSystemQueryService;
    private MemberSystemCommandService memberSystemCommandService;
    private MemberSystemOrganizationCommandService memberSystemOrganizationCommandService;

    public MemberSystemApiController(OrganizationSessionContext sessionContext, MemberSystemQueryService memberSystemQueryService, MemberSystemCommandService memberSystemCommandService, MemberSystemOrganizationCommandService memberSystemOrganizationCommandService) {
        this.sessionContext = sessionContext;
        this.memberSystemQueryService = memberSystemQueryService;
        this.memberSystemCommandService = memberSystemCommandService;
        this.memberSystemOrganizationCommandService = memberSystemOrganizationCommandService;
    }

    /**
     * 根据ID查询基本会员系统
     *
     * @param id 会员系统id
     * @return
     */
    @GetMapping("/basic-member-system/{id}")
    public ResponseEntity<MemberSystemBasicDTO> getBasicMemberSystemById(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(memberSystemQueryService.getBasicMemberSystemById(id));
    }

    /**
     * 根据IDs查询基本会员系统
     *
     * @param ids 会员系统ids
     * @return
     */
    @GetMapping("/basic-member-system/by-ids")
    ResponseEntity<List<MemberSystemBasicDTO>> getBasicMemberSystemsByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(memberSystemQueryService.getBasicMemberSystemsByIds(ids));
    }

    /**
     * 查询组织下的基本会员系统
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping("/basic-member-systems")
    public ResponseEntity<List<MemberSystemBasicDTO>> getBasicMemberSystemsByOrganizationId(
            @RequestParam(value = "organization_id") Long organizationId
    ) {
        return ResponseUtils.ok().body(memberSystemQueryService.getBasicMemberSystemsByOrganizationId(organizationId));
    }

    /**
     * 查询组织下的基本会员系统扩展
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping("/basic-member-systems-extend")
    public ResponseEntity<List<MemberSystemBasicExtendDTO>> getBasicMemberSystemsExtendByOrganizationId(
            @RequestParam(value = "organization_id") Long organizationId
    ) {
        return ResponseUtils.ok().body(memberSystemQueryService.getBasicMemberSystemsExtendByOrganizationId(organizationId));
    }

    /**
     * 查询组织和指定来源下的基本会员系统
     *
     * @param organizationId 组织id
     * @param source 来源
     * @return
     */
    @GetMapping("/basic-member-systems/by-source")
    public ResponseEntity<List<MemberSystemBasicDTO>> getBasicMemberSystemsByOrganizationIdAndSource(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "source") Integer source
    ) {
        return ResponseUtils.ok().body(memberSystemQueryService.getBasicMemberSystemsByOrganizationIdAndSource(organizationId, source));
    }

    /**
     * 创建单个会员系统
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<MemberSystemIdDTO> createMemberSystem(
            @Valid @RequestBody CreateMemberSystemCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        Long id = memberSystemCommandService.createMemberSystem(command, null);

        return ResponseUtils.ok("会员系统创建成功").body(new MemberSystemIdDTO(id));
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
        memberSystemCommandService.updateMemberSystem(command, null);

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

        memberSystemCommandService.deleteMemberSystem(id, null);

        return ResponseUtils.noContent("删除会员系统成功").build();
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

    /**
     * 创建会员系统组织关系
     *
     * @param command
     * @return
     */
    @PostMapping("/organization")
    public void createMemberSystemOrganization(
            @Valid @RequestBody MemberSystemAuthorizationCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberSystemOrganizationCommandService.createMemberSystemOrganization(command, null);
    }

    /**
     * 删除会员系统组织关系命令
     *
     * @param organizationId
     * @return
     */
    @DeleteMapping("/organization/{id}")
    public void deleteMemberSystemOrganization(
            @PathVariable(value = "id") Long organizationId
    ) {
        memberSystemOrganizationCommandService.removeMemberSystemOrganization(organizationId);
    }

}