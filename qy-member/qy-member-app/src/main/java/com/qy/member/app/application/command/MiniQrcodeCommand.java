package com.qy.member.app.application.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wwd
 * @since 2023-10-26 15:44
 */
@Data
public class MiniQrcodeCommand {

    private String scene;

    private String page;

    private Integer width;

    private String envVersion;

    private String clientId;
}
