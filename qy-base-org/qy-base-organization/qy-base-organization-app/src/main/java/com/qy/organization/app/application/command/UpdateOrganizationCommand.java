package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改组织命令
 *
 * @author legendjw
 */
@Data
public class UpdateOrganizationCommand implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入组织名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    private String shortName;

    /**
     * 组织logo
     */
    @ApiModelProperty("组织logo")
    private String logo;

    /**
     * 组织电话
     */
    @ApiModelProperty("组织电话")
    private String tel;

    /**
     * 组织联系人
     */
    @ApiModelProperty("组织联系人")
    private String contactName;

    /**
     * 组织邮箱
     */
    @ApiModelProperty("组织邮箱")
    private String email;

    /**
     * 组织主页
     */
    @ApiModelProperty("组织主页")
    private String homepage;

    /**
     * 组织地址
     */
    @ApiModelProperty("组织地址")
    private String address;

    /**
     * 组织行业id
     */
    @ApiModelProperty("组织行业id")
    private Integer industryId;

    /**
     * 组织行业名称
     */
    @ApiModelProperty("组织行业名称")
    private String industryName;

    /**
     * 组织规模id
     */
    @ApiModelProperty("组织规模id")
    private Integer scaleId;

    /**
     * 组织规模名称
     */
    @ApiModelProperty("组织规模名称")
    private String scaleName;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    @JsonIgnore
    private Long updatorId;

    /**
     * 更新人名称
     */
    @ApiModelProperty("更新人名称")
    @JsonIgnore
    private String updatorName;
}