package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员 Mapper 接口
 */
@Mapper
public interface AdminMapper {
    /**
     * 根据用户ID查询管理员信息
     */
    Admin selectByUserId(@Param("userId") Long userId);

    /**
     * 插入管理员信息
     */
    int insert(Admin admin);

    /**
     * 更新管理员信息
     */
    int update(Admin admin);
}
