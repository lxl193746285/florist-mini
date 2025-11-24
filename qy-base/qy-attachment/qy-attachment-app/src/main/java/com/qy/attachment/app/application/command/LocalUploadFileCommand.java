package com.qy.attachment.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 本地上传单文件命令
 *
 * @author legendjw
 */
@Data
public class LocalUploadFileCommand implements Serializable {
    /**
     * 当前用户id
     */
    @JsonIgnore
    private Long identityId;

    /**
     * 当前用户名称
     */
    @JsonIgnore
    private String identityName;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 模块id
     */
    private String moduleId = "default";
}
