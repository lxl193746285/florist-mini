package com.qy.member.api.dto;

import lombok.Data;

@Data
public class MemberSystemBasicExtendDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 系统类型id
     */
    private Integer typeId;

    /**
     * 系统类型名称
     */
    private String typeName;

    /**
     * 会员类型id
     */
    private Integer memberTypeId;

    /**
     * 会员类型名称
     */
    private String memberTypeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 微信公众号appId
     */
    private String appId;
}
