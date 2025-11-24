package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 附带用户信息的消息
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageWithUserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 一级关联模块id
     */
    private String primaryModuleId;

    /**
     * 一级关联模块名称
     */
    private String primaryModuleName;

    /**
     * 一级关联数据id
     */
    private Long primaryDataId;

    /**
     * 二级关联模块id
     */
    private String secondaryModuleId;

    /**
     * 二级关联模块名称
     */
    private String secondaryModuleName;

    /**
     * 二级关联数据id
     */
    private Long secondaryDataId;

    /**
     * 链接
     */
    private String link;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

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
