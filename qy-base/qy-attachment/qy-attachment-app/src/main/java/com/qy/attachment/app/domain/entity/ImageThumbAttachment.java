package com.qy.attachment.app.domain.entity;

import com.qy.attachment.app.domain.enums.StorageMode;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.domain.valueobject.ThumbAttachmentId;
import com.qy.attachment.app.domain.valueobject.User;
import lombok.Builder;
import lombok.Getter;

/**
 * 图片缩略图附件
 *
 * @author legendjw
 */
@Getter
@Builder
public class ImageThumbAttachment {
    /**
     * id
     */
    private ThumbAttachmentId id;
    /**
     * 图片附件id
     */
    private AttachmentId imageAttachmentId;
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 文件存储方式
     */
    private StorageMode storageMode;

    /**
     * 场景
     */
    private String scenario;

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
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    /**
     * md5值
     */
    private String md5;

    /**
     * 创建人
     */
    private User creator;
}