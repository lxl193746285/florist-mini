package com.qy.rbac.app.infrastructure.persistence.mybatis.mapper;

import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 权限规则范围 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-18
 */
@Mapper
public interface RuleScopeMapper extends BaseMapper<RuleScopeDO> {
    int update(@Param("id") String id, @Param("ruleScopeDO") RuleScopeDO ruleScopeDO);
}
