package com.qy.identity.app.application.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.service.UserCommandService;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.repository.UserRepository;
import com.qy.identity.app.domain.service.AvatarDomainService;
import com.qy.identity.app.domain.service.UserDomainService;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.verification.api.client.VerificationCodeClient;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import com.qy.identity.app.domain.valueobject.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author legendjw
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private UserRepository userRepository;
    private UserDomainService userDomainService;
    private VerificationCodeClient verificationCodeClient;
    private AvatarDomainService avatarDomainService;
    private AttachmentClient attachmentClient;

    public UserCommandServiceImpl(UserRepository userRepository, UserDomainService userDomainService, VerificationCodeClient verificationCodeClient, AvatarDomainService avatarDomainService, AttachmentClient attachmentClient) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.verificationCodeClient = verificationCodeClient;
        this.avatarDomainService = avatarDomainService;
        this.attachmentClient = attachmentClient;
    }

    @Override
    public UserId createAccount(CreateAccountCommand command) {
        //验证注册来源
        UserSource userSource = UserSource.getById(command.getSource());
        if (userSource == null) {
            throw new ValidationException("非法的注册来源");
        }
        //创建用户
        return userDomainService.create(
                command.getName(),
                StringUtils.isNotBlank(command.getUsername()) ? new Username(command.getUsername()) : null,
                StringUtils.isNotBlank(command.getPhone()) ? new PhoneNumber(command.getPhone()) : null,
                StringUtils.isNotBlank(command.getEmail()) ? new Email(command.getEmail()) : null,
                null,
                userSource
        );
    }

    @Override
    public void modifyAccount(ModifyAccountCommand command) {
        User user = findUserById(command.getUserId());
        if (StringUtils.isNotBlank(command.getName())) {
            user.modifyName(command.getName());
            userRepository.saveUser(user);
        }
        if (command.getUsername() != null) {
            User findUser = userRepository.findByUsername(command.getUsername());
            if (findUser != null && !findUser.getUserId().equals(user.getUserId())) {
                throw new ValidationException("指定用户名已经注册");
            }
            if (user.getUsername() == null || !user.getUsername().getName().equals(command.getUsername())) {
                userDomainService.changeUsername(user.getUserId(), new Username(command.getUsername()));
            }
        }
        if (command.getPhone() != null) {
            User findUser = userRepository.findByPhone(command.getPhone());
            if (findUser != null && !findUser.getUserId().equals(user.getUserId())) {
                throw new ValidationException("指定手机号已经注册");
            }
            if (user.getPhone() == null || !user.getPhone().getNumber().equals(command.getPhone())) {
                userDomainService.changePhone(user.getUserId(), new PhoneNumber(command.getPhone()));
            }
        }
        if (command.getEmail() != null) {
            User findUser = userRepository.findByEmail(command.getEmail());
            if (findUser != null && !findUser.getUserId().equals(user.getUserId())) {
                throw new ValidationException("指定邮箱已经注册");
            }
            if (user.getEmail() == null || !user.getEmail().getAddress().equals(command.getEmail())) {
                userDomainService.changeEmail(user.getUserId(), new Email(command.getEmail()));
            }
        }
    }

    @Override
    public void modifyUserInfo(ModifyUserInfoCommand command) {
        User user = findUserById(command.getUserId());
        user.modifyName(command.getName());
        //修改头像
        if (command.getAvatarAttachmentId() != null && command.getAvatarAttachmentId().longValue() > 0L) {
            Avatar avatar = avatarDomainService.generateAvatarByAttachment(attachmentClient.getAttachment(command.getAvatarAttachmentId()), user.getUserId().getId().toString());
            user.modifyAvatar(avatar);
        }
        userRepository.saveUser(user);
    }

    @Override
    public void changeUserPhone(ChangePhoneCommand command) {
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("CHANGE_PHONE", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        userDomainService.changePhone(new UserId(command.getUserId()), new PhoneNumber(command.getPhone()));
    }

    @Override
    public void changeUsername(ChangeUsernameCommand command) {
        User user = findUserById(command.getUserId());
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("CHANGE_USERNAME", "SMS", user.getPhone().getNumber(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        userDomainService.changeUsername(user.getUserId(), new Username(command.getUsername()));
    }

    @Override
    public void modifyUserPasswordByPhone(ModifyPasswordByPhoneCommand command) {
        User user = findUserById(command.getUserId());
        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("MODIFY_USER_PASSWORD", "SMS", user.getPhone().getNumber(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }
        userDomainService.modifyPassword(user.getUserId(), new Password(command.getPassword()));
    }

    @Override
    public void modifyUserPasswordByOldPassword(ModifyPasswordByOldPasswordCommand command) {
        User user = findUserById(command.getUserId());
        //验证旧密码
        if (!user.getPassword().matches(command.getOldPassword())) {
            throw new ValidationException("旧密码错误");
        }
        userDomainService.modifyPassword(user.getUserId(), new Password(command.getPassword()));
    }

    @Override
    public void modifyUserPassword(ModifyPasswordCommand command) {
        User user = findUserById(command.getUserId());
        userDomainService.modifyPassword(user.getUserId(), new Password(command.getPassword()));
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    private User findUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("未找到对应的用户");
        }
        return user;
    }
}
