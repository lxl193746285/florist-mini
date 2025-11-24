package com.qy.member.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员系统基本DTO
 *
 * @author legendjw
 */
@Data
public class MemberOrganizationBasicDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organizationName;

    /**
     * 组织简称
     */
    @ApiModelProperty("组织简称")
    private String shortName;
}