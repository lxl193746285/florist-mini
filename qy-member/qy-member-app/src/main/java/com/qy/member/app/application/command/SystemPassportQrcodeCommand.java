package com.qy.member.app.application.command;

import lombok.Data;

/**
 * 系统二维码命令
 *
 * @author wwd
 */
@Data
public class SystemPassportQrcodeCommand {

    /**
     * uuid
     */
    private String uuid;

    /**
     * 二维码类型
     */
    private String linkType;

    /**
     * 客户端
     */
    private String clientId;

    private Integer status;

    private String path;
}
