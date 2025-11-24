package com.qy.member.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.member.app.application.repository.MemberSystemOrganizationDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemOrganizationDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberSystemOrganizationMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员系统组织关系数据资源库实现
 *
 * @author wwd
 */
@Repository
public class MemberSystemOrganizationDataRepositoryImpl implements MemberSystemOrganizationDataRepository {
    private MemberSystemOrganizationMapper memberSystemOrganizationMapper;

    public MemberSystemOrganizationDataRepositoryImpl(MemberSystemOrganizationMapper memberSystemOrganizationMapper) {
        this.memberSystemOrganizationMapper = memberSystemOrganizationMapper;
    }

    @Override
    public void save(MemberSystemOrganizationDO memberSystemOrganizationDO) {
        memberSystemOrganizationDO.setCreateTime(LocalDateTime.now());
        memberSystemOrganizationMapper.insert(memberSystemOrganizationDO);
    }

    @Override
    public List<MemberSystemOrganizationDO> findBySystemId(Long systemId) {
        LambdaQueryWrapper<MemberSystemOrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberSystemOrganizationDO::getSystemId, systemId);
        return memberSystemOrganizationMapper.selectList(wrapper);
    }

    @Override
    public List<MemberSystemOrganizationDO> findByOrganizationId(Long organizationId) {
        LambdaQueryWrapper<MemberSystemOrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberSystemOrganizationDO::getOrganizationId, organizationId);
        return memberSystemOrganizationMapper.selectList(wrapper);
    }

    @Override
    public void removeByOrganizationId(Long organizationId) {
        LambdaQueryWrapper<MemberSystemOrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberSystemOrganizationDO::getOrganizationId, organizationId);
        memberSystemOrganizationMapper.delete(wrapper);
    }

    @Override
    public MemberSystemOrganizationDO findBySystemIdAndOrganizationId(Long systemId, Long organizationId) {
        LambdaQueryWrapper<MemberSystemOrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberSystemOrganizationDO::getSystemId, systemId);
        wrapper.eq(MemberSystemOrganizationDO::getOrganizationId, organizationId);
        return memberSystemOrganizationMapper.selectOne(wrapper);
    }

    @Override
    public List<MemberSystemOrganizationDO> findOrganizationIdAndSource(Long organizationId, Integer source) {
        LambdaQueryWrapper<MemberSystemOrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberSystemOrganizationDO::getOrganizationId, organizationId);
        wrapper.eq(MemberSystemOrganizationDO::getSource, source);
        return memberSystemOrganizationMapper.selectList(wrapper);
    }
}
