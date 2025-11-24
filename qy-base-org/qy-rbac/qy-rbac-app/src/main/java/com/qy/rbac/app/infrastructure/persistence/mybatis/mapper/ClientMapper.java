package com.qy.rbac.app.infrastructure.persistence.mybatis.mapper;

import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户端 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-11-29
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientDO> {

}
