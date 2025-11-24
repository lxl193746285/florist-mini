package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信日志
 * </p>
 *
 * @author lxl
 * @since 2025-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_log")
public class SMSLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送类型1.单发，2.群发
     */
    private Integer type;

    /**
     * 接收人手机号
     */
    private String phone;

    /**
     * 参数内容
     */
    private String params;

    /**
     * 状态码
     */
    private String resultCode;

    /**
     * 结果描述
     */
    private String message;

    /**
     * 回执id
     */
    private String bizid;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 原始报文
     */
    private String result;

    /**
     * 发送人id
     */
    private Long senderId;

    /**
     * 发送数量
     */
    private Integer sendNum;

    /**
     * 场景
     */
    private String scene;

    /**
     * 发送状态（1发送成功2发送失败）
     */
    private Integer status;

}
