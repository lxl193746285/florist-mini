package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改账号信息命令
 *
 * @author legendjw
 */
@Data
public class ModifyAccountInfoCommand {
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 头像附件id
     */
    @ApiModelProperty("头像附件id")
    private Long avatarAttachmentId;
}