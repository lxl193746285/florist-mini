package com.qy.attachment.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("id")
    private Long id;

    /**
     * uid
     */
    @ApiModelProperty("uid")
    private String uid;

    /**
     * 文件存储方式: 1: 本地 2: 阿里云oss
     */
    @ApiModelProperty("文件存储方式: 1: 本地 2: 阿里云oss")
    private Integer storageMode;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 访问地址
     */
    @ApiModelProperty("访问地址")
    private String url;

    private String path;

    /**
     * 媒体类型
     */
    @ApiModelProperty("媒体类型")
    private String mimeType;

    /**
     * 文件大小(字节)
     */
    @ApiModelProperty("文件大小(字节)")
    private Integer size;

    /**
     * 是否是图片
     */
    @ApiModelProperty("是否是图片")
    private Byte isImage;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;

    /**
     * 默认缩略图
     */
    @ApiModelProperty("默认缩略图")
    private String thumbUrl = "";

    /**
     * 所有缩略图
     */
    @ApiModelProperty("所有缩略图")
    private List<AttachmentThumbBasicDTO> thumbs = new ArrayList<>();
}