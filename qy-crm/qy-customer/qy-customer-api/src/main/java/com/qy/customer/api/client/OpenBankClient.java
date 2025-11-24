package com.qy.customer.api.client;

import com.qy.customer.api.command.BatchSaveOpenBankCommand;
import com.qy.customer.api.dto.OpenBankDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 开户行接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-crm-customer", contextId = "qy-crm-customer-open-bank")
public interface OpenBankClient {
    /**
     * 根据关联模块获取开户行
     *
     * @return
     */
    @GetMapping("/v4/api/crm/customer/open-banks/by-related-module")
    List<OpenBankDTO> getOpenBanksByRelatedModule(
            @RequestParam(name = "related_module_id") String relatedModuleId,
            @RequestParam(name = "related_data_id") Long relatedDataId
    );

    /**
     * 批量保存开户行命令
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/crm/customer/open-banks/batch-save")
    List<Long> batchSaveOpenBank(
            @Valid @RequestBody BatchSaveOpenBankCommand command
    );
}
