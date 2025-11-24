package com.qy.member.api.dto;

import lombok.Data;

/**
 * 会员基本数据
 *
 * @author legendjw
 */
@Data
public class MemberBasicDTO {
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
     * 会员系统名称
     */
    private String systemName;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态id
     */
    private Integer statusId;

    private Long employeeId;

    private Integer memberTypeId;

    private String memberTypeName;
}
