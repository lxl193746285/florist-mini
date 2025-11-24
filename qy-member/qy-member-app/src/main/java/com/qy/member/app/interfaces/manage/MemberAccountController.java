package com.qy.member.app.interfaces.manage;

import com.qy.member.app.application.command.AssignRoleToAccountCommand;
import com.qy.member.app.application.command.BindChildAccountCommand;
import com.qy.member.app.application.command.CreateChildAccountCommand;
import com.qy.member.app.application.command.ModifyAccountStatusCommand;
import com.qy.member.app.application.dto.AccountBasicDTO;
import com.qy.member.app.application.dto.AccountDTO;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.application.service.AccountCommandService;
import com.qy.member.app.application.service.AccountQueryService;
import com.qy.member.app.application.service.MemberQueryService;
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
 * 会员账号
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/mbr/member/accounts")
public class MemberAccountController {
    private OrganizationSessionContext sessionContext;
    private AccountQueryService accountQueryService;
    private AccountCommandService accountCommandService;
    private MemberQueryService memberQueryService;

    public MemberAccountController(OrganizationSessionContext sessionContext, AccountQueryService accountQueryService, AccountCommandService accountCommandService, MemberQueryService memberQueryService) {
        this.sessionContext = sessionContext;
        this.accountQueryService = accountQueryService;
        this.accountCommandService = accountCommandService;
        this.memberQueryService = memberQueryService;
    }

    /**
     * 获取账号列表
     */
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccount(AccountQuery query) {
        Identity identity = sessionContext.getUser();
        Page<AccountDTO> accountDTOPage = accountQueryService.getAccounts(query, identity);
        return ResponseUtils.ok().body(accountDTOPage.getRecords());
    }

    /**
     * 创建子账号
     *
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/create-child-account")
    public ResponseEntity<Object> createChildAccount(
            @Valid @RequestBody CreateChildAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        MemberBasicDTO memberBasicDTO = findMember(command.getMemberId());

        accountCommandService.createChildAccount(command, sessionContext.getEmployee(memberBasicDTO.getOrganizationId()));

        return ResponseUtils.ok("子账号创建成功").build();
    }

    /**
     * 修改账号的状态
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> modifyAccountStatus(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyAccountStatusCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AccountBasicDTO accountBasicDTO = findAccount(id);

        command.setId(id);
//        accountCommandService.modifyAccountStatus(command, sessionContext.getEmployee(accountBasicDTO.getOrganizationId()));

        return ResponseUtils.ok("会员状态修改成功").build();
    }

    /**
     * 授权权限组给账号
     *
     * @param id
     * @param command
     * @param result
     * @return
     */
    @PostMapping("/{id}/assign-role")
    public ResponseEntity<Object> assignRoleToAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AssignRoleToAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        AccountBasicDTO accountBasicDTO = findAccount(id);

        command.setAccountId(id);
//        accountCommandService.assignRoleToAccount(command, sessionContext.getEmployee(accountBasicDTO.getOrganizationId()));

        return ResponseUtils.ok("授权权限组成功").build();
    }

    /**
     * 查找账号
     *
     * @param id
     * @return
     */
    private AccountBasicDTO findAccount(Long id) {
        AccountBasicDTO accountBasicDTO = accountQueryService.getBasicAccount(id);
        if (accountBasicDTO == null) {
            throw new NotFoundException("未找到指定的会员账号");
        }
        return accountBasicDTO;
    }

    /**
     * 查找会员
     *
     * @param id
     * @return
     */
    private MemberBasicDTO findMember(Long id) {
        MemberBasicDTO memberBasicDTO = memberQueryService.getBasicMember(id);
        if (memberBasicDTO == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        return memberBasicDTO;
    }
}