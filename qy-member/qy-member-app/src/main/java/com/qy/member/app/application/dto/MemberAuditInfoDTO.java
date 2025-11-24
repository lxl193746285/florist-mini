package com.qy.member.app.application.dto;

import lombok.Data;

import java.util.List;

/**
 * 会员审核信息
 *
 * @author legendjw
 */
@Data
public class MemberAuditInfoDTO {
    /**
     * 审核状态id 0: 待审核 1: 门店审核通过 2: 门店审核不通过 3: 平台审核通过 4：平台审核不通过
     */
    private Integer auditStatusId;

    /**
     * 审核状态名称
     */
    private String auditStatusName;

    /**
     * 审核时间
     */
    private String auditTimeName;

    /**
     * 审核拒绝原因
     */
    private String auditReason;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 审核日志
     */
    private List<AuditBasicInfoDTO> auditLogs;
}
