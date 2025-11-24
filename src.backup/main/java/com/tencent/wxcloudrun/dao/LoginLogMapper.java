package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志 Mapper 接口
 */
@Mapper
public interface LoginLogMapper {
    /**
     * 插入登录日志
     */
    int insert(LoginLog loginLog);
}
