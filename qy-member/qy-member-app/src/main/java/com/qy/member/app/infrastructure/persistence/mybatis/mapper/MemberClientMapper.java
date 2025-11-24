package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.dto.MemberClientDTO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberClientDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员系统会员 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberClientMapper extends BaseMapper<MemberClientDO> {

    List<MemberClientDTO> findMemberClientList(Long memberId);

    MemberClientDTO findMemberClient(@Param("memberId") Long memberId, @Param("clientId") String clientId);
}
