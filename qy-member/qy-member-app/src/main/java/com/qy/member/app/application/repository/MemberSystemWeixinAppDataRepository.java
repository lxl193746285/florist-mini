package com.qy.member.app.application.repository;

import com.qy.member.app.application.query.MemberSystemWeixinAppQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 会员系统微信应用数据资源库
 *
 * @author legendjw
 */
public interface MemberSystemWeixinAppDataRepository {
    /**
     * 分页查询会员系统微信应用
     *
     * @param query
     * @param filterQuery
     * @return
     */
    Page<MemberSystemWeixinAppDO> findByQuery(MemberSystemWeixinAppQuery query, MultiOrganizationFilterQuery filterQuery);

    /**
     * 根据id集合查询会员系统微信应用
     *
     * @param ids
     * @return
     */
    List<MemberSystemWeixinAppDO> findByIds(List<Long> ids);

    /**
     * 根据会员系统查询会员系统微信应用
     *
     * @param systemId
     * @return
     */
    List<MemberSystemWeixinAppDO> findBySystemId(Long systemId);

    /**
     * 根据ID查询会员系统微信应用
     *
     * @param id
     * @return
     */
    MemberSystemWeixinAppDO findById(Long id);

    /**
     * 根据客户端ID查询会员系统微信应用
     *
     * @param clientId
     * @return
     */
    MemberSystemWeixinAppDO findByClientId(String clientId);

    /**
     * 根据会员系统id以及应用id查询会员系统微信应用
     *
     * @param systemId
     * @param appId
     * @return
     */
    MemberSystemWeixinAppDO findBySystemIdAndAppId(Long systemId, String appId);

    /**
     * 根据会员系统id以及应用类型查询会员系统微信应用
     *
     * @param systemId
     * @param appType
     * @return
     */
    MemberSystemWeixinAppDO findBySystemIdAndAppType(Long systemId, Integer appType);

    /**
     * 根据会员系统id以及应用类型查询会员系统微信应用
     *
     * @param systemId
     * @param appType
     * @return
     */
    MemberSystemWeixinAppDO findBySystemIdAndAppTypeAnClientId(Long systemId, Integer appType, String clientId);

    /**
     * 保存一个会员系统微信应用
     *
     * @param memberSystemWeixinAppDO
     * @return
     */
    Long save(MemberSystemWeixinAppDO memberSystemWeixinAppDO);

    /**
     * 移除一个会员系统微信应用
     *
     * @param id
     * @param employee
     */
    void remove(Long id, EmployeeIdentity employee);

    /**
     * 查找指定组织指定名称的数量
     *
     * @param organizationId
     * @param name
     * @param excludeId
     * @return
     */
    int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId);

}