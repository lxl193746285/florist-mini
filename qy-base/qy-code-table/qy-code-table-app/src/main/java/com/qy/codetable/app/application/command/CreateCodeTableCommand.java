package com.qy.codetable.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建代码表命令
 *
 * @author legendjw
 */
@Data
public class CreateCodeTableCommand {
    /**
     * 当前登录用户
     */
    @JsonIgnore
    private Identity user;

    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    private Long categoryId;

    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    @ApiModelProperty("类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码")
    @NotBlank(message = "请选择类型")
    private String type;

    /**
     * 标示码
     */
    @ApiModelProperty("标示码")
    @NotBlank(message = "请输入唯一标示码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "请输入名称")
    private String name;

    /**
     * 值类型: NUMBER: 数字 STRING: 字符串
     */
    @ApiModelProperty("值类型: NUMBER: 数字 STRING: 字符串")
    private String valueType;

    /**
     * 数据结构类型: LIST: 列表 TREE: 树形
     */
    @ApiModelProperty("数据结构类型: LIST: 列表 TREE: 树形")
    private String structureType;

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