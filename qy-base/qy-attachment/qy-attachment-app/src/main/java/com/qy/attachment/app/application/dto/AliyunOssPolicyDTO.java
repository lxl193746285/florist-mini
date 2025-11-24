package com.qy.attachment.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 阿里云OSS请求上传Policy和回调DTO
 *
 * @author legendjw
 */
@Data
public class AliyunOssPolicyDTO implements Serializable {
    private String accessid;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;
    private String callback;
}