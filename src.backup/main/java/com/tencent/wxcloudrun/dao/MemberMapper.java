package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员 Mapper 接口
 */
@Mapper
public interface MemberMapper {
    /**
     * 根据用户ID查询会员信息
     */
    Member selectByUserId(@Param("userId") Long userId);

    /**
     * 根据会员编号查询
     */
    Member selectByMemberNo(@Param("memberNo") String memberNo);

    /**
     * 根据微信OpenID查询
     */
    Member selectByOpenid(@Param("openid") String openid);

    /**
     * 插入会员信息
     */
    int insert(Member member);

    /**
     * 更新会员信息
     */
    int update(Member member);

    /**
     * 生成会员编号（获取今日最大编号）
     */
    String generateMemberNo(@Param("prefix") String prefix);
}
