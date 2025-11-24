package com.qy.comment.api.vo;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import lombok.Data;

@Data
public class AttachmentVo extends AttachmentBasicDTO {
    private Integer isVideo;
}

