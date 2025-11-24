package com.qy.crm.customer.app.interfaces.api;

import com.qy.crm.customer.app.application.command.BatchSaveOpenBankCommand;
import com.qy.crm.customer.app.application.dto.OpenBankDTO;
import com.qy.crm.customer.app.application.service.OpenBankCommandService;
import com.qy.crm.customer.app.application.service.OpenBankQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 开户行内部接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/crm/customer/open-banks")
public class OpenBankApiController {
    private OrganizationSessionContext sessionContext;
    private OpenBankCommandService openBankCommandService;
    private OpenBankQueryService openBankQueryService;

    public OpenBankApiController(OrganizationSessionContext sessionContext, OpenBankCommandService openBankCommandService, OpenBankQueryService openBankQueryService) {
        this.sessionContext = sessionContext;
        this.openBankCommandService = openBankCommandService;
        this.openBankQueryService = openBankQueryService;
    }

    /**
     * 根据关联模块获取开户行
     *
     * @return
     */
    @GetMapping("/by-related-module")
    public ResponseEntity<List<OpenBankDTO>> getOpenBanksByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    ) {
        return ResponseUtils.ok().body(openBankQueryService.getOpenBanksByRelatedModule(relatedModuleId, relatedDataId));
    }

    /**
     * 批量保存开户行命令
     *
     * @param command
     * @return
     */
    @PostMapping("/batch-save")
    public ResponseEntity<List<Long>> batchSaveOpenBank(
            @Valid @RequestBody BatchSaveOpenBankCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        List<Long> ids = openBankCommandService.batchSaveOpenBank(command);

        return ResponseUtils.ok("开户行创建成功").body(ids);
    }
}

