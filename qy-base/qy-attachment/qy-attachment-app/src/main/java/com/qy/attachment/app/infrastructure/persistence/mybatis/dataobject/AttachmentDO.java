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
 * 附件
 * </p>
 *
 * @author legendjw
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_attachment")
public class AttachmentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
