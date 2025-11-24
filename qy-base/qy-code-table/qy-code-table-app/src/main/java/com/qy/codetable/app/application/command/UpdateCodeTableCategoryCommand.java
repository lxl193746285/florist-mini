package com.qy.codetable.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 编辑代码表分类命令
 *
 * @author legendjw
 */
@Data
public class UpdateCodeTableCategoryCommand {
    /**
     * 当前登录用户
     */
    @JsonIgnore
    private Identity user;

    /**
     * id
     */
    private Long id;

    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    @ApiModelProperty("类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码")
    private String type;

    /**
     * 标示码
     */
    @ApiModelProperty("标示码")
    @NotBlank(message = "请输入分类唯一标示")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入分类名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态id
     */
    @ApiModelProperty("状态id")
    @NotNull(message = "请选择状态")
    private Integer statusId;
}
