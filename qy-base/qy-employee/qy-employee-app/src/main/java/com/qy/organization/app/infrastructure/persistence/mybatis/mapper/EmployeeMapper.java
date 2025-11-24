package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.organization.app.application.dto.EmployeeMemberDTO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织员工 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

    List<EmployeeMemberDTO> getEmployeeMember(@Param("ids") List<Long> employeeIds);
}
