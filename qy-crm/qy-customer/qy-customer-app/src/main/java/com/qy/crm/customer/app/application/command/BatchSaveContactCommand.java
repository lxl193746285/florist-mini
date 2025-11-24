package com.qy.crm.customer.app.application.command;

import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量保存客户联系人命令
 *
 * @author legendjw
 */
@Data
public class BatchSaveContactCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联信息
     */
    private RelatedModuleDataDTO relatedModuleData;

    /**
     * 联系人
     */
    private List<ContactForm> contacts = new ArrayList<>();
}