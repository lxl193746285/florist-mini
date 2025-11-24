package com.qy.customer.api.client;

import com.qy.customer.api.command.BatchSaveOpenBankCommand;
import com.qy.customer.api.command.CreateOpenBankCommand;
import com.qy.customer.api.command.DeleteRelatedDataCommand;
import com.qy.customer.api.command.UpdateOpenBankCommand;
import com.qy.customer.api.dto.OpenBankDTO;
import com.qy.customer.api.dto.OpenBankIdDTO;
import com.qy.customer.api.query.OpenBankQuery;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 开户行管理接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-open-bank-manage", configuration = FeignTokenRequestInterceptor.class)
public interface OpenBankManageClient {
    /**
     * 获取开户行列表
     *
     * @return
     */
    @GetMapping("/v4/crm/customer/open-banks")
    List<OpenBankDTO> getOpenBanks(@SpringQueryMap OpenBankQuery query);

    /**
     * 获取单个开户行
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/crm/customer/open-banks/{id}")
    OpenBankDTO getOpenBank(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建单个开户行
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/open-banks")
    OpenBankIdDTO createOpenBank(
            @Valid @RequestBody CreateOpenBankCommand command
    );

    /**
     * 修改单个开户行
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/crm/customer/open-banks/{id}")
    void updateOpenBank(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateOpenBankCommand command
    );

    /**
     * 批量保存开户行
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/crm/customer/open-banks/batch-save")
    List<Long> batchSaveOpenBank(
            @Valid @RequestBody BatchSaveOpenBankCommand command
    );

    /**
     * 删除单个开户行
     *
     * @param id
     */
    @DeleteMapping("/v4/crm/customer/open-banks/{id}")
    void deleteOpenBank(
            @PathVariable(value = "id") Long id
    );

    /**
     * 删除关联信息的开户行
     *
     * @param id
     */
    @DeleteMapping("/v4/crm/customer/open-banks/by-related-data")
    public ResponseEntity<Object> deleteByRelatedData(@SpringQueryMap DeleteRelatedDataCommand command);
}
