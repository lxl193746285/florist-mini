package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 创建会员系统微信应用表单
 *
 * @author legendjw
 */
@Data
public class MemberSystemWeixinAppForm {
    /**
     * id
     */
    private Long id;

    /**
     * 客户端id
     */
    @NotNull(message = "客户端id")
    private Long clientId;

    /**
     * 应用类型id 系统代码表：weixin_app_type
     */
    @NotNull(message = "请选择应用类型")
    private Integer typeId;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 二维码图片附件id
     */
    private Long qrCodeAttachmentId;

    /**
     * 备注
     */
    private String remark;
}
