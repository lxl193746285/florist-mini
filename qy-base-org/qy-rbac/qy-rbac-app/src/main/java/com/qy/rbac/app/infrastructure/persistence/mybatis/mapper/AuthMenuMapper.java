package com.qy.rbac.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthMenuDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Mapper
public interface AuthMenuMapper extends BaseMapper<AuthMenuDO> {

}
