package com.qy.customer.api.client;

import com.qy.customer.api.command.BatchSaveContactCommand;
import com.qy.customer.api.dto.ContactDTO;

import java.util.Collections;
import java.util.List;

/**
 * 联系人客户端占位接口.
 */
public interface ContactClient {

    /**
     * 根据关联模块获取联系人列表.
     */
    default List<ContactDTO> getContactsByRelatedModule(Integer relatedModuleId, Long relatedDataId) {
        return Collections.emptyList();
    }

    /**
     * 批量保存联系人
     */
    default void batchSaveContact(BatchSaveContactCommand command) {
        // no-op placeholder
    }
}
