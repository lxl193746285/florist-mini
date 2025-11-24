package com.qy.organization.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户基本信息
 *
 * @author legendjw
 */
@Data
public class UserBasicDTO implements Serializable {
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
}