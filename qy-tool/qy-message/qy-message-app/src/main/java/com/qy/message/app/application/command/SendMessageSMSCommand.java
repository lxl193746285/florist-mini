package com.qy.message.app.application.command;

import com.qy.message.app.application.dto.UserMessageWxDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 发送短信消息命令
 *
 * @author lxl
 */
@Data
public class SendMessageSMSCommand {
    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    private String phone;

    /**
     * 场景
     *
     * 目前存在以下可用：
     * SCREEN_LOTTERY_WIN: 会议大屏中奖短信
     *
     */
    @NotBlank(message = "请输入场景")
    private String scene;

    /**
     * 短信值
     */
    @NotBlank(message = "请输入短信值")
    private String content;

    /**
     * 发送人id
     */
    private Long senderId;
}