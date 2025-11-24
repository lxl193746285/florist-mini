package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeJobDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 组织用户岗位关联 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Mapper
public interface EmployeeJobMapper extends BaseMapper<EmployeeJobDO> {

}
