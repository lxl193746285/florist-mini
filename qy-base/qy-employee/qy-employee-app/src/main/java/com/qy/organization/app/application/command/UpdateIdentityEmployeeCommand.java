package com.qy.organization.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 认证员工修改信息命令
 *
 * @author legendjw
 */
@Data
public class UpdateIdentityEmployeeCommand {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入员工姓名")
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别id 代码表：gender
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;
}