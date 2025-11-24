package com.qy.audit.app.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 审核日志id
 *
 * @author legendjw
 * @since 2021-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogId implements Serializable {
    /**
     * id
     */
    private Long id;
}
