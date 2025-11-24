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
 * 消息系统消息用户
 * </p>
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_msg_message_user")
public class MessageUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文名称
     */
    private String contextName;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否已读
     */
    private Byte isRead;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;


}
