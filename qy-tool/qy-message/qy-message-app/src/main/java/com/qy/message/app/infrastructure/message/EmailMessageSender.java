package com.qy.message.app.infrastructure.message;

import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.dto.SendEmailMessageDTO;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 邮件发送器
 *
 * @author legendjw
 */
@Component
public class EmailMessageSender {
    private PlatformQueryService platformQueryService;

    public EmailMessageSender(PlatformQueryService platformQueryService) {
        this.platformQueryService = platformQueryService;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(SendEmailMessageDTO message) throws MessagingException, GeneralSecurityException {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(message.getPlatformId());
        if (platformDTO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        String mailHost = platformDTO.getConfig().getMailHost();
        String mailPort = platformDTO.getConfig().getMailPort();
        String mailUsername = platformDTO.getConfig().getMailUsername();
        String mailPassword = platformDTO.getConfig().getMailPassword();
        if (StringUtils.isBlank(mailUsername) || StringUtils.isBlank(mailPassword)) {
            throw new ValidationException("发送邮箱消息邮箱配置错误");
        }

        if (StringUtils.isBlank(message.getEmail())) {
            throw new ValidationException("发送邮箱消息邮箱不能为空");
        }

        //调用邮箱发送器发送
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setPort(Integer.valueOf(mailPort));
        javaMailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.timeout", "5000");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        javaMailSender.setJavaMailProperties(properties);

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setTo(message.getEmail());
        messageHelper.setFrom(mailUsername);
        messageHelper.setSubject(message.getTitle());
        messageHelper.setText(message.getContent(), true);

        javaMailSender.send(mailMessage);
    }
}