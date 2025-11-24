package com.qy.rbac.api.client;

import com.qy.rbac.api.command.*;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rbac.api.dto.RuleScopeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base-org", contextId = "qy-rbac-rule-scope")
public interface RuleScopeClient {
    /**
     * 获取单个规则范围
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/rbac/rule-scopes/{id}")
    RuleScopeDTO getRuleScope(
            @PathVariable(value = "id") String id
    );
}
