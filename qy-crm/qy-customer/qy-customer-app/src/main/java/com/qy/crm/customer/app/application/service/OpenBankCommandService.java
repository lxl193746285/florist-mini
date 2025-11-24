package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.OpenBankIdDTO;
import com.qy.crm.customer.app.application.command.*;

import java.util.List;

/**
 * 开户行命令服务
 *
 * @author legendjw
 */
public interface OpenBankCommandService {
    /**
     * 创建开户行命令
     *
     * @param command
     * @return
     */
    OpenBankIdDTO createOpenBank(CreateOpenBankCommand command);

    /**
     * 编辑开户行命令
     *
     * @param command
     */
    void updateOpenBank(UpdateOpenBankCommand command);

    /**
     * 批量保存开户行命令
     *
     * @param command
     * @return
     */
    List<Long> batchSaveOpenBank(BatchSaveOpenBankCommand command);

    /**
     * 删除开户行命令
     *
     * @param command
     */
    void deleteOpenBank(DeleteOpenBankCommand command);

    /**
     * 删除关联信息的开户行命令
     *
     * @param command
     */
    void deleteRelatedOpenBank(DeleteRelatedDataCommand command);

    /**
     * 批量删除开户行命令
     *
     * @param command
     */
    void batchDeleteOpenBank(BatchDeleteContactCommand command);
}
