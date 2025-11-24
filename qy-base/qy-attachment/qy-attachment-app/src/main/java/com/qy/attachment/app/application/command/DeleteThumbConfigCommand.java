package com.qy.attachment.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除附件缩略图配置命令
 *
 * @author legendjw
 */
@Data
public class DeleteThumbConfigCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * id
     */
    private Long id;
}