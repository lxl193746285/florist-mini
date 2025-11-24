package com.qy.member.app.application.dto;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员系统微信应用DTO
 *
 * @author legendjw
 */
@Data
public class MemberSystemWeixinAppDTO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 应用类型id
     */
    private Integer typeId;

    /**
     * 应用类型名称
     */
    private String typeName;

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
     * 二维码图片
     */
    private AttachmentBasicDTO qrCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private String updateTimeName;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
