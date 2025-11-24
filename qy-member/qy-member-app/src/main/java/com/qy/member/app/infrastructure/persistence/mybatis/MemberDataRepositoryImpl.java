package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 会员数据资源库
 *
 * @author legendjw
 */
@Repository
public class MemberDataRepositoryImpl implements MemberDataRepository {
    private MemberMapper memberMapper;

    public MemberDataRepositoryImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDO findById(Long id) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getId, id)
                .last(" limit 1");
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public List<MemberDO> findByIds(List<Long> ids) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(MemberDO::getId, ids);
        return memberMapper.selectList(queryWrapper);
    }

    @Override
    public Page<MemberDO> findMemberByQuery(MemberQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(MemberDO::getCreateTime).orderByDesc(MemberDO::getId);

        //if (filterQuery == null) {
        //    queryWrapper.eq(MemberDO::getId, 0);
        //}
        //else {
        //    queryWrapper.in(MemberDO::getOrganizationId, filterQuery.getOrganizationIds());
        //}
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(MemberDO::getName, query.getKeywords()).or().like(MemberDO::getPhone, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(MemberDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getSystemId() != null) {
            queryWrapper.eq(MemberDO::getSystemId, query.getSystemId());
        }
        if (query.getStatusId() != null) {
            queryWrapper.eq(MemberDO::getStatusId, query.getStatusId());
        }
        if (query.getAuditStatusId() != null) {
            queryWrapper.eq(MemberDO::getAuditStatusId, query.getAuditStatusId());
        }
        if (query.getPhone() != null) {
            queryWrapper.eq(MemberDO::getPhone, query.getPhone());
        }
        if (query.getIds() != null) {
            if (query.getIds().isEmpty()) {
                queryWrapper.eq(MemberDO::getId, 0);
            } else {
                queryWrapper.in(MemberDO::getId, query.getIds());
            }
        }
        if (query.getCreateStartTime() != null) {
            queryWrapper.ge(MemberDO::getCreateTime, LocalDateTime.of(LocalDate.parse(query.getCreateStartTime()), LocalTime.MIDNIGHT));
        }
        if (query.getCreateEndTime() != null) {
            queryWrapper.lt(MemberDO::getCreateTime, LocalDateTime.of(LocalDate.parse(query.getCreateEndTime()), LocalTime.MIDNIGHT).plusDays(1));
        }

        IPage<MemberDO> iPage = memberMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public MemberDO findByPhoneSystem(String phone, Long systemId, Long orgId) {
        LambdaQueryWrapper<MemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberDO::getPhone, phone)
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(systemId != null, MemberDO::getSystemId, systemId)
                .eq(orgId != null, MemberDO::getOrganizationId, orgId)
                .last(" limit 1");
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public MemberDO findByAccountSystem(Long accountId, Long systemId, Long orgId) {
        LambdaQueryWrapper<MemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberDO::getAccountId, accountId)
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(systemId != null, MemberDO::getSystemId, systemId)
                .eq(orgId != null, MemberDO::getOrganizationId, orgId)
                .last(" limit 1");
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public List<MemberBasicDTO> getMembersByAccount(Long accountId) {
        return memberMapper.getMembersByAccount(accountId);
    }

    @Override
    public List<MemberDO> getMembersByAccountAndSystemId(Long accountId, Long systemId) {
        LambdaQueryWrapper<MemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberDO::getAccountId, accountId)
                .eq(MemberDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(MemberDO::getSystemId, systemId);
        return memberMapper.selectList(wrapper);
    }
}
