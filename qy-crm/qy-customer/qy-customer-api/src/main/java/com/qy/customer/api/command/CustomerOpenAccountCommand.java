package com.qy.customer.api.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 开户命令
 *
 * @author legendjw
 */
@Data
public class CustomerOpenAccountCommand implements Serializable {
    /**
     * 客户id
     */
    private Long id;

    /**
     * 账号类型 1: 使用已有账号 2: 创建新账号
     */
    private Integer accountType;

    /**
     * 昵称
     */
    private String accountNickname;

    /**
     * 手机号
     */
    private String accountPhone;

    /**
     * 短信验证码
     */
    private String verificationCode;

    /**
     * 开户组织名称
     */
    private String name;

    /**
     * 权限组ID集合
     */
    private List<Long> roleIds;
}