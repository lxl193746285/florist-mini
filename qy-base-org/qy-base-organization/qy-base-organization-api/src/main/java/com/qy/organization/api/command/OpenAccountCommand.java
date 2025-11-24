package com.qy.organization.api.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 开户命令
 *
 * @author legendjw
 */
@Data
public class OpenAccountCommand implements Serializable {
    /**
     * 超管昵称
     */
    private String accountNickname;

    /**
     * 手机号
     */
    private String accountPhone;

    /**
     * 短信验证码
     */
    private String verificationCode;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织简称
     */
    private String shortName;

    /**
     * 组织电话
     */
    private String tel;

    /**
     * 组织邮箱
     */
    private String email;

    /**
     * 组织主页
     */
    private String homepage;

    /**
     * 组织行业
     */
    private Integer industryId;

    /**
     * 组织规模
     */
    private Integer scaleId;

    /**
     * 权限组ID集合
     */
    private List<Long> roleIds;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 来源类型
     */
    private String source;

    /**
     * 来源类型名称
     */
    private String sourceName;

    /**
     * 来源关联id
     */
    private Long sourceDataId;

    /**
     * 来源关联名称
     */
    private String sourceDataName;
}