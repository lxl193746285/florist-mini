package com.qy.identity.app.application.service.impl;

import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.authorization.api.command.RefreshAccessTokenCommand;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.authorization.api.dto.ValidateClientResultDTO;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.command.*;
import com.qy.identity.app.application.dto.EmployeeDO;
import com.qy.identity.app.application.service.AccountCommandService;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.repository.UserRepository;
import com.qy.identity.app.domain.service.UserDomainService;
import com.qy.identity.app.domain.valueobject.Password;
import com.qy.identity.app.domain.valueobject.PhoneNumber;
import com.qy.identity.app.domain.valueobject.UserId;
import com.qy.identity.app.domain.valueobject.Username;
import com.qy.member.api.client.AccountClient;
import com.qy.member.api.command.ModifyPasswordCommand;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.SessionContext;
import com.qy.verification.api.client.VerificationCodeClient;
import com.qy.verification.api.dto.ValidateCodeResultDTO;
import com.qy.verification.api.query.ValidateVerificationCodeQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 账号命令服务实现
 *
 * @author legendjw
 */
@Service
public class AccountCommandServiceImpl implements AccountCommandService {
    private UserRepository userRepository;
    private UserDomainService userDomainService;
    private VerificationCodeClient verificationCodeClient;
    private AuthorizationClient authorizationClient;
    private OrganizationClient organizationClient;

    public AccountCommandServiceImpl(UserRepository userRepository, UserDomainService userDomainService, VerificationCodeClient verificationCodeClient, AuthorizationClient authorizationClient, OrganizationClient organizationClient) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.verificationCodeClient = verificationCodeClient;
        this.authorizationClient = authorizationClient;
        this.organizationClient = organizationClient;
    }
    @Autowired
    private AccountClient accountClient;

    @Override
    public AccessTokenDTO registerAccount(RegisterAccountCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("REGISTER", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO codeResultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!codeResultDTO.isValid()) {
            throw new ValidationException(codeResultDTO.getErrorMessage());
        }

        //验证注册来源
        UserSource userSource = UserSource.getById(command.getSource());
        if (userSource == null) {
            throw new ValidationException("非法的注册来源");
        }

        //创建用户
        UserId userId = userDomainService.create(
                command.getName(),
                StringUtils.isNotBlank(command.getUsername()) ? new Username(command.getUsername()) : null,
                new PhoneNumber(command.getPhone()),
                null,
                new Password(command.getPassword()),
                userSource
        );

        //生成访问令牌
        return generateAccessToken(command.getClientId(), userId.getId());
    }

    @Override
    public AccessTokenDTO loginByPassword(LoginByPasswordCommand command, HttpServletRequest request) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证账号以及密码
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++command.getUsername():  " + command.getUsername());
        User user = userRepository.findByAccount(command.getUsername());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++User:  " + user);
        if (user == null) {
            throw new NotFoundException("此账号尚未注册");
        }
        EmployeeDO employeeDO = userRepository.getEmployeeDO(command.getUsername());
        if (employeeDO == null) {
            throw new ValidationException("未找到组织人员");
        }
        if (employeeDO.getStatus() != null && employeeDO.getStatus() == 0) {
            throw new NotFoundException("此账号已被禁用");
        }
        if (!user.getPassword().matches(command.getPassword())) {
            throw new ValidationException("账号密码错误");
        }
        List<OrganizationBasicDTO> joinOrganizations = organizationClient.getUserJoinOrganizations(user.getUserId().getId());
        if (CollectionUtils.isEmpty(joinOrganizations)) {
            throw new ValidationException("未找到组织");
        }
        userDomainService.login(user.getUserId(), command.getClientId(), request.getHeader("User-Agent"), request.getRemoteAddr());

        return generateAccessToken(command.getClientId(), user.getUserId().getId());
    }

    @Override
    public AccessTokenDTO loginByPhone(LoginByPhoneCommand command, HttpServletRequest request) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证验证码
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("LOGIN", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO codeResultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!codeResultDTO.isValid()) {
            throw new ValidationException(codeResultDTO.getErrorMessage());
        }

        //验证用户
        User user = userRepository.findByPhone(command.getPhone());
        if (user == null) {
            throw new NotFoundException("此账号尚未注册");
        }

        List<OrganizationBasicDTO> joinOrganizations = organizationClient.getUserJoinOrganizations(user.getUserId().getId());
        if (CollectionUtils.isEmpty(joinOrganizations)) {
            throw new ValidationException("未找到组织");
        }
        userDomainService.login(user.getUserId(), command.getClientId(), request.getHeader("User-Agent"), request.getRemoteAddr());

        return generateAccessToken(command.getClientId(), user.getUserId().getId());
    }

    @Override
    public AccessTokenDTO refreshToken(RefreshTokenCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        RefreshAccessTokenCommand refreshAccessTokenCommand = new RefreshAccessTokenCommand();
        refreshAccessTokenCommand.setContextId(SessionContext.contextId);
        refreshAccessTokenCommand.setClientId(command.getClientId());
        refreshAccessTokenCommand.setRefreshToken(command.getRefreshToken());
        return authorizationClient.refreshAccessToken(refreshAccessTokenCommand);
    }

    @Override
    public void retrievePasswordByPhone(RetrievePasswordByPhoneCommand command) {
        //验证客户端
        validateClient(command.getClientId(), command.getClientSecret());

        //验证码验证
        ValidateVerificationCodeQuery verificationCodeQuery = new ValidateVerificationCodeQuery("RETRIEVE_PASSWORD", "SMS", command.getPhone(), command.getVerificationCode());
        ValidateCodeResultDTO resultDTO = verificationCodeClient.validateVerificationCode(verificationCodeQuery);
        if (!resultDTO.isValid()) {
            throw new ValidationException(resultDTO.getErrorMessage());
        }

        ModifyPasswordCommand passwordCommand = new ModifyPasswordCommand();
        passwordCommand.setPhone(command.getPhone());
        passwordCommand.setPassword(command.getPassword());
        accountClient.modifyPasswordByPhoneAndVerificationCode(passwordCommand);

    }

    @Override
    public void logout(LogoutCommand command, HttpServletRequest request) {
        userDomainService.logout(new UserId(command.getUserId()), command.getClientId(), request.getHeader("User-Agent"), request.getRemoteAddr());
    }

    /**
     * 生成访问令牌
     *
     * @param clientId
     * @param userId
     * @return
     */
    @Override
    public AccessTokenDTO generateAccessToken(String clientId, Long userId) {
        GenerateAccessTokenCommand userAccessTokenCommand = new GenerateAccessTokenCommand();
        userAccessTokenCommand.setContextId(SessionContext.contextId);
        userAccessTokenCommand.setClientId(clientId);
        userAccessTokenCommand.setUserId(userId.toString());
        return authorizationClient.generateAccessToken(userAccessTokenCommand);
    }

    /**
     * 验证客户端
     *
     * @param clientId
     * @param clientSecret
     */
    private void validateClient(String clientId, String clientSecret) {
        //客户端验证
        ValidateClientResultDTO clientResultDTO = authorizationClient.validateClient(clientId, clientSecret);
        if (!clientResultDTO.isValid()) {
            throw new ValidationException(clientResultDTO.getErrorMessage());
        }
    }
}