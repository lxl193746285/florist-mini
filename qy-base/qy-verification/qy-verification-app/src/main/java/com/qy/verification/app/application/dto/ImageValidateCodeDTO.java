package com.qy.verification.app.application.dto;

import lombok.Data;

/**
 * @author wwd
 * @since 2024-01-15 17:39
 */
@Data
public class ImageValidateCodeDTO {

    /**
     * 验证码图片
     */
    private String imageUrl;

    /**
     * 临时会话token
     */
    private String tmpAccessToken;
}
