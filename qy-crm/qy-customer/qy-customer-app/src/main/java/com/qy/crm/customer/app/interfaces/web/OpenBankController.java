package com.qy.crm.customer.app.interfaces.web;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.OpenBankDTO;
import com.qy.crm.customer.app.application.dto.OpenBankIdDTO;
import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.crm.customer.app.application.service.OpenBankCommandService;
import com.qy.crm.customer.app.application.service.OpenBankQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 开户行
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/crm/customer/open-banks")
public class OpenBankController {
    private OrganizationSessionContext sessionContext;
    private OpenBankCommandService openBankCommandService;
    private OpenBankQueryService openBankQueryService;

    public OpenBankController(OrganizationSessionContext sessionContext, OpenBankCommandService openBankCommandService, OpenBankQueryService openBankQueryService) {
        this.sessionContext = sessionContext;
        this.openBankCommandService = openBankCommandService;
        this.openBankQueryService = openBankQueryService;
    }

    /**
     * 获取开户行列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<OpenBankDTO>> getOpenBanks(OpenBankQuery query) {
        Page<OpenBankDTO> page = openBankQueryService.getOpenBanks(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个开户行
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpenBankDTO> getOpenBank(
        @PathVariable(value = "id") Long id
    ) {
        OpenBankDTO openBankDTO = openBankQueryService.getOpenBankById(id);
        if (openBankDTO == null) {
            throw new NotFoundException("未找到指定的开户行");
        }
        return ResponseUtils.ok().body(openBankDTO);
    }

    /**
     * 创建单个开户行
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<OpenBankIdDTO> createOpenBank(
            @Valid @RequestBody CreateOpenBankCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        OpenBankIdDTO openBankIdDTO = openBankCommandService.createOpenBank(command);

        return ResponseUtils.ok("开户行创建成功").body(openBankIdDTO);
    }

    /**
     * 修改单个开户行
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateOpenBank(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateOpenBankCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);
        OpenBankDTO openBankDTO = getOpenBankById(id);

        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(openBankDTO.getOrganizationId()));
        openBankCommandService.updateOpenBank(command);

        return ResponseUtils.ok("开户行修改成功").build();
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

        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        List<Long> ids = openBankCommandService.batchSaveOpenBank(command);

        return ResponseUtils.ok("开户行创建成功").body(ids);
    }

    /**
     * 删除单个开户行
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOpenBank(
        @PathVariable(value = "id") Long id
    ) {
        OpenBankDTO openBankDTO = getOpenBankById(id);
        DeleteOpenBankCommand command = new DeleteOpenBankCommand();
        command.setId(id);
        command.setIdentity(sessionContext.getEmployee(openBankDTO.getOrganizationId()));
        openBankCommandService.deleteOpenBank(command);

        return ResponseUtils.noContent("删除开户行成功").build();
    }

    /**
     * 删除关联信息的开户行
     *
     * @param command
     */
    @DeleteMapping("/by-related-data")
    public ResponseEntity<Object> deleteByRelatedData(
            DeleteRelatedDataCommand command
    ) {
        command.setIdentity(sessionContext.getEmployee(command.getOrganizationId()));
        openBankCommandService.deleteRelatedOpenBank(command);

        return ResponseUtils.noContent("删除开户行成功").build();
    }

    /**
     * 根据id获取开户行
     *
     * @param id
     * @return
     */
    private OpenBankDTO getOpenBankById(Long id) {
        OpenBankDTO openBankDTO = openBankQueryService.getOpenBankById(id);
        if (openBankDTO == null) {
            throw new NotFoundException("未找到指定的开户行");
        }
        return openBankDTO;
    }
}

