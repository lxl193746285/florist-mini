package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.repository.MemberWeixinDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberWeixinMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

/**
 * 会员微信绑定数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberWeixinDataRepositoryImpl implements MemberWeixinDataRepository {
    private MemberWeixinMapper memberWeixinMapper;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;

    public MemberWeixinDataRepositoryImpl(MemberWeixinMapper memberWeixinMapper, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository) {
        this.memberWeixinMapper = memberWeixinMapper;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
    }

    @Override
    public MemberWeixinDO findByMemberSystemAndAppTypeAndAccountId(Long systemId, Integer appType, Long accountId) {
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppType(systemId, appType);
        if (memberSystemWeixinAppDO == null) { return null; }

        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getAppId, memberSystemWeixinAppDO.getAppId())
                .eq(MemberWeixinDO::getAccountId, accountId)
                .last("limit 1");
        return memberWeixinMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberWeixinDO findByMemberSystemAndOpenId(Long systemId, String openid) {
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getSystemId, systemId)
                .eq(MemberWeixinDO::getOpenId, openid)
                .last("limit 1");
        return memberWeixinMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberWeixinDO findByMemberSystemAndOpenIdAndOrgId(Long organizationId, Long systemId, String openid) {
        LambdaQueryWrapper<MemberWeixinDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberWeixinDO::getSystemId, systemId)
                .eq(MemberWeixinDO::getOpenId, openid)
                .eq(MemberWeixinDO::getOrganizationId, organizationId)
                .last("limit 1");
        return memberWeixinMapper.selectOne(queryWrapper);
    }
}
