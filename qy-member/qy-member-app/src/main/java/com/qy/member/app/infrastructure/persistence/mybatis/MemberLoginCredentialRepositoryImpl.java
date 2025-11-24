package com.qy.member.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.application.repository.MemberLoginCredentialRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberLoginCredentialDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberAccountMapper;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberLoginCredentialMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberLoginCredentialRepositoryImpl implements MemberLoginCredentialRepository {
    private MemberLoginCredentialMapper memberLoginCredentialMapper;

    public MemberLoginCredentialRepositoryImpl(MemberLoginCredentialMapper memberLoginCredentialMapper) {
        this.memberLoginCredentialMapper = memberLoginCredentialMapper;
    }

    @Override
    public MemberLoginCredentialDO findByAccountAndCredential(Long accountId, String credentialValue) {
        LambdaQueryWrapper<MemberLoginCredentialDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberLoginCredentialDO::getAccountId, accountId)
                .eq(MemberLoginCredentialDO::getCredentialValue, credentialValue)
                .last(" limit 1");
        return memberLoginCredentialMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberLoginCredentialDO findByCredential(String credentialValue) {
        LambdaQueryWrapper<MemberLoginCredentialDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberLoginCredentialDO::getCredentialValue, credentialValue)
                .last(" limit 1");
        return memberLoginCredentialMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveData(MemberLoginCredentialDO memberLoginCredentialDO) {
        removeByAccountId(memberLoginCredentialDO.getAccountId());
        memberLoginCredentialMapper.insert(memberLoginCredentialDO);
    }

    @Override
    public void removeByAccountId(Long accountId) {
        memberLoginCredentialMapper.delete(new LambdaQueryWrapper<MemberLoginCredentialDO>().eq(MemberLoginCredentialDO::getAccountId, accountId));
    }
}
