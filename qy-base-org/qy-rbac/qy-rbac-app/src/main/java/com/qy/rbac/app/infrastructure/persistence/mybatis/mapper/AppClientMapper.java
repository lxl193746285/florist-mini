package com.qy.rbac.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AppDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 应用 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Mapper
public interface AppClientMapper extends BaseMapper<AppClientDO> {

}
