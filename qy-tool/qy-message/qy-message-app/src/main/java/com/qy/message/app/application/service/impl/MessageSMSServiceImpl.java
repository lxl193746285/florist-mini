package com.qy.message.app.application.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qy.codetable.api.client.CodeTableClient;
import com.qy.message.app.application.command.SendMessageSMSCommand;
import com.qy.message.app.application.enums.MessageSmsScene;
import com.qy.message.app.application.service.MessageSMSService;

import com.qy.message.app.application.util.MsgJpIamUtils;
import com.qy.message.app.domain.sender.SendSMSResult;
import com.qy.message.app.domain.valueobject.SmsReceiver;
import com.qy.message.app.domain.valueobject.SmsScene;
import com.qy.message.app.infrastructure.persistence.PlatformRepository;
import com.qy.message.app.infrastructure.persistence.SMSRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.SMSLogDO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

@Service
public class MessageSMSServiceImpl implements MessageSMSService {
    private SMSRepository smsRepository;

    public MessageSMSServiceImpl(SMSRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @Override
    public SendSMSResult sendSMS(SendMessageSMSCommand command) {
        SmsReceiver receiver = new SmsReceiver();
        receiver.setAddress(command.getPhone());
        return sendMessageSMS(new SmsScene(command.getScene()), command.getContent(), receiver, command.getSenderId());
    }

    /**
     * 给指定接收方发送一条短信
     *
     * @param scene
     * @param receiver
     * @return
     */
    private SendSMSResult sendMessageSMS(SmsScene scene, String content, SmsReceiver receiver, Long senderId) {
        SMSLogDO smsLogDO = new SMSLogDO();
        ObjectMapper objectMapper = new ObjectMapper(); // 复用 ObjectMapper 实例
        SendSMSResult smsResult =  new SendSMSResult();

        // 辅助方法：记录日志并返回结果
        BiFunction<String, String, SendSMSResult> logAndReturn = (message, errorMessage) -> {
            smsLogDO.setSendTime(LocalDateTime.now());
            smsLogDO.setType(1);
            smsLogDO.setSendNum(1);
            smsLogDO.setMessage(message);
            smsLogDO.setStatus(0);
            smsLogDO.setSenderId(senderId);
            smsLogDO.setScene(scene == null ? null : scene.getId());

            Map<String, Object> params = new HashMap<>();
            params.put("scene", scene == null ? null : scene.getId());
            params.put("content", content);
            params.put("receiver", receiver == null ? null : receiver.getAddress());

            try {
                String paramsJson = objectMapper.writeValueAsString(params);
                smsLogDO.setParams(paramsJson);
            } catch (Exception e) {
                smsLogDO.setParams("参数转换失败: " + e.getMessage());
            }

            smsRepository.save(smsLogDO);

            smsResult.setSuccess(false);
            smsResult.setErrorMessage(errorMessage);
            smsResult.setReceiver(receiver);
            return smsResult;
        };

        // 参数校验
        if (receiver == null || receiver.getAddress() == null || receiver.getAddress().isEmpty()) {
            return logAndReturn.apply("短信接收手机为空", "短信接收手机为空");
        }
        if (!isValidPhoneNumber(receiver.getAddress())) {
            return logAndReturn.apply("无效的手机号格式", "无效的手机号格式");
        }
        if (scene == null || scene.getId() == null) {
            return logAndReturn.apply("短信场景为空", "短信场景为空");
        }
        if (content == null || content.isEmpty()) {
            return logAndReturn.apply("短信内容为空", "短信内容为空");
        }

        // 验证场景 ID 是否合法
        MessageSmsScene smsScene;
        try {
            smsScene = MessageSmsScene.valueOf(scene.getId());
        } catch (IllegalArgumentException e) {
            return logAndReturn.apply("非法的短信场景", "非法的短信场景: " + e.getMessage());
        }

        // 发送短信
        boolean result = true;
        String resultText = "";
        try {
            String smsContent = MsgJpIamUtils.sendSms(receiver.getAddress(), content, smsScene.getCode());
            JSONObject jsonObject = JSONObject.parseObject(smsContent);
            if (jsonObject.containsKey("error")) {
                result = false;
                resultText = jsonObject.getString("error");
            }
            smsLogDO.setResult(smsContent);
        } catch (Exception e) {
            result = false;
            resultText = e.getMessage();
            smsLogDO.setResult(e.getMessage());
        }
        smsResult.setSuccess(result);
        smsResult.setErrorMessage(resultText);
        smsResult.setReceiver(receiver);

        // 记录成功或失败的日志
        smsLogDO.setType(1);
        smsLogDO.setSendNum(1);
        smsLogDO.setScene(smsScene.getScene());
        smsLogDO.setStatus(smsResult.isSuccess() ? 1 : 0);
        smsLogDO.setPhone(smsResult.getReceiver().getAddress());

        Map<String, Object> params = new HashMap<>();
        params.put("scene", scene.getId());
        params.put("content", content);
        params.put("receiver", receiver.getAddress());

        try {
            String paramsJson = objectMapper.writeValueAsString(params);
            smsLogDO.setParams(paramsJson);
        } catch (Exception e) {
            smsLogDO.setParams("参数转换失败: " + e.getMessage());
        }

        smsLogDO.setSenderId(senderId);
        smsLogDO.setMessage(smsResult.getErrorMessage());
        smsLogDO.setSendTime(LocalDateTime.now());
        smsRepository.save(smsLogDO);

        return smsResult;
    }

    /**
     * 验证手机号格式
     *
     * @param phoneNumber
     * @return
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        // 这里使用一个简单的正则表达式来验证手机号格式
        // 请根据实际需求调整正则表达式
        Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");
        return pattern.matcher(phoneNumber).matches();
    }

}
