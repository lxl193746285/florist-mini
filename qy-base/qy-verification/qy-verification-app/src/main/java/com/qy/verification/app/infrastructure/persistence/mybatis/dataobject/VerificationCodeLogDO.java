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
 * 验证码发送日志
 * </p>
 *
 * @author legendjw
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_verification_code_log")
public class VerificationCodeLogDO implements Serializable {

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
     * 结果: 1:成功 0:失败
     */
    private Byte result;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}