package com.qy.codetable.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建代码表项命令
 *
 * @author legendjw
 */
@Data
public class CreateCodeTableItemCommand {
    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 类型: SYSTEM: 系统代码 PERSONAL: 个人代码 ORGANIZATION: 组织代码
     */
    @NotBlank(message = "请选择类型")
    private String type;

    /**
     * 关联id
     */
    private Long relatedId;

    /**
     * 标示码
     */
    @NotBlank(message = "请输入唯一标示码")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称")
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 扩展数据
     */
    private String extendData;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态id
     */
    private Integer statusId;
}