package com.qy.rbac.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 更新应用命令
 *
 * @author legendjw
 */
@Data
public class UpdateAppCommand implements Serializable {
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

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入应用名称")
    private String name;

    /**
     * 应用标示
     */
    @ApiModelProperty("应用标示")
    private String code;

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
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 额外数据
     */
    @ApiModelProperty("额外数据")
    private String extraData;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

//    @ApiModelProperty("会员系统id")
//    private Long memberSystemId;

    @ApiModelProperty("组织id")
    private Long organizationId;

    @ApiModelProperty("客户端ids")
    @NotNull(message = "请选择客户端")
    private List<Long> clientIds;
}