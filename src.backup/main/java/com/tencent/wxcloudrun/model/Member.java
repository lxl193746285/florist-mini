package com.tencent.wxcloudrun.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员扩展信息实体类
 */
@Data
public class Member {
    /**
     * 会员ID
     */
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 会员编号
     */
    private String memberNo;

    /**
     * 会员等级：1-普通，2-银卡，3-金卡，4-钻石
     */
    private Integer level;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 注册来源：wechat-微信，app-APP，web-网页
     */
    private String source;

    /**
     * 微信OpenID
     */
    private String openid;

    /**
     * 微信UnionID
     */
    private String unionid;

    /**
     * 推荐人ID
     */
    private Long referrerId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
