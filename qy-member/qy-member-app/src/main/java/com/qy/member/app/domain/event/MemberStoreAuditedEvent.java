package com.qy.member.app.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 会员门店已审核事件
 *
 * @author legendjw
 */
@Getter
public class MemberStoreAuditedEvent extends ApplicationEvent {
    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 审核层级
     */
    private Integer auditLevel = 2;

    /**
     * 审核类型id: 1: 通过 2：拒绝
     */
    private Integer statusId;

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
     * 审核人姓名
     */
    private String auditorName;

    public MemberStoreAuditedEvent(Object source, Long memberId, Integer auditLevel, Integer statusId, String reason, String remark, Long auditorId, String auditorName) {
        super(source);
        this.memberId = memberId;
        this.auditLevel = auditLevel;
        this.statusId = statusId;
        this.reason = reason;
        this.remark = remark;
        this.auditorId = auditorId;
        this.auditorName = auditorName;
    }
}