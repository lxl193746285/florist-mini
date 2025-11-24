package com.qy.crm.customer.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 审核日志
 * </p>
 *
 * @author legendjw
 * @since 2021-09-15
 */
@Data
public class CustomerAuditLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联数据id
     */
    private Long dataId;

    /**
     * 审核类型id
     */
    private Integer status;

    /**
     * 审核类型名称
     */
    private String statusName;

    /**
     * 拒绝原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核人id
     */
    private Long auditorId;

    /**
     * 审核人名称
     */
    private String auditorName;

    /**
     * 审核时间
     */
    private String auditTime;
}
