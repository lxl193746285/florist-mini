package com.qy.organization.app.application.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建权限组命令
 *
 * @author legendjw
 */
@Data
public class CreateRoleCommand implements Serializable {
    /**
     * 当前员工
     */
    @JsonIgnore
    private EmployeeIdentity employee;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    @NotNull(message = "请选择权限组所属组织")
    private Long organizationId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入权限组名称")
    private String name;

    /**
     * 上下文，支持以下：
     * 组织内部使用：organization
     * 下级客户使用：subordinate
     * 会员系统：member_system
     */
    @ApiModelProperty("上下文，代码表：role_context 支持以下：组织内部使用：organization 下级客户使用：subordinate 会员系统：member_system")
    private String context;

    /**
     * 上下文id
     */
    @ApiModelProperty("上下文id")
    private String contextId;

    /**
     * 岗位描述
     */
    @ApiModelProperty("岗位描述")
    private String description;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态id
     */
    @ApiModelProperty("状态id")
    private Integer statusId;

    /**
     * 状态名称
     */
    @ApiModelProperty("状态名称")
    private String statusName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    private String systemId;
}