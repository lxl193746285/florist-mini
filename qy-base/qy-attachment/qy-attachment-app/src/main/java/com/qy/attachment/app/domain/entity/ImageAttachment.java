package com.qy.attachment.app.domain.entity;

import com.qy.attachment.app.domain.enums.StorageMode;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.domain.valueobject.User;
import lombok.Builder;
import lombok.Getter;

/**
 * 图片附件
 *
 * @author legendjw
 */
@Getter
@Builder
public class ImageAttachment {
    /**
     * id
     */
    private AttachmentId id;
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 文件存储方式
     */
    private StorageMode storageMode;

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
     * 下载数
     */
    private Integer downloads;

    /**
     * 创建人
     */
    private User creator;

    /**
     * 宽度
     */
    private int width;

    /**
     * 高度
     */
    private int height;

    public void setId(AttachmentId id) {
        this.id = id;
    }
}