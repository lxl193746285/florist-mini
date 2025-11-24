package com.qy.audit.app.infrastructure.persistence.mybatis.mapper;

import com.qy.audit.app.infrastructure.persistence.mybatis.dataobject.AuditLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 审核日志 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-09-15
 */
@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLogDO> {

}
