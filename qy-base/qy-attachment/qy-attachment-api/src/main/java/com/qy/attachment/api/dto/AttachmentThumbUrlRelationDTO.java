package com.qy.attachment.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttachmentThumbUrlRelationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件id
     */
    private Long attachmentId;

    /**
     * 缩略图地址
     */
    private String thumbUrl;
}
