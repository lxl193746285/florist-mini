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
 * 系统二维码
 * </p>
 *
 * @author wwd
 * @since 2024-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_passport_qrcode")
public class SystemPassportQrcodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一识别key
     */
    private String uuid;

    /**
     * 二维码类别
     */
    private String linkType;

    /**
     * 状态 0.未使用,1.允许 2 拒绝
     */
    private Integer status;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * token
     */
    private String accessToken;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;


}
