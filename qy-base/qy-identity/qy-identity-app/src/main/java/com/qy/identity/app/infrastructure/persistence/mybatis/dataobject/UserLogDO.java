package com.qy.identity.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户日志
 * </p>
 *
 * @author legendjw
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_user_log")
public class UserLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 客户端
     */
    private String client;

    /**
     * 动作
     */
    private String action;

    /**
     * 数据
     */
    private String data;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}