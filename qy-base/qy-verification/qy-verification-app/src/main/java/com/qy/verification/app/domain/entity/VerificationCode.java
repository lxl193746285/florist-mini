package com.qy.verification.app.domain.entity;

import com.qy.verification.app.domain.valueobject.Scene;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码实体
 *
 * @author legendjw
 */
public class VerificationCode implements Serializable {
    /**
     * 场景
     */
    private Scene scene;

    /**
     * 验证码
     */
    private String code;

    /**
     * 有效时长，单位分钟
     */
    private Integer validDuration = 5;

    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 是否使用过
     */
    private boolean isUsed = false;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;

    public VerificationCode(Scene scene, String code) {
        this.scene = scene;
        this.code = code;
    }

    public VerificationCode(Scene scene, String code, Integer validDuration, LocalDateTime createTime, boolean isUsed, LocalDateTime useTime) {
        this.scene = scene;
        this.code = code;
        this.validDuration = validDuration;
        this.createTime = createTime;
        this.isUsed = isUsed;
        this.useTime = useTime;
    }

    public Scene getScene() {
        return scene;
    }

    public String getCode() {
        return code;
    }

    public Integer getValidDuration() {
        return validDuration;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public LocalDateTime getUseTime() {
        return useTime;
    }

    /**
     * 获取失效时间
     *
     * @return
     */
    public LocalDateTime getExpiredTime() {
        return createTime.plusMinutes(validDuration);
    }

    /**
     * 是否是有效的验证码（未过期且未被使用过的）
     *
     * @return
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(this.getExpiredTime()) && !isUsed) {
            return true;
        }
        return false;
    }

    /**
     * 设置验证码已使用
     */
    public void setUsed() {
        isUsed = true;
        useTime = LocalDateTime.now();
    }
}