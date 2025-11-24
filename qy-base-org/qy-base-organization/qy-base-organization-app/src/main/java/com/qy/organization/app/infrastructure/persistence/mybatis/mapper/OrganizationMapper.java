package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 组织 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-22
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<OrganizationDO> {

}
