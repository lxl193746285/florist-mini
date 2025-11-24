package com.qy.rbac.app.infrastructure.persistence.mybatis.mapper;

import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限认证项子父关系 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Mapper
public interface AuthItemChildMapper extends BaseMapper<AuthItemChildDO> {

}
