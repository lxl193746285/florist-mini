package com.qy.attachment.app.application.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 缩略图配置
 *
 * @author legendjw
 * @since 2021-08-03
 */
@Data
public class AttachmentThumbConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型: 1: 系统设置 2: 模块设置
     */
    private Integer typeId;

    /**
     * 图片压缩处理，1.前端，2.服务器
     */
    private Integer imageHandle;

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
    private String scenario;

    /**
     * 上传图片大小限制（单位m)
     */
    private float uploadImageMaxSize;

    /**
     * 上传图片格式限制
     */
    private List<String> uploadImageFormatLimit;

    /**
     * 上传文件大小限制（单位m)
     */
    private float uploadFileMaxSize;

    /**
     * 上传文件格式限制
     */
    private List<String> uploadFileFormatLimit;

    /**
     * 上传视频大小限制（单位m)
     */
    private float uploadVideoMaxSize;

    /**
     * 上传视频格式限制
     */
    private List<String> uploadVideoFormatLimit;

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
     * 缩略尺寸阀值（单位m）
     */
    private float sizeThreshold;

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
     * 图片质量
     */
    private Integer quality;

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

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
