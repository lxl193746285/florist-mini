package com.qy.verification.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 验证码
 * </p>
 *
 * @author legendjw
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_verification_code")
public class VerificationCodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 场景
     */
    private String scene;

    /**
     * 接收方消息类型: SMS:短信, EMAIL:邮箱
     */
    private String receiverMessageType;

    /**
     * 接收方地址
     */
    private String receiverAddress;

    /**
     * 验证码
     */
    private String code;

    /**
     * 有效时长，单位分钟
     */
    private Integer validDuration;

    /**
     * 是否已使用: 1:已使用, 0:未使用
     */
    private Byte isUsed;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;

    /**
     * 失效时间
     */
    private LocalDateTime expiredTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}