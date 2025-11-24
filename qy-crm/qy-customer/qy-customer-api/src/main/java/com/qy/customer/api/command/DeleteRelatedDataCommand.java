package com.qy.customer.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除关联信息的数据命令
 *
 * @author legendjw
 */
@Data
public class DeleteRelatedDataCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联模块id
     */
    private String relatedModuleId;

    /**
     * 关联数据id
     */
    private Long relatedDataId;
}