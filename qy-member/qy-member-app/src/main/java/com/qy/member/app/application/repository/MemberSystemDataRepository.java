package com.qy.member.app.application.repository;

import com.qy.member.app.application.dto.MemberOrganizationBasicDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;

import java.util.List;

/**
 * 会员系统数据资源库
 *
 * @author legendjw
 */
public interface MemberSystemDataRepository {
    /**
     * 分页查询会员系统
     *
     * @param query
     * @return
     */
    Page<MemberSystemDO> findByQuery(MemberSystemQuery query);

    /**
     * 根据id集合查询会员系统
     *
     * @param ids
     * @return
     */
    List<MemberSystemDO> findByIds(List<Long> ids);

    /**
     * 根据组织ID获取会员系统
     *
     * @param organizationId
     * @return
     */
    List<MemberSystemDO> findByOrganizationId(Long organizationId);

    /**
     * 根据会员ID和会员系统获取已加入的组织
     *
     * @param accountId
     * @return
     */
    List<MemberOrganizationBasicDTO> findByOrganizationsByAccount(Long accountId, Long systemId);

    /**
     * 根据ID查询会员系统
     *
     * @param id
     * @return
     */
    MemberSystemDO findById(Long id);

    /**
     * 根据appId查询会员系统
     *
     * @param appId
     * @return
     */
    MemberSystemDO findByAppId(String appId);

    /**
     * 根据系统ID查询会员系统
     *
     * @param systemId
     * @return
     */
    MemberSystemDO findBySystemId(String systemId);

    /**
     * 查找默认的会员系统
     *
     * @return
     */
    MemberSystemDO findDefaultMemberSystem();

    /**
     * 保存一个会员系统
     *
     * @param memberSystemDO
     * @return
     */
    Long save(MemberSystemDO memberSystemDO);

    /**
     * 移除一个会员系统
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