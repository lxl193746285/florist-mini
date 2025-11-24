package com.qy.member.app.application.dto;

import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员详情
 *
 * @author legendjw
 */
@Data
public class SystemPassportQrcodeDTO {

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
