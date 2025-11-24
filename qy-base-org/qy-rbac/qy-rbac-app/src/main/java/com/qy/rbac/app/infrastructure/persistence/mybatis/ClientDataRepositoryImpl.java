package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.qy.rbac.app.application.query.ClientQuery;
import com.qy.rbac.app.infrastructure.persistence.ClientDataRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.ClientDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.ClientMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.Identity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class ClientDataRepositoryImpl implements ClientDataRepository {
    private ClientMapper clientMapper;

    public ClientDataRepositoryImpl(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public Page<ClientDO> findByQuery(ClientQuery query) {
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .eq(query.getMemberSystemId() != null, ClientDO::getSystemId, query.getMemberSystemId())
                .orderByAsc(ClientDO::getSort)
                .orderByAsc(ClientDO::getCreateTime);
        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(ClientDO::getName, query.getKeywords()));
        }
        IPage<ClientDO> iPage = clientMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<ClientDO> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .in(ClientDO::getId, ids)
                .orderByAsc(ClientDO::getSort)
                .orderByAsc(ClientDO::getCreateTime);
        return clientMapper.selectList(queryWrapper);
    }

    @Override
    public ClientDO findById(Long id) {
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getId, id)
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return clientMapper.selectOne(queryWrapper);
    }

    @Override
    public ClientDO findByClientId(String clientId) {
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getClientId, clientId)
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return clientMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(ClientDO clientDO) {
        if (clientDO.getId() == null) {
            clientMapper.insert(clientDO);
        } else {
            clientMapper.updateById(clientDO);
        }
        return clientDO.getId();
    }

    @Override
    public void remove(Long id, Identity identity) {
        ClientDO clientDO = findById(id);
        clientDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        clientDO.setDeletorId(identity.getId());
        clientDO.setDeletorName(identity.getName());
        clientDO.setDeleteTime(LocalDateTime.now());
        clientMapper.updateById(clientDO);
    }

    @Override
    public int countByName(String name, Long excludeId) {
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getName, name)
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(ClientDO::getId, excludeId);
        }
        return clientMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByClientId(String clientId, Long excludeId) {
        LambdaQueryWrapper<ClientDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ClientDO::getClientId, clientId)
                .eq(ClientDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        if (excludeId != null) {
            queryWrapper.ne(ClientDO::getId, excludeId);
        }
        return clientMapper.selectCount(queryWrapper);
    }
}