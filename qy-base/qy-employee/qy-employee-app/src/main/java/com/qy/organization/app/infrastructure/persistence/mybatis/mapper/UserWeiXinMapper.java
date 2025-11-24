package com.qy.organization.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.api.dto.MemberDTO;
import com.qy.organization.app.application.dto.EmployeeBasicDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.organization.app.domain.entity.UserDetailDTO;
import com.qy.organization.app.domain.entity.UserWeiXin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWeiXinMapper extends BaseMapper<UserWeiXin> {
    UserDetailDTO getUserDetailByPhone(String username);

    UserDetailDTO getUserDetailById(Long id);

    MemberDTO getMemberByAccountId(Long accountId);

    EmployeeBasicDTO getEmployeeInfo(Long userId);
}
