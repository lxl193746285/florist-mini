package com.qy.member.api.dto;

import lombok.Data;

/**
 * 账号基本信息
 *
 * @author legendjw
 */
@Data
public class AccountBasicDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 账号类型id：1: 主账号 2: 子账号
     */
    private Integer typeId;

    /**
     * 账号类型名称
     */
    private String typeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String passwordHash;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 手机唯一id
     */
    private String mobileId;
}
