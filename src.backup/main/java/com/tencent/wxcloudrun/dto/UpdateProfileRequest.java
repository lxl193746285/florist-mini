package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * 修改用户资料请求 DTO
 */
@Data
public class UpdateProfileRequest {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;
}
