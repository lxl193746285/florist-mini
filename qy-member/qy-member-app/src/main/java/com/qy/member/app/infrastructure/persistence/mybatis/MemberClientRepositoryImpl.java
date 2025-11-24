package com.qy.member.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qy.member.app.application.dto.MemberClientDTO;
import com.qy.member.app.application.repository.MemberClientRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberClientDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberClientMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员客户端资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberClientRepositoryImpl implements MemberClientRepository {
    private MemberClientMapper memberClientMapper;

    public MemberClientRepositoryImpl(MemberClientMapper memberClientMapper) {
        this.memberClientMapper = memberClientMapper;
    }

    @Override
    public List<MemberClientDTO> findMemberClientList(Long memberId) {
        return memberClientMapper.findMemberClientList(memberId);
    }

    @Override
    public MemberClientDTO findMemberClientList(Long memberId, String clientId) {
        return memberClientMapper.findMemberClient(memberId, clientId);
    }

    @Override
    public void saveBatch(List<MemberClientDO> memberClientDOS) {
        LambdaQueryWrapper<MemberClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MemberClientDO::getMemberId, memberClientDOS.stream().map(MemberClientDO::getMemberId).collect(Collectors.toList()));
        memberClientMapper.delete(queryWrapper);
        for (MemberClientDO memberClientDO : memberClientDOS){
            memberClientMapper.insert(memberClientDO);
        }
    }

    @Override
    public void removeMemberClient(Long memberId) {
        LambdaQueryWrapper<MemberClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberClientDO::getMemberId, memberId);
        memberClientMapper.delete(queryWrapper);
    }
}
