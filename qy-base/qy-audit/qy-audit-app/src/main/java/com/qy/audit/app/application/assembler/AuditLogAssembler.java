package com.qy.audit.app.application.assembler;

import com.qy.audit.app.application.dto.AuditLogDTO;
import com.qy.audit.app.infrastructure.persistence.mybatis.dataobject.AuditLogDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 审核日志汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class AuditLogAssembler {
    @Mapping(source = "auditTime", target = "auditTime", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract AuditLogDTO toDTO(AuditLogDO accountDO);
}