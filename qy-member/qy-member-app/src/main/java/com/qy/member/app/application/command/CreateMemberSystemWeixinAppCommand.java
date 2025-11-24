package com.qy.member.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建会员系统微信应用命令
 *
 * @author legendjw
 */
@Data
public class CreateMemberSystemWeixinAppCommand implements Serializable {
    /**
     * 组织id
     */
    @NotNull(message = "请选择组织")
    private Long organizationId;

    /**
     * 客户端id
     */
    @NotNull(message = "请选择客户端")
    private String clientId;

//    /**
//     * 会员系统id
//     */
//    @NotNull(message = "请选择会员系统")
//    private Long systemId;

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