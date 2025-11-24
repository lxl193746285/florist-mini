package com.qy.rbac.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用
 *
 * @author lxl
 * @since 2024-07-17
 */
@Data
public class AppClientDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * appid
     */
    @ApiModelProperty("appid")
    private Long appId;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private Long clientId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;
}