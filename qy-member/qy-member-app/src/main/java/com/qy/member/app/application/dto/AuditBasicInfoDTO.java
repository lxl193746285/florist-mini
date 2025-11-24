package com.qy.member.app.application.dto;

import lombok.Data;

/**
 * 审核基本信息
 *
 * @author legendjw
 */
@Data
public class AuditBasicInfoDTO {
    /**
     * 审核类型id 0: 提交审核 1：通过 2：不通过
     */
    private Integer typeId;

    /**
     * 审核类型名称
     */
    private String typeName;

    /**
     * 审核时间
     */
    private String auditTimeName;
}
