package com.qy.customer.api.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 设置指定联系人为超管
 *
 * @author legendjw
 */
@Data
public class SetContactIsSuperAdminCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联模块id
     */
    private String relatedModuleId;

    /**
     * 关联数据id
     */
    private Long relatedDataId;

    /**
     * 联系人id
     */
    private Long id;
}