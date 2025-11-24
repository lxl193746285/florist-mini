package com.qy.member.app.application.service;

import com.qy.member.app.application.dto.MemberSystemBasicDTO;
import com.qy.member.app.application.dto.MemberSystemBasicExtendDTO;
import com.qy.member.app.application.dto.MemberSystemDTO;
import com.qy.member.app.application.dto.MemberSystemDetailDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 会员系统查询服务
 *
 * @author legendjw
 */
public interface MemberSystemQueryService {
    /**
     * 查询会员系统
     *
     * @param query
     * @param identity
     * @return
     */
    Page<MemberSystemDTO> getMemberSystems(MemberSystemQuery query, Identity identity);

    /**
     * 根据ID查询会员系统
     *
     * @param id
     * @return
     */
    MemberSystemDTO getMemberSystemById(Long id, Identity identity);

    /**
     * 根据IDs查询会员系统
     *
     * @param ids
     * @return
     */
    List<MemberSystemBasicDTO> getBasicMemberSystemsByIds(List<Long> ids);

    /**
     * 根据ID查询会员系统
     *
     * @param id
     * @return
     */
    MemberSystemDetailDTO getMemberSystemDetailById(Long id, Identity identity);

    /**
     * 根据ID查询基本会员系统
     *
     * @param id
     * @return
     */
    MemberSystemBasicDTO getBasicMemberSystemById(Long id);

    /**
     * 查询组织下的基本会员系统
     *
     * @param organizationId
     * @return
     */
    List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationId(Long organizationId);

    /**
     * 查询组织下的基础会员系统扩展
     *
     * @param organizationId
     * @return
     */
    List<MemberSystemBasicExtendDTO> getBasicMemberSystemsExtendByOrganizationId(Long organizationId);

    /**
     * 查询组织和指定来源下的基本会员系统
     *
     * @param organizationId
     * @return
     */
    List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationIdAndSource(Long organizationId, Integer source);


}