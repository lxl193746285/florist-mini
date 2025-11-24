package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.application.repository.AccountDataRepository;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.application.repository.MemberLoginCredentialRepository;
import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberLoginCredentialDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberAccountMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账号数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class AccountDataRepositoryImpl implements AccountDataRepository {
    private MemberAccountMapper memberAccountMapper;
    private MemberDataRepository memberDataRepository;
    private MemberLoginCredentialRepository memberLoginCredentialRepository;

    public AccountDataRepositoryImpl(MemberAccountMapper memberAccountMapper, MemberDataRepository memberDataRepository,
                                     MemberLoginCredentialRepository memberLoginCredentialRepository) {
        this.memberAccountMapper = memberAccountMapper;
        this.memberDataRepository = memberDataRepository;
        this.memberLoginCredentialRepository = memberLoginCredentialRepository;
    }

    @Override
    public MemberAccountDO findById(Long id) {
        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getId, id)
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberAccountMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberAccountDO findMemberPrimaryAccount(Long memberId) {
        MemberDO memberDO = memberDataRepository.findById(memberId);
        if (memberDO == null) { return null; }

        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberAccountDO::getId, memberDO.getAccountId())
                .last("limit 1");
        return memberAccountMapper.selectOne(queryWrapper);
    }

    @Override
    public List<MemberAccountDO> findMemberChildAccounts(Long memberId) {
        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return memberAccountMapper.selectList(queryWrapper);
    }

    @Override
    public Page<MemberAccountDO> findByQuery(AccountQuery query) {
        LambdaQueryWrapper<MemberAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberAccountDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(MemberAccountDO::getCreateTime);

//        if (StringUtils.isNotBlank(query.getKeywords())) {
//            queryWrapper.and(i -> i.like(MemberAccountDO::getName, query.getKeywords()).or()
//                    .like(MemberAccountDO::getPhone, query.getKeywords()));
//        }
        if (query.getSystemId() != null) {
            queryWrapper.eq(MemberAccountDO::getSystemId, query.getSystemId());
        }

        IPage<MemberAccountDO> iPage = memberAccountMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public MemberAccountDO findByPhone(String phone) {
        MemberLoginCredentialDO memberLoginCredentialDO = memberLoginCredentialRepository.findByCredential(phone);
        if (memberLoginCredentialDO == null){
            return null;
        }
        return memberAccountMapper.selectById(memberLoginCredentialDO.getAccountId());
    }
}
