package com.qy.message.app.infrastructure.message;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.dto.SendSmsMessageDTO;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 短信发送器
 *
 * @author legendjw
 */
@Component
public class SmsMessageSender {
    private PlatformQueryService platformQueryService;
    private ObjectMapper objectMapper;

    public SmsMessageSender(PlatformQueryService platformQueryService, ObjectMapper objectMapper) {
        this.platformQueryService = platformQueryService;
        this.objectMapper = objectMapper;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(SendSmsMessageDTO message) throws ClientException, JsonProcessingException {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(message.getPlatformId());
        if (platformDTO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        String smsKey = platformDTO.getConfig().getSmsKey();
        String smsSecret = platformDTO.getConfig().getSmsSecret();
        if (StringUtils.isBlank(smsKey) || StringUtils.isBlank(smsSecret)) {
            throw new ValidationException("发送短信消息短信配置错误");
        }

        if (StringUtils.isBlank(message.getPhone())) {
            throw new ValidationException("发送短信消息手机号不能为空");
        }

        //调用阿里云短信服务发送短信
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsKey, smsSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(message.getPhone());
        request.setSignName(platformDTO.getConfig().getSmsSign());
        request.setTemplateCode(message.getSmsTemplateId());
        request.setTemplateParam(objectMapper.writeValueAsString(message.getData()));
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (!sendSmsResponse.getCode().equals("OK")) {
            throw new RuntimeException(String.format("短信发送失败：%s", sendSmsResponse.getMessage()));
        }
    }
}