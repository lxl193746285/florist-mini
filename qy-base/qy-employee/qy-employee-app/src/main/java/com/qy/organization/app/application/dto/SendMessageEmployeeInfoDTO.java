package com.qy.organization.app.application.dto;

import lombok.Data;

/**
 * 发送消息员工账号信息DTO
 *
 * @author legendjw
 */
@Data
public class SendMessageEmployeeInfoDTO {
    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 公众号openId
     */
    private String openId;

    /**
     * 手机唯一id
     */
    private String mobileId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}