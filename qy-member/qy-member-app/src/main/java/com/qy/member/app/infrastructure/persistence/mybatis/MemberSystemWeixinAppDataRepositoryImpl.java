package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.query.MemberSystemWeixinAppQuery;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberSystemWeixinAppMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 岗位数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberSystemWeixinAppDataRepositoryImpl implements MemberSystemWeixinAppDataRepository {
    private MemberSystemWeixinAppMapper memberSystemWeixinAppMapper;

    public MemberSystemWeixinAppDataRepositoryImpl(MemberSystemWeixinAppMapper memberSystemWeixinAppMapper) {
        this.memberSystemWeixinAppMapper = memberSystemWeixinAppMapper;
    }

    @Override
    public Page<MemberSystemWeixinAppDO> findByQuery(MemberSystemWeixinAppQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(MemberSystemWeixinAppDO::getCreateTime);

        if (filterQuery == null) {
            queryWrapper.eq(MemberSystemWeixinAppDO::getId, 0);
        }
        else {
            queryWrapper.in(MemberSystemWeixinAppDO::getOrganizationId, filterQuery.getOrganizationIds());
        }

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(MemberSystemWeixinAppDO::getName, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(MemberSystemWeixinAppDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getSystemId() != null) {
            queryWrapper.eq(MemberSystemWeixinAppDO::getSystemId, query.getSystemId());
        }

        IPage<MemberSystemWeixinAppDO> iPage = memberSystemWeixinAppMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<MemberSystemWeixinAppDO> findByIds(List<Long> ids) {
        return memberSystemWeixinAppMapper.selectBatchIds(ids);
    }

    @Override
    public List<MemberSystemWeixinAppDO> findBySystemId(Long systemId) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getSystemId, systemId)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return memberSystemWeixinAppMapper.selectList(queryWrapper);
    }

    @Override
    public MemberSystemWeixinAppDO findById(Long id) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getId, id)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemWeixinAppMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemWeixinAppDO findByClientId(String clientId) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getClientId, clientId)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemWeixinAppMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemWeixinAppDO findBySystemIdAndAppId(Long systemId, String appId) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getAppId, appId)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        if (systemId != null) {
            queryWrapper.eq(MemberSystemWeixinAppDO::getSystemId, systemId);
        }

        return memberSystemWeixinAppMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemWeixinAppDO findBySystemIdAndAppType(Long systemId, Integer appType) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getSystemId, systemId)
                .eq(MemberSystemWeixinAppDO::getTypeId, appType)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemWeixinAppMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemWeixinAppDO findBySystemIdAndAppTypeAnClientId(Long systemId, Integer appType, String clientId) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getSystemId, systemId)
                .eq(MemberSystemWeixinAppDO::getTypeId, appType)
                .eq(MemberSystemWeixinAppDO::getClientId, clientId)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemWeixinAppMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(MemberSystemWeixinAppDO memberSystemWeixinAppDO) {
        if (memberSystemWeixinAppDO.getId() == null) {
            memberSystemWeixinAppMapper.insert(memberSystemWeixinAppDO);
        }
        else {
            memberSystemWeixinAppMapper.updateById(memberSystemWeixinAppDO);
        }
        return memberSystemWeixinAppDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = findById(id);
        if (memberSystemWeixinAppDO == null) { return; }
        memberSystemWeixinAppDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        memberSystemWeixinAppDO.setDeletorId(employee.getId());
        memberSystemWeixinAppDO.setDeletorName(employee.getName());
        memberSystemWeixinAppDO.setDeleteTime(LocalDateTime.now());
        memberSystemWeixinAppMapper.updateById(memberSystemWeixinAppDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId) {
        LambdaQueryWrapper<MemberSystemWeixinAppDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemWeixinAppDO::getOrganizationId, organizationId)
                .eq(MemberSystemWeixinAppDO::getName, name)
                .eq(MemberSystemWeixinAppDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(MemberSystemWeixinAppDO::getId, excludeId);
        }
        return memberSystemWeixinAppMapper.selectCount(queryWrapper);
    }
}
