package com.qy.member.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员系统基本DTO
 *
 * @author legendjw
 */
@Data
public class MemberSystemBasicDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 系统类型id
     */
    @ApiModelProperty("系统类型id")
    private Integer typeId;

    /**
     * 系统类型名称
     */
    @ApiModelProperty("系统类型名称")
    private String typeName;

    /**
     * 会员类型id
     */
    @ApiModelProperty("会员类型id")
    private Integer memberTypeId;

    /**
     * 会员类型名称
     */
    @ApiModelProperty("会员类型名称")
    private String memberTypeName;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
}