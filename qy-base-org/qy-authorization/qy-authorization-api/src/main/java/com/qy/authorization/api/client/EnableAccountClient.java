package com.qy.authorization.api.client;

import com.qy.authorization.api.command.CreateEnableAccountCommand;
import com.qy.authorization.api.command.DeleteEnableAccountCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author di
 */
@FeignClient(name = "qy-base-org", contextId = "qy-authorization-enable-account")
public interface EnableAccountClient {

    @PostMapping("/v4/api/authorization/enable-account")
    void createEnableAccount(
            @Valid @RequestBody CreateEnableAccountCommand command
    );

    @PostMapping("/v4/api/authorization/enable-account/delete")
    void deleteEnableAccount(
            @Valid @RequestBody DeleteEnableAccountCommand command
    );

    @GetMapping("/v4/api/authorization/enable-account")
    Integer countByUserOrg(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "account_id") Long accountId,
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "system_id") String systemId
    );
}
