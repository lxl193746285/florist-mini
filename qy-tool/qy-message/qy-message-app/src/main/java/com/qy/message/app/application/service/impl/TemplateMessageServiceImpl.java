package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.command.SendTemplateMessageCommand;
import com.qy.message.app.application.dto.*;
import com.qy.message.app.application.enums.MessageLogType;
import com.qy.message.app.application.service.*;
import com.qy.message.app.infrastructure.message.*;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplate;
import com.qy.rest.exception.NotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 模版消息服务实现
 *
 * @author legendjw
 */
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {
    private MessageTemplateService messageTemplateService;
    private MessageTemplateCustomParameterService messageTemplateCustomParameterService;
    private MessageTemplateParameterService messageTemplateParameterService;
    private MessageLogService messageLogService;
    private InternalMessageSender internalMessageSender;
    private WechatMessageSender wechatMessageSender;
    private AppMessageSender appMessageSender;
    private EmailMessageSender emailMessageSender;
    private SmsMessageSender smsMessageSender;

    public TemplateMessageServiceImpl(MessageTemplateService messageTemplateService, MessageTemplateCustomParameterService messageTemplateCustomParameterService, MessageTemplateParameterService messageTemplateParameterService, MessageLogService messageLogService, InternalMessageSender internalMessageSender, WechatMessageSender wechatMessageSender, AppMessageSender appMessageSender, EmailMessageSender emailMessageSender, SmsMessageSender smsMessageSender) {
        this.messageTemplateService = messageTemplateService;
        this.messageTemplateCustomParameterService = messageTemplateCustomParameterService;
        this.messageTemplateParameterService = messageTemplateParameterService;
        this.messageLogService = messageLogService;
        this.internalMessageSender = internalMessageSender;
        this.wechatMessageSender = wechatMessageSender;
        this.appMessageSender = appMessageSender;
        this.emailMessageSender = emailMessageSender;
        this.smsMessageSender = smsMessageSender;
    }

    @Override
    @Async
    public void sendTemplateMessage(SendTemplateMessageCommand command) {
        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplateByModuleFunction(command.getTemplateModuleId(),
                command.getTemplateFunctionId(), command.getWeixinAppId());
        if (messageTemplate == null) {
            throw new NotFoundException(String.format("未找到指定模块%s指定功能%s对应的消息模版", command.getTemplateModuleId(), command.getTemplateFunctionId()));
        }
        if (messageTemplate.getIsEnable().intValue() != 1) { return; }

        //解析消息模版参数
        Map<String, Object> allParams = new HashMap<>();
        Map<String, Object> customParamMap = messageTemplateCustomParameterService.customParameterParse(messageTemplate.getId(), command.getParams());
        Map<String, Object> paramMap = messageTemplateParameterService.parameterParse(messageTemplate.getId(), command.getParams());
        allParams.putAll(customParamMap);
        allParams.putAll(paramMap);
        if (command.getParams() != null) {
            allParams.putAll(command.getParams());
        }

        //解析消息标题和内容
        String title = messageTemplateService.getParsedTitle(messageTemplate, allParams);
        String content = messageTemplateService.getParsedContent(messageTemplate, allParams);

        //根据设置发送具体的消息
        //站内信
        if (messageTemplate.getIsSendSystemNotice().intValue() == 1) {
            try {
                SendInternalMessageDTO sendInternalMessageDTO = new SendInternalMessageDTO();
                sendInternalMessageDTO.setTitle(title);
                sendInternalMessageDTO.setContent(content);
                sendInternalMessageDTO.setContext(command.getContext());
                sendInternalMessageDTO.setContextName(command.getContextName());
                sendInternalMessageDTO.setContextId(command.getContextId());
                sendInternalMessageDTO.setUserId(command.getUserId());
                sendInternalMessageDTO.setPrimaryModuleId(command.getPrimaryModuleId());
                sendInternalMessageDTO.setPrimaryModuleName(command.getPrimaryModuleName());
                sendInternalMessageDTO.setPrimaryDataId(command.getPrimaryDataId());
                sendInternalMessageDTO.setSecondaryModuleId(command.getSecondaryModuleId());
                sendInternalMessageDTO.setSecondaryModuleName(command.getSecondaryModuleName());
                sendInternalMessageDTO.setSecondaryDataId(command.getSecondaryDataId());
                sendInternalMessageDTO.setLink(command.getLink());
                internalMessageSender.sendMessage(sendInternalMessageDTO);
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.STATION_LETTER.getByte(), title, content, command.getReceiveUserId(), (byte) 1, null, null, null, "", command.getSrcId(), command.getSrcRowId());
            }
            catch (Exception e) {
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.STATION_LETTER.getByte(), title, content, command.getReceiveUserId(), (byte) 0, null, null, null, e.getMessage(), command.getSrcId(), command.getSrcRowId());
            }
        }
        //微信
        if (messageTemplate.getIsSendWeixin().intValue() == 1) {
            try {
                SendWechatMessageDTO sendWechatMessageDTO = new SendWechatMessageDTO();
                sendWechatMessageDTO.setWechatTemplateId(messageTemplate.getWeixinModelId());
                sendWechatMessageDTO.setOpenId(command.getOpenId());
                sendWechatMessageDTO.setPlatformId(Long.valueOf(messageTemplate.getClientType()));
                sendWechatMessageDTO.setData(allParams);
                wechatMessageSender.sendMessage(sendWechatMessageDTO);
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.WECHAT.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 1, null, null, null, "", command.getSrcId(), command.getSrcRowId());
            }
            catch (Exception e) {
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.WECHAT.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 0, null, null, null, e.getMessage(), command.getSrcId(), command.getSrcRowId());
            }
        }
        //短信
        if (messageTemplate.getIsSendPhoneMessage().intValue() == 1) {
            try {
                SendSmsMessageDTO sendSmsMessageDTO = new SendSmsMessageDTO();
                sendSmsMessageDTO.setPlatformId(Long.valueOf(messageTemplate.getClientType()));
                sendSmsMessageDTO.setPhone(command.getPhone());
                sendSmsMessageDTO.setSmsTemplateId(messageTemplate.getPhoneMessageModelId());
                sendSmsMessageDTO.setData(allParams);
                smsMessageSender.sendMessage(sendSmsMessageDTO);
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.SMS.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 1, null, null, null, "", command.getSrcId(), command.getSrcRowId());
            }
            catch (Exception e) {
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.SMS.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 0, null, null, null, e.getMessage(), command.getSrcId(), command.getSrcRowId());
            }
        }
        //邮件
        if (messageTemplate.getIsSendEmail().intValue() == 1) {
            try {
                SendEmailMessageDTO sendEmailMessageDTO = new SendEmailMessageDTO();
                sendEmailMessageDTO.setPlatformId(Long.valueOf(messageTemplate.getClientType()));
                sendEmailMessageDTO.setEmail(command.getEmail());
                sendEmailMessageDTO.setTitle(title);
                sendEmailMessageDTO.setContent(content);
                emailMessageSender.sendMessage(sendEmailMessageDTO);
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.EMAIL.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 1, null, null, null, "", command.getSrcId(), command.getSrcRowId());
            }
            catch (Exception e) {
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.EMAIL.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 0, null, null, null, e.getMessage(), command.getSrcId(), command.getSrcRowId());
            }
        }
        //APP推送
        if (messageTemplate.getIsSendApppush().intValue() == 1) {
            try {
                SendAppMessageDTO sendAppMessageDTO = new SendAppMessageDTO();
                sendAppMessageDTO.setPlatformId(Long.valueOf(messageTemplate.getClientType()));
                sendAppMessageDTO.setMobileId(command.getMobileId());
                sendAppMessageDTO.setTitle(title);
                sendAppMessageDTO.setContent(content);
                sendAppMessageDTO.setTitle(title);
                sendAppMessageDTO.setContent(content);
                sendAppMessageDTO.setData(command.getExtraParams());
                appMessageSender.sendMessage(sendAppMessageDTO);
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.APP.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 1, null, null, null, "", command.getSrcId(), command.getSrcRowId());
            }
            catch (Exception e) {
                messageLogService.createMessageLog(messageTemplate.getId(), MessageLogType.APP.getByte(), title, allParams.toString(), command.getReceiveUserId(), (byte) 0, null, null, null, e.getMessage(), command.getSrcId(), command.getSrcRowId());
            }
        }
    }
}
