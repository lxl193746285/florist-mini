package com.qy.member.app.infrastructure.persistence.mybatis;

import com.qy.member.app.application.repository.WeixinSessionDataRepository;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.WeixinSessionDO;
import com.qy.member.app.infrastructure.persistence.mybatis.mapper.WeixinSessionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 微信会话数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class WeixinSessionDataRepositoryImpl implements WeixinSessionDataRepository {
    private WeixinSessionMapper weixinSessionMapper;

    public WeixinSessionDataRepositoryImpl(WeixinSessionMapper weixinSessionMapper) {
        this.weixinSessionMapper = weixinSessionMapper;
    }

    @Override
    public WeixinSessionDO findByWeixinToken(String weixinToken) {
        LambdaQueryWrapper<WeixinSessionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(WeixinSessionDO::getCreateTime)
                .eq(WeixinSessionDO::getWeixinToken, weixinToken)
                .last("limit 1");
        return weixinSessionMapper.selectOne(queryWrapper);
    }

    @Override
    public WeixinSessionDO findById(Long id) {
        LambdaQueryWrapper<WeixinSessionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(WeixinSessionDO::getId, id)
                .last("limit 1");
        return weixinSessionMapper.selectOne(queryWrapper);
    }

    @Override
    public Long saveWeixinSession(WeixinSessionDO weixinSessionDO) {
        weixinSessionDO.setCreateTime(LocalDateTime.now());
        weixinSessionMapper.insert(weixinSessionDO);
        return weixinSessionDO.getId();
    }
}
