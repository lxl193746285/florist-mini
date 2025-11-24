package com.qy.member.api.command;

import lombok.Data;

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
