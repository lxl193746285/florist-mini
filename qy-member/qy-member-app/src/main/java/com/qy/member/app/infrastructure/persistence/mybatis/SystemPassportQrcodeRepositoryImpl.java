package com.qy.member.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.application.repository.AccountDataRepository;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.application.repository.MemberLoginCredentialRepository;
import com.qy.member.app.application.repository.SystemPassportQrcodeRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberLoginCredentialDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.SystemPassportQrcodeDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.MemberAccountMapper;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.SystemPassportQrcodeMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 账号数据资源库实现
 *
 * @author wwd
 */
@Repository
public class SystemPassportQrcodeRepositoryImpl implements SystemPassportQrcodeRepository {

    private SystemPassportQrcodeMapper systemPassportQrcodeMapper;

    public SystemPassportQrcodeRepositoryImpl(SystemPassportQrcodeMapper systemPassportQrcodeMapper) {
        this.systemPassportQrcodeMapper = systemPassportQrcodeMapper;
    }

    @Override
    public SystemPassportQrcodeDO save(SystemPassportQrcodeDO systemPassportQrcodeDO) {
        systemPassportQrcodeDO.setCreateTime(LocalDateTime.now());
        systemPassportQrcodeDO.setExpirationTime(LocalDateTime.now().plusSeconds(300));
        systemPassportQrcodeMapper.insert(systemPassportQrcodeDO);
        return systemPassportQrcodeDO;
    }

    @Override
    public SystemPassportQrcodeDO update(SystemPassportQrcodeDO systemPassportQrcodeDO) {
        LambdaQueryWrapper<SystemPassportQrcodeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemPassportQrcodeDO::getUuid, systemPassportQrcodeDO.getUuid());
        systemPassportQrcodeMapper.update(systemPassportQrcodeDO, wrapper);
        return systemPassportQrcodeDO;
    }

    @Override
    public SystemPassportQrcodeDO findByUuid(String uuid) {
        LambdaQueryWrapper<SystemPassportQrcodeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemPassportQrcodeDO::getUuid, uuid);
        return systemPassportQrcodeMapper.selectOne(wrapper);
    }
}
