package com.qy.member.app.application.repository;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;

import java.util.List;

/**
 * 会员系统组织关系数据资源库
 *
 * @author wwd
 */
public interface MemberSystemOrganizationDataRepository {
    /**
     * 保存一个会员系统组织关系
     *
     * @param memberSystemOrganizationDO
     * @return
     */
    void save(MemberSystemOrganizationDO memberSystemOrganizationDO);

    /**
     * 根据会员系统查询会员系统组织关系
     *
     * @param systemId
     */
    List<MemberSystemOrganizationDO> findBySystemId(Long systemId);

    /**
     * 根据组织查询会员系统组织关系
     *
     * @param organizationId
     */
    List<MemberSystemOrganizationDO> findByOrganizationId(Long organizationId);

    /**
     * 根据组织移除会员系统组织关系
     *
     * @param organizationId
     */
    void removeByOrganizationId(Long organizationId);

    /**
     * 根据会员系统id和组织id查询会员系统组织关系
     *
     * @param systemId
     * @param organizationId
     */
    MemberSystemOrganizationDO findBySystemIdAndOrganizationId(Long systemId, Long organizationId);

    /**
     * 根据组织id和来源查询会员系统组织关系
     *
     * @param source
     * @param organizationId
     */
    List<MemberSystemOrganizationDO> findOrganizationIdAndSource(Long organizationId, Integer source);

}