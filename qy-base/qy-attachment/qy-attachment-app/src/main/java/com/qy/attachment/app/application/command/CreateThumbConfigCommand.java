package com.qy.attachment.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建附件缩略图配置命令
 *
 * @author legendjw
 */
@Data
public class CreateThumbConfigCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * 名称
     */
    @NotBlank(message = "请输入配置名称")
    private String name;

    /**
     * 类型: 1: 系统设置 2: 模块设置
     */
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 模块id
     */
    private String moduleId;

    /**
     * 场景
     */
    private String scenario = "default";

    /**
     * 上传图片大小限制（单位kb)
     */
    private float uploadImageMaxSize = 0;

    /**
     * 上传图片格式限制 系统代码表：attachment_image_format
     */
    private List<String> uploadImageFormatLimit = new ArrayList<>();

    /**
     * 上传文件大小限制（单位kb)
     */
    private float uploadFileMaxSize = 0;

    /**
     * 上传文件格式限制 系统代码表：attachment_file_format
     */
    private List<String> uploadFileFormatLimit = new ArrayList<>();

    /**
     * 上传视频大小限制（单位kb)
     */
    private float uploadVideoMaxSize = 0;

    /**
     * 上传视频格式限制 系统代码表：attachment_video_format
     */
    private List<String> uploadVideoFormatLimit = new ArrayList<>();

    /**
     * 限制图片宽度，单位px
     */
    private Integer limitImageWidth;

    /**
     * 限制图片高度，单位px
     */
    private Integer limitImageHeight;

    /**
     * 是否缩略 0: 否 1: 是
     */
    private Byte isThumb;

    /**
     * 缩略尺寸阀值（单位kb)
     */
    private float sizeThreshold = 0;

    /**
     * 是否缩放 0: 否 1: 是
     */
    private Byte isResize;

    /**
     * 缩放模式
     */
    private String resizeMode;

    /**
     * 是否裁剪 0: 否 1: 是
     */
    private Byte isCrop;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 高度
     */
    private Integer imageHandle;

    /**
     * 图片质量
     */
    private Integer quality = 100;
}