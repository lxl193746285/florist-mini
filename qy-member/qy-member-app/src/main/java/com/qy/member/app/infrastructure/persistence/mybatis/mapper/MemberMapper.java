package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MemberMapper extends BaseMapper<MemberDO> {

    List<MemberBasicDTO> getMembersByAccount(Long accountId);
}
