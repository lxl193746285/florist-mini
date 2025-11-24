package com.qy.crm.customer.app.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 客户修改会员命令
 *
 * @author legendjw
 */
@Data
public class CustomerUpdateOpenMemberCommand {
    /**
     * 客户id
     */
    private Long id;

    /**
     * 会员系统id
     */
    @NotNull(message = "请选择会员系统")
    private String systemId;

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
    @NotBlank(message = "请输入验证码")
    private String verificationCode;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;

    /**
     * 开户组织id
     */
    private Long openAccountId;
}