package com.qy.crm.customer.app.application.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 客户修改开户超管命令
 *
 * @author legendjw
 */
@Data
public class CustomerModifyOpenAccountCommand implements Serializable {
    /**
     * 客户id
     */
    private Long id;

    /**
     * 超管联系人
     */
    private Long contactId;

    /**
     * 超管昵称
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
     * 组织名称
     */
    private String name;

    /**
     * 组织简称
     */
    private String shortName;

    /**
     * 组织电话
     */
    private String tel;

    /**
     * 组织邮箱
     */
    private String email;

    /**
     * 组织主页
     */
    private String homepage;

    /**
     * 组织行业
     */
    private Integer industryId;

    /**
     * 组织规模
     */
    private Integer scaleId;

    /**
     * 权限组ID集合
     */
    private List<Long> roleIds;
}