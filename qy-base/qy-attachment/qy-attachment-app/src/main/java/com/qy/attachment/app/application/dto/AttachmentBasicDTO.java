package com.qy.attachment.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件基本DTO
 *
 * @author legendjw
 */
@Data
public class AttachmentBasicDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * uid
     */
    private String uid;

    /**
     * 文件存储方式: 1: 本地 2: 阿里云oss
     */
    private Integer storageMode;

    /**
     * 名称
     */
    private String name;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 媒体类型
     */
    private String mimeType;

    /**
     * 文件大小(字节)
     */
    private Integer size;

    /**
     * 是否是图片
     */
    private Byte isImage;

    /**
     * 创建时间
     */
    private String createTimeName;

    /**
     * 默认缩略图
     */
    private String thumbUrl = "";

    /**
     * 所有缩略图
     */
    private List<AttachmentThumbBasicDTO> thumbs = new ArrayList<>();
}