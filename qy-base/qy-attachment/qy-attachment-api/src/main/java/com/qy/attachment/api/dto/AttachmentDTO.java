package com.qy.attachment.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件DTO
 *
 * @author legendjw
 */
@Data
public class AttachmentDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 文件存储方式: 1: 本地 2: 阿里云oss
     */
    private Integer storageMode;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

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
     * md5值
     */
    private String md5;

    /**
     * 是否是图片
     */
    private Byte isImage;

    /**
     * 下载数
     */
    private Integer downloads;

    /**
     * 默认缩略图
     */
    private String thumbUrl = "";

    /**
     * 所有缩略图
     */
    private List<AttachmentThumbBasicDTO> thumbs = new ArrayList<>();

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private String updateTimeName;
}