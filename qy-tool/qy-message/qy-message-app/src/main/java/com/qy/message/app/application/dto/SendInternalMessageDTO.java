package com.qy.message.app.application.dto;

import lombok.Data;

/**
 * 站内信消息DTO
 *
 * @author legendjw
 */
@Data
public class SendInternalMessageDTO {
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
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 上下文名称
     */
    private String contextName;

    /**
     * 用户id
     */
    private String userId;
}
