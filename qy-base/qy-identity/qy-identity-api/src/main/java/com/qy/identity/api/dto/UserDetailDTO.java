package com.qy.identity.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户详情信息
 *
 * @author legendjw
 */
@Data
public class UserDetailDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 加密密码
     */
    private String passwordHash;
}