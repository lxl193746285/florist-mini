package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 组织员工信息 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Mapper
public interface EmployeeInfoMapper extends BaseMapper<EmployeeInfoDO> {

}
