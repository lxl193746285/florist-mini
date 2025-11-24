package com.qy.identity.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户信息命令
 *
 * @author legendjw
 */
@Data
public class ModifyUserInfoCommand {
    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;
}