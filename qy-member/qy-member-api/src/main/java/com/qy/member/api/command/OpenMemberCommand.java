package com.qy.member.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 开通会员命令
 *
 * @author legendjw
 */
@Data
public class OpenMemberCommand {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 短信验证码 场景：OPEN_MEMBER
     */
    private String verificationCode;

    /**
     * 密码
     */
    private String passwordHash;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;

    /**
     * 开户组织id
     */
    private Long openAccountId;

    /**
     * 会员类型
     */
    private Integer memberTypeId;

    private Integer auditStatus;

    /**
     * 会员id
     */
    private Long memberId;
}