package com.qy.customer.api.command;

import com.qy.customer.api.dto.RelatedModuleDataDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量保存联系人命令.
 */
@Data
public class BatchSaveContactCommand {
    private Object identity;
    private Long organizationId;
    private RelatedModuleDataDTO relatedModuleData;
    private List<ContactForm> contacts = new ArrayList<>();
}
