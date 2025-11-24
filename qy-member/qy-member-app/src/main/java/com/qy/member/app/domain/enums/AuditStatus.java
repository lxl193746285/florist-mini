package com.qy.member.app.domain.enums;

import com.qy.codetable.api.client.CodeTableClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * 审核类型
 *
 * @author legendjw
 */
public enum AuditStatus {
    WAITING_AUDIT(0, "待审核"),
    STORE_PASSED(1, "商城审核通过"),
    STORE_REFUSED(2, "商城审核不通过"),
    PLATFORM_PASSED(3, "平台审核通过"),
    PLATFORM_REFUSED(4, "平台审核不通过");

    private Integer id;
    private String name;
    private CodeTableClient codeTableClient;

    public static AuditStatus getById(Integer id) {
        return Arrays.stream(AuditStatus.values()).filter(a -> a.id.equals(id)).findFirst().orElse(null);
    }

    AuditStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return codeTableClient.getSystemCodeTableItemName("member_audit_status", this.getId().toString());
    }

    @Component
    public static class EnumInjector {
        @Autowired
        private CodeTableClient codeTableClient;

        @PostConstruct
        public void postConstruct() {
            for (AuditStatus auditStatus : EnumSet.allOf(AuditStatus.class)) {
                auditStatus.codeTableClient = codeTableClient;
            }
        }
    }
}
