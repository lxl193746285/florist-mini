package com.qy.identity.app.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.event.*;
import com.qy.identity.app.domain.repository.UserRepository;
import com.qy.identity.app.domain.service.UserDomainService;
import com.qy.identity.app.domain.service.AvatarDomainService;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserLogDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.sequence.Sequence;
import com.qy.identity.app.domain.event.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 账号领域服务实现
 *
 * @author legendjw
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {
    private UserRepository userRepository;
    private AvatarDomainService avatarDomainService;
    private ApplicationEventPublisher applicationEventPublisher;
    private ServiceMatcher serviceMatcher;
    private final Destination.Factory destinationFactory;
    private Sequence sequence;

    public UserDomainServiceImpl(UserRepository userRepository, AvatarDomainService avatarDomainService, ApplicationEventPublisher applicationEventPublisher, ServiceMatcher serviceMatcher, Destination.Factory destinationFactory, Sequence sequence) {
        this.userRepository = userRepository;
        this.avatarDomainService = avatarDomainService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.serviceMatcher = serviceMatcher;
        this.destinationFactory = destinationFactory;
        this.sequence = sequence;
    }

    @Override
    public UserId create(String nickname, Username username, PhoneNumber phone, Email email, Password password, UserSource source) {
        //验证用户名
        if (username != null) {
            if (userRepository.findByUsername(username.getName()) != null) {
                throw new ValidationException("指定用户名已经注册");
            }
        }
        //验证手机号
        if (phone != null) {
            if (userRepository.findByPhone(phone.getNumber()) != null) {
                throw new ValidationException("指定手机号已经注册");
            }
        }
        //验证邮箱
        if (email != null) {
            if (userRepository.findByEmail(email.getAddress()) != null) {
                throw new ValidationException("指定邮箱已经注册");
            }
        }
        UserId userId = new UserId(sequence.nextId());
        //验证密码
        Password hashPassword = password != null ? password : Password.defaultPassword();
        //昵称处理
        String finalNickname = StringUtils.isNotBlank(nickname) ? nickname : generateRandomNickname();
        //生成头像
        Avatar avatar = avatarDomainService.generateAvatarByName(nickname, userId.getId().toString());

        User user = User.builder()
                .userId(userId)
                .name(finalNickname)
                .username(username)
                .phone(phone)
                .email(email)
                .password(hashPassword)
                .source(source)
                .avatar(avatar)
                .build();

        userRepository.saveUser(user);

        //发布用户已创建事件
        applicationEventPublisher.publishEvent(new UserCreatedEvent(
                this,
                serviceMatcher.getBusId(),
                destinationFactory.getDestination("**"),
                userId.getId(),
                user.getName(),
                user.getUsername() != null ? user.getUsername().getName() : null,
                user.getPhone() != null ? user.getPhone().getNumber() : null,
                user.getEmail() != null ? user.getEmail().getAddress() : null,
                user.getAvatar() != null ? user.getAvatar().getUrl() : null,
                user.getSource().getId()
        ));

        return userId;
    }

    @Override
    public void changePhone(UserId userId, PhoneNumber phoneNumber) {
        User user = findUserById(userId);
        if (userRepository.findByPhone(phoneNumber.getNumber()) != null) {
            throw new ValidationException("指定手机号已经注册");
        }

        PhoneNumber oldPhoneNumber = user.getPhone();
        user.changePhone(phoneNumber);
        userRepository.saveUser(user);

        //发布用户手机号已变更事件
        applicationEventPublisher.publishEvent(new UserPhoneChangedEvent(
                this,
                serviceMatcher.getBusId(),
                destinationFactory.getDestination("**"),
                user.getUserId().getId(),
                oldPhoneNumber.getNumber(),
                user.getPhone().getNumber()
        ));
    }

    @Override
    public void changeEmail(UserId userId, Email email) {
        User user = findUserById(userId);
        if (userRepository.findByEmail(email.getAddress()) != null) {
            throw new ValidationException("指定邮箱已经注册");
        }

        user.changeEmail(email);
        userRepository.saveUser(user);
    }

    @Override
    public void changeUsername(UserId userId, Username username) {
        User user = findUserById(userId);
        Username oldUsername = user.getUsername();
        if (username.equals(user.getUsername())) {
            throw new ValidationException("更换的用户名不可与原用户名相同");
        }
        if (userRepository.findByUsername(username.getName()) != null) {
            throw new ValidationException("指定用户名已经被注册");
        }

        user.changeUsername(username);
        userRepository.saveUser(user);

        //发布用户名已变更事件
        applicationEventPublisher.publishEvent(new UserUsernameChangedEvent(
                this,
                serviceMatcher.getBusId(),
                destinationFactory.getDestination("**"),
                user.getUserId().getId(),
                oldUsername.getName(),
                user.getUsername().getName()
        ));
    }

    @Override
    public void modifyPassword(UserId userId, Password password) {
        User user = findUserById(userId);
        user.modifyPassword(password);
        userRepository.saveUser(user);
    }

    @Override
    public void login(UserId userId, String clientId, String userAgent, String ip) {
        User user = findUserById(userId);

        //用户登录
        user.login();

        //发布用户已登录事件
        applicationEventPublisher.publishEvent(new UserLoginEvent(
                this,
                serviceMatcher.getBusId(),
                destinationFactory.getDestination("**"),
                user.getUserId().getId(),
                clientId,
                userAgent,
                ip
        ));

        //记录登录日志
        try{
            System.out.println("记录日志---------------------------------");
            UserLogDO userLogDO = new UserLogDO();
            userLogDO.setUserId(user.getUserId().getId());
            userLogDO.setAction("login");
            userLogDO.setUserAgent(userAgent);
            userLogDO.setClient(clientId);
            userLogDO.setIp(ip);
            userLogDO.setData(" ");
            userLogDO.setCreateTime(LocalDateTime.now());
            userRepository.saveUserLog(userLogDO);
            System.out.println("userLogDO---------------------------------"+ JSON.toJSONString(userLogDO));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void logout(UserId userId, String clientId, String userAgent, String ip) {
        User user = findUserById(userId);

        //发布用户已退出事件
        applicationEventPublisher.publishEvent(new UserLogoutEvent(
                this,
                serviceMatcher.getBusId(),
                destinationFactory.getDestination("**"),
                user.getUserId().getId(),
                clientId,
                userAgent,
                ip
        ));
        //记录登录日志
        try{
            System.out.println("记录日志---------------------------------");
            UserLogDO userLogDO = new UserLogDO();
            userLogDO.setUserId(user.getUserId().getId());
            userLogDO.setAction("logout");
            userLogDO.setUserAgent(userAgent);
            userLogDO.setClient(clientId);
            userLogDO.setIp(ip);
            userLogDO.setData(" ");
            userLogDO.setCreateTime(LocalDateTime.now());
            userRepository.saveUserLog(userLogDO);
            System.out.println("userLogDO---------------------------------"+ JSON.toJSONString(userLogDO));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成随机昵称
     *
     * @return
     */
    private String generateRandomNickname() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    private User findUserById(UserId userId) {
        User user = userRepository.findById(userId.getId());
        if (user == null) {
            throw new NotFoundException("未找到对应的用户");
        }
        return user;
    }
}
