package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
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
public interface OrgDatasourceMapper extends BaseMapper<OrgDatasourceDO> {

}
