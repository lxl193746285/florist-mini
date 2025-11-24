package com.qy.member.app.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.member.app.application.dto.MemberOrganizationBasicDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 会员系统 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Mapper
public interface MemberSystemMapper extends BaseMapper<MemberSystemDO> {

    List<MemberOrganizationBasicDTO> selectByAccountId(Long accountId, Long systemId);

    IPage<MemberSystemDO> selectByQuery(IPage page, MemberSystemQuery query);

}
