package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.ContactIdDTO;
import com.qy.crm.customer.app.application.command.*;

import java.util.List;

/**
 * 客户联系人命令服务
 *
 * @author legendjw
 */
public interface ContactCommandService {
    /**
     * 创建联系人命令
     *
     * @param command
     * @return
     */
    ContactIdDTO createContact(CreateContactCommand command);

    /**
     * 编辑联系人命令
     *
     * @param command
     */
    void updateContact(UpdateContactCommand command);

    /**
     * 批量保存联系人命令
     *
     * @param command
     * @return
     */
    List<Long> batchSaveContact(BatchSaveContactCommand command);

    /**
     * 删除联系人命令
     *
     * @param command
     */
    void deleteContact(DeleteContactCommand command);

    /**
     * 批量删除联系人命令
     *
     * @param command
     */
    void batchDeleteContact(BatchDeleteContactCommand command);

    /**
     * 删除关联信息的联系人命令
     *
     * @param command
     */
    void deleteRelatedContact(DeleteRelatedDataCommand command);

    /**
     * 设置指定联系人为超管
     *
     * @param command
     */
    void setContactIsSuperAdmin(SetContactIsSuperAdminCommand command);
}
