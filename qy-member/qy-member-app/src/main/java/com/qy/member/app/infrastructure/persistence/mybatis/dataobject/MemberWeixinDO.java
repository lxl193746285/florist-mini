package com.qy.member.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员系统会员微信绑定
 * </p>
 *
 * @author legendjw
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_mbr_member_weixin")
public class MemberWeixinDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * appid
     */
    private String appId;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 微信unionid
     */
    private String unionId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
