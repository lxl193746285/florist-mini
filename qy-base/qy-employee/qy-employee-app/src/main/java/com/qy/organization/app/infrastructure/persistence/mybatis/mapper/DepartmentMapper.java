package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.organization.app.application.dto.DepartmentDTO;
import com.qy.organization.app.application.dto.RbacAuthItemChildDTO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.DepartmentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织部门 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {
    Long getTopDepartmentId(Long organizationId);

}
