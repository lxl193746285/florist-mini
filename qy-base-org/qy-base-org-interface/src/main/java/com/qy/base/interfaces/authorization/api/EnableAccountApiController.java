package com.qy.base.interfaces.authorization.api;

import com.qy.authorization.app.application.command.CreateEnableAccountCommand;
import com.qy.authorization.app.application.command.DeleteEnableAccountCommand;
import com.qy.authorization.app.application.command.QueryEnableAccountCommand;
import com.qy.authorization.app.application.service.EnableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author di
 */
@RestController
@RequestMapping("/v4/api/authorization/enable-account")
public class EnableAccountApiController {

    @Autowired
    private EnableAccountService enableAccountService;

    @PostMapping
    public void createEnableAccount(
            @RequestBody CreateEnableAccountCommand command
            ){
        enableAccountService.createEnableAccount(command);
    }

    @PostMapping("/delete")
    public void createEnableAccount(
            @RequestBody DeleteEnableAccountCommand command
            ){
        enableAccountService.deleteEnableAccount(command);
    }

    @GetMapping
    public Integer countByUserOrg(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "account_id") Long accountId,
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "system_id") String systemId
    ){
        QueryEnableAccountCommand command = new QueryEnableAccountCommand();
        command.setAccountId(accountId);
        command.setOrganizationId(organizationId);
        command.setSystemId(systemId);
        command.setUserId(userId);
        return enableAccountService.countByUserOrg(command);
    }

}
