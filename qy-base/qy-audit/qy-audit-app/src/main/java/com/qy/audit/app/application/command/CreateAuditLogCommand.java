package com.qy.audit.app.application.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建审核日志命令
 *
 * @author legendjw
 * @since 2021-09-15
 */
@Data
public class CreateAuditLogCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联模块id
     */
    private String moduleId;

    /**
     * 关联数据id
     */
    private Long dataId;

    /**
     * 审核类型id
     */
    private Integer typeId;

    /**
     * 审核类型名称
     */
    private String typeName;

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
}
