package com.qy.verification.app.application.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.member.api.query.MemberQuery;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.util.ImageVerificationCodeUtils;
import com.qy.verification.app.application.command.SendEmailVerificationCodeCommand;
import com.qy.verification.app.application.command.SendSmsVerificationCodeCommand;
import com.qy.verification.app.application.dto.ImageValidateCodeDTO;
import com.qy.verification.app.application.dto.ValidateCodeResultDTO;
import com.qy.verification.app.application.dto.VerificationCodeDTO;
import com.qy.verification.app.application.query.ValidateVerificationCodeQuery;
import com.qy.verification.app.application.service.VerificationCodeService;
import com.qy.verification.app.application.util.JpIamUtils;
import com.qy.verification.app.domain.aggregate.SentVerificationCode;
import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.entity.VerificationCodeLog;
import com.qy.verification.app.domain.enums.MessageType;
import com.qy.verification.app.domain.enums.SmsScene;
import com.qy.verification.app.domain.repository.VerificationCodeLogRepository;
import com.qy.verification.app.domain.repository.VerificationCodeRepository;
import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.service.VerificationCodeDomainService;
import com.qy.verification.app.domain.valueobject.Receiver;
import com.qy.verification.app.domain.valueobject.Scene;
import com.qy.verification.app.infrastructure.persistence.mybatis.dataobject.VerificationCodeDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Value("${qy.verification.close-validate}")
    private Boolean closeValidate;
    @Value("${qy.verification.validate-image-path}")
    private String validateImagePath;
    @Value("${qy.verification.validate-image-url}")
    private String validateImageUrl;

    private VerificationCodeDomainService verificationCodeDomainService;
    private VerificationCodeRepository verificationCodeRepository;
    private VerificationCodeLogRepository verificationCodeLogRepository;
    private MemberClient memberClient;
    private RedisTemplate redisTemplate;

    public VerificationCodeServiceImpl(VerificationCodeDomainService verificationCodeDomainService,
                                       VerificationCodeRepository verificationCodeRepository,
                                       VerificationCodeLogRepository verificationCodeLogRepository,
                                       MemberClient memberClient,
                                       RedisTemplate redisTemplate) {
        this.verificationCodeDomainService = verificationCodeDomainService;
        this.verificationCodeRepository = verificationCodeRepository;
        this.verificationCodeLogRepository = verificationCodeLogRepository;
        this.memberClient = memberClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SendResult sendVerificationCode(SendSmsVerificationCodeCommand command) {
        MemberQuery query = new MemberQuery();
        query.setPhone(command.getPhone());
        MemberBasicDTO dto = memberClient.getBasicSystemMember(query);
        if (dto == null){
            throw new NotFoundException("用户不存在");
        }
        Receiver receiver = new Receiver(MessageType.SMS, command.getPhone());
        return sendVerificationCode(new Scene(command.getScene()), receiver);
    }
    @Override
    public SendResult sendVerificationCodeWithImageValidate(SendSmsVerificationCodeCommand command) {
        validateImageCode(command.getTmpAccessToken(), command.getCode());
        Receiver receiver = new Receiver(MessageType.SMS, command.getPhone());
        return sendVerificationCode(new Scene(command.getScene()), receiver);
    }

    @Override
    public SendResult sendVerificationCode(SendEmailVerificationCodeCommand command) {
        Receiver receiver = new Receiver(MessageType.EMAIL, command.getAddress());
        return sendVerificationCode(new Scene(command.getScene()), receiver);
    }

    @Override
    public ValidateCodeResultDTO validateVerificationCode(ValidateVerificationCodeQuery query) {
        //验证码可配置不验证
        if (closeValidate != null && closeValidate) {
            return new ValidateCodeResultDTO(true);
        }

        Receiver receiver = new Receiver(MessageType.valueOf(query.getMessageType()), query.getAddress());
        SentVerificationCode sentVerificationCode = verificationCodeRepository.findSentVerificationCode(new Scene(query.getScene()), receiver, query.getCode());
        if (sentVerificationCode == null || !sentVerificationCode.getVerificationCode().isValid()) {
            return new ValidateCodeResultDTO(false, "非法的验证码");
        }
        //验证码验证成功后设为已使用
        sentVerificationCode.getVerificationCode().setUsed();
        verificationCodeRepository.saveSentVerificationCode(sentVerificationCode);
        return new ValidateCodeResultDTO(true);
    }

    @Override
    public ValidateCodeResultDTO validateVerificationCodeNoUsed(ValidateVerificationCodeQuery query) {
        //验证码可配置不验证
        if (closeValidate != null && closeValidate) {
            return new ValidateCodeResultDTO(true);
        }

        Receiver receiver = new Receiver(MessageType.valueOf(query.getMessageType()), query.getAddress());
        SentVerificationCode sentVerificationCode = verificationCodeRepository.findSentVerificationCode(new Scene(query.getScene()), receiver, query.getCode());
        if (sentVerificationCode == null || !sentVerificationCode.getVerificationCode().isValid()) {
            return new ValidateCodeResultDTO(false, "非法的验证码");
        }
        return new ValidateCodeResultDTO(true);
    }
    @Override
    public ImageValidateCodeDTO getImageValidateCode() throws IOException {
        ImageValidateCodeDTO dto = new ImageValidateCodeDTO();
        dto.setTmpAccessToken(UUID.randomUUID().toString());
        ImageVerificationCodeUtils utils = new ImageVerificationCodeUtils();
        BufferedImage image = utils.getImage();
        OutputStream out = new FileOutputStream(validateImagePath + dto.getTmpAccessToken() + ".png");
        ImageIO.write(image, "png", out);
        dto.setImageUrl(validateImageUrl + dto.getTmpAccessToken() + ".png");
        String code = utils.getText().toLowerCase();
        redisTemplate.opsForValue().set(dto.getTmpAccessToken(), code, 5 * 60, TimeUnit.SECONDS);
        return dto;
    }

    @Override
    public void validateImageCode(String tmpAccessToken, String code) {
        if (tmpAccessToken == null || code == null){
            throw new ValidationException("验证码错误");
        }
        Object value = redisTemplate.opsForValue().get(tmpAccessToken);
        if (value == null){
            throw new ValidationException("验证码错误");
        }
        redisTemplate.delete(tmpAccessToken);
        if (!code.toLowerCase().equals(value)){
            throw new ValidationException("验证码错误");
        }
    }
    @Override
    public IPage<VerificationCodeDTO> verificationCodeDOList(IPage<VerificationCodeDTO> iPage, String receiverAddress, String scene) {
        IPage<VerificationCodeDO> iPage1 = new Page<>(iPage.getCurrent(), iPage.getSize());
        iPage1 = verificationCodeRepository.verificationCodeDOList(iPage1, receiverAddress, scene);
        List<VerificationCodeDTO> dtos = new ArrayList<>();
        for (VerificationCodeDO verificationCodeDO : iPage1.getRecords()){
            VerificationCodeDTO dto = new VerificationCodeDTO();
            BeanUtils.copyProperties(verificationCodeDO, dto);
            dtos.add(dto);
        }
        iPage.setRecords(dtos);
        iPage.setTotal(iPage1.getTotal());
        iPage.setSize(iPage1.getSize());
        iPage.setCurrent(iPage1.getCurrent());
        iPage.setPages(iPage1.getPages());
        return iPage;
    }

    /**
     * 给指定接收方发送一个验证码
     *
     * @param scene
     * @param receiver
     * @return
     */
    private SendResult sendVerificationCode(Scene scene, Receiver receiver) {
        VerificationCode verificationCode = verificationCodeDomainService.generateVerificationCode(scene, 6);

        boolean result = true;
        SmsScene smsScene = SmsScene.valueOf(scene.getId());

        String resultText = "";
        try {
            String content = JpIamUtils.sendSms(receiver.getAddress(), verificationCode.getCode(), smsScene.getCode());
            JSONObject jsonObject = JSONObject.parseObject(content);
            if (jsonObject.get("error") != null) {
                result = false;
                resultText = jsonObject.get("error").toString();
            }
        } catch (Exception e) {
            result = false;
            resultText = e.getMessage();
        }
        SendResult sendResult = new SendResult(result, resultText, receiver, verificationCode);
//        SendResult sendResult = verificationCodeDomainService.sendVerificationCode(receiver, verificationCode);

        //发送成功记录保存已发送的验证码
        if (sendResult.isSuccess()) {
            SentVerificationCode sentVerificationCode = new SentVerificationCode(sendResult.getReceiver(), sendResult.getVerificationCode());
            verificationCodeRepository.saveSentVerificationCode(sentVerificationCode);
        }
        //发送失败记录发送的日志信息
        else {
            VerificationCodeLog verificationCodeLog = new VerificationCodeLog(sendResult);
            verificationCodeLogRepository.saveVerificationCodeLog(verificationCodeLog);
        }

        return sendResult;
    }
}