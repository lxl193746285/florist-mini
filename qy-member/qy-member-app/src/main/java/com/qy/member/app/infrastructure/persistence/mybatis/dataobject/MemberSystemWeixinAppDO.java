package com.qy.member.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganization;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员系统微信应用绑定
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_mbr_member_system_weixin_app")
public class MemberSystemWeixinAppDO implements GetOrganization, Serializable {

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
     * 客户端id
     */
    private String clientId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 应用类型id
     */
    private Integer typeId;

    /**
     * 应用类型名称
     */
    private String typeName;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 二维码图片
     */
    private Long qrCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
