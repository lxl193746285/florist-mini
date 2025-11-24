package com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 附件缩略图设置
 * </p>
 *
 * @author legendjw
 * @since 2021-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_attachment_thumb_config")
public class AttachmentThumbConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 类型名称
     */
    private String typeName;

    /**
     * 图片压缩处理，1.前端，2.服务器
     */
    private Integer imageHandle;

    /**
     * 模块id
     */
    private String moduleId;

    /**
     * 场景
     */
    private String scenario;

    /**
     * 上传图片大小限制（单位byte）
     */
    private Integer uploadImageMaxSize;

    /**
     * 上传图片格式限制
     */
    private String uploadImageFormatLimit;

    /**
     * 上传文件大小限制（单位byte）
     */
    private Integer uploadFileMaxSize;

    /**
     * 上传文件格式限制
     */
    private String uploadFileFormatLimit;

    /**
     * 上传视频大小限制（单位byte）
     */
    private Integer uploadVideoMaxSize;

    /**
     * 上传视频格式限制
     */
    private String uploadVideoFormatLimit;

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
     * 缩略尺寸阀值
     */
    private Integer sizeThreshold;

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
    private LocalDateTime createTime;

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
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
