package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改会员信息命令
 *
 * @author legendjw
 */
@Data
public class ModifyMemberInfoCommand {
    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 头像附件id
     */
    private Long avatarAttachmentId;

    /**
     * 性别id
     */
    private Integer genderId;

    /**
     * 省份代码
     */
    private Integer provinceId;

    /**
     * 城市代码
     */
    private Integer cityId;

    /**
     * 地区代码
     */
    private Integer areaId;

    /**
     * 街道代码
     */
    private Integer streetId;

    /**
     * 详细地址
     */
    private String address;
}