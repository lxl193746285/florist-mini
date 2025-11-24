package com.qy.member.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("id")
    private Long id;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private Long systemId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 状态id
     */
    @ApiModelProperty("状态id")
    private Integer statusId;

    /**
     * 状态名称
     */
    @ApiModelProperty("状态名称")
    private String statusName;
}
