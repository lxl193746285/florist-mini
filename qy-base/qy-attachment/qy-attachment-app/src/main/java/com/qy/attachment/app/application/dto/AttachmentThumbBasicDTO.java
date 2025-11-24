package com.qy.attachment.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 附件缩略图基本DTO
 *
 * @author legendjw
 */
@Data
public class AttachmentThumbBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 场景
     */
    private String scenario;

    /**
     * 名称
     */
    private String name;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 文件大小(字节)
     */
    private Integer size;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 创建时间
     */
    private String createTimeName;
}