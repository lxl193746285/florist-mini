package com.qy.verification.app.application.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qy.verification.app.domain.enums.SmsScene;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 验证码
 * </p>
 *
 * @author legendjw
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VerificationCodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 场景
     */
    private String scene;

    /**
     * 接收方消息类型: SMS:短信, EMAIL:邮箱
     */
    private String receiverMessageType;

    /**
     * 接收方地址
     */
    private String receiverAddress;

    /**
     * 验证码
     */
    private String code;

    /**
     * 有效时长，单位分钟
     */
    private Integer validDuration;

    /**
     * 是否已使用: 1:已使用, 0:未使用
     */
    private Byte isUsed;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;

    /**
     * 失效时间
     */
    private LocalDateTime expiredTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private String expiredTimeName;

    private String createTimeName;


    /**
     * 是否已使用: 1:已使用, 0:未使用
     */
    private String isUsedName;

    private String useTimeName;

    private String sceneName;

    public String getExpiredTimeName() {
        if (expiredTime != null){
            return expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return expiredTimeName;
    }

    public String getCreateTimeName() {
        if (createTime != null){
            return createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return createTimeName;
    }

    public String getIsUsedName() {
        if (isUsed != null){
            return isUsed == 1 ? "已使用" : "未使用";
        }
        return isUsedName;
    }

    public String getUseTimeName() {
        if (useTime != null){
            return useTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return useTimeName;
    }

    public String getSceneName() {
        SmsScene smsScene = SmsScene.getByScene(scene);
        if (smsScene != null){
            return smsScene.getDescription();
        }
        return sceneName;
    }
}