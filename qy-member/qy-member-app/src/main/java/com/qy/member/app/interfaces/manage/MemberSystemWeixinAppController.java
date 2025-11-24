package com.qy.member.app.interfaces.manage;

import com.qy.member.app.application.command.CreateMemberSystemWeixinAppCommand;
import com.qy.member.app.application.command.UpdateMemberSystemWeixinAppCommand;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.query.MemberSystemWeixinAppQuery;
import com.qy.member.app.application.service.MemberSystemWeixinAppCommandService;
import com.qy.member.app.application.service.MemberSystemWeixinAppQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
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
 * 会员系统微信应用
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/mbr/member-system-weixin-apps")
public class MemberSystemWeixinAppController {
    private OrganizationSessionContext sessionContext;
    private MemberSystemWeixinAppCommandService memberSystemWeixinAppCommandService;
    private MemberSystemWeixinAppQueryService memberSystemWeixinAppQueryService;

    public MemberSystemWeixinAppController(OrganizationSessionContext sessionContext, MemberSystemWeixinAppCommandService memberSystemWeixinAppCommandService, MemberSystemWeixinAppQueryService memberSystemWeixinAppQueryService) {
        this.sessionContext = sessionContext;
        this.memberSystemWeixinAppCommandService = memberSystemWeixinAppCommandService;
        this.memberSystemWeixinAppQueryService = memberSystemWeixinAppQueryService;
    }

    /**
     * 获取会员系统微信应用列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MemberSystemWeixinAppDTO>> getMemberSystemWeixinApps(MemberSystemWeixinAppQuery query) {
        Page<MemberSystemWeixinAppDTO> page = memberSystemWeixinAppQueryService.getMemberSystemWeixinApps(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个会员系统微信应用
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberSystemWeixinAppDTO> getMemberSystemWeixinApp(
        @PathVariable(value = "id") Long id
    ) {
        MemberSystemWeixinAppDTO memberSystemWeixinAppDTO = findMemberSystemWeixinApp(id, sessionContext.getUser());
        return ResponseUtils.ok().body(memberSystemWeixinAppDTO);
    }

    /**
     * 创建单个会员系统微信应用
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createMemberSystemWeixinApp(
            @Valid @RequestBody CreateMemberSystemWeixinAppCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        memberSystemWeixinAppCommandService.createMemberSystemWeixinApp(command, sessionContext.getEmployee(command.getOrganizationId()));

        return ResponseUtils.ok("会员系统微信应用创建成功").build();
    }

    /**
     * 修改单个会员系统微信应用
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateMemberSystemWeixinApp(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateMemberSystemWeixinAppCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        memberSystemWeixinAppCommandService.updateMemberSystemWeixinApp(command, sessionContext.getEmployee(command.getOrganizationId()));

        return ResponseUtils.ok("会员系统微信应用修改成功").build();
    }

    /**
     * 删除单个会员系统微信应用
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMemberSystemWeixinApp(
        @PathVariable(value = "id") Long id
    ) {
        MemberSystemWeixinAppDTO memberSystemWeixinAppDTO = findMemberSystemWeixinApp(id, null);

        memberSystemWeixinAppCommandService.deleteMemberSystemWeixinApp(id, sessionContext.getEmployee(memberSystemWeixinAppDTO.getOrganizationId()));

        return ResponseUtils.noContent("删除会员系统微信应用成功").build();
    }

    /**
     * 查找会员系统微信应用
     *
     * @param id
     * @return
     */
    private MemberSystemWeixinAppDTO findMemberSystemWeixinApp(Long id, Identity identity) {
        MemberSystemWeixinAppDTO memberSystemWeixinAppDTO = memberSystemWeixinAppQueryService.getMemberSystemWeixinAppById(id, identity);
        if (memberSystemWeixinAppDTO == null) {
            throw new NotFoundException("未找到指定的会员系统微信应用");
        }
        return memberSystemWeixinAppDTO;
    }
}

