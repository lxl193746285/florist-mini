package com.qy.identity.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.enums.UserStatus;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.ValidationException;
import com.qy.identity.app.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author legendjw
 */
@Getter
@Builder
public class User implements Entity {
    /**
     * 账号ID
     */
    private UserId userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private Username username;

    /**
     * 手机号
     */
    private PhoneNumber phone;

    /**
     * 邮箱
     */
    private Email email;

    /**
     * 密码
     */
    private Password password;

    /**
     * 头像
     */
    private Avatar avatar;

    /**
     * 状态
     */
    @Builder.Default
    private UserStatus status = UserStatus.ENABLE;

    /**
     * 来源
     */
    private UserSource source;

    /**
     * 创建时间
     */
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改姓名
     *
     * @param name
     */
    public void modifyName(String name) {
        if (StringUtils.isBlank( name)) {
            throw new ValidationException("姓名不能为空");
        }
        this.name = name;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 修改头像
     *
     * @param avatar
     */
    public void modifyAvatar(Avatar avatar) {
        this.avatar = avatar;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 更换手机号
     *
     * @param phone
     */
    public void changePhone(PhoneNumber phone) {
        this.phone = phone;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 更换邮箱
     *
     * @param email
     */
    public void changeEmail(Email email) {
        this.email = email;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 更换用户名
     *
     * @param username
     */
    public void changeUsername(Username username) {
        this.username = username;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 修改密码
     *
     * @param password
     */
    public void modifyPassword(Password password) {
        this.password = password;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 账号登录
     */
    public void login() {
        if (!this.status.equals(UserStatus.ENABLE)) {
            throw new BusinessException("账号已被禁用无法登录");
        }
    }
}