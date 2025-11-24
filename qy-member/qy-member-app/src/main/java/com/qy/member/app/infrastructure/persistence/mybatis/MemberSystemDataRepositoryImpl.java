package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.dto.MemberOrganizationBasicDTO;
import com.qy.member.app.application.query.MemberSystemQuery;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberSystemMapper;
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
 * 会员系统数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class MemberSystemDataRepositoryImpl implements MemberSystemDataRepository {
    private MemberSystemMapper memberSystemMapper;

    public MemberSystemDataRepositoryImpl(MemberSystemMapper memberSystemMapper) {
        this.memberSystemMapper = memberSystemMapper;
    }

    @Override
    public Page<MemberSystemDO> findByQuery(MemberSystemQuery query) {
//        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
//                .orderByDesc(MemberSystemDO::getCreateTime);
//
//        if (filterQuery == null) {
//            queryWrapper.eq(MemberSystemDO::getId, 0);
//        }
//        else {
//            queryWrapper.in(MemberSystemDO::getOrganizationId, filterQuery.getOrganizationIds());
//        }
//
//        if (StringUtils.isNotBlank(query.getKeywords())) {
//            queryWrapper.and(i -> i.like(MemberSystemDO::getName, query.getKeywords()));
//        }
//        if (query.getOrganizationId() != null) {
//            queryWrapper.eq(MemberSystemDO::getOrganizationId, query.getOrganizationId());
//        }
//        if (query.getStatusId() != null) {
//            queryWrapper.eq(MemberSystemDO::getStatusId, query.getStatusId());
//        }
//        IPage<MemberSystemDO> iPage = memberSystemMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);

        IPage<MemberSystemDO> iPage = memberSystemMapper.selectByQuery(new PageDTO<>(query.getPage(), query.getPerPage()), query);

        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<MemberSystemDO> findByIds(List<Long> ids) {
        return memberSystemMapper.selectBatchIds(ids);
    }

    @Override
    public List<MemberSystemDO> findByOrganizationId(Long organizationId) {
        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemDO::getOrganizationId, organizationId)
                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        return memberSystemMapper.selectList(queryWrapper);
    }

    @Override
    public List<MemberOrganizationBasicDTO> findByOrganizationsByAccount(Long accountId, Long systemId) {
        return memberSystemMapper.selectByAccountId(accountId, systemId);
    }

    @Override
    public MemberSystemDO findById(Long id) {
        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemDO::getId, id)
                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemDO findByAppId(String appId) {
        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberSystemDO findBySystemId(String systemId) {
        return findById(Long.valueOf(systemId));
    }

    @Override
    public MemberSystemDO findDefaultMemberSystem() {
        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemDO::getIsDefault, 1)
                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return memberSystemMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(MemberSystemDO memberSystemDO) {
        if (memberSystemDO.getId() == null) {
            memberSystemMapper.insert(memberSystemDO);
        }
        else {
            memberSystemMapper.updateById(memberSystemDO);
        }
        return memberSystemDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity employee) {
        MemberSystemDO memberSystemDO = findById(id);
        if (memberSystemDO == null) { return; }
        memberSystemDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        memberSystemDO.setDeletorId(employee != null ? employee.getId() : 0L);
        memberSystemDO.setDeletorName(employee != null ? employee.getName() : "");
        memberSystemDO.setDeleteTime(LocalDateTime.now());
        memberSystemMapper.updateById(memberSystemDO);
    }

    @Override
    public int countByOrganizationIdAndName(Long organizationId, String name, Long excludeId) {
        LambdaQueryWrapper<MemberSystemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(MemberSystemDO::getOrganizationId, organizationId)
                .eq(MemberSystemDO::getName, name)
                .eq(MemberSystemDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(MemberSystemDO::getId, excludeId);
        }
        return memberSystemMapper.selectCount(queryWrapper);
    }
}
