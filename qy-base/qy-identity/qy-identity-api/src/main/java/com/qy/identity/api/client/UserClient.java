package com.qy.identity.api.client;

import com.qy.identity.api.command.CreateAccountCommand;
import com.qy.identity.api.command.LoginByPasswordCommand;
import com.qy.identity.api.command.ModifyAccountCommand;
import com.qy.identity.api.command.ModifyPasswordCommand;
import com.qy.identity.api.dto.AccessTokenDTO;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.identity.api.dto.UserDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-identity-user")
public interface UserClient {
    /**
     * 根据用户名获取用户详情
     *
     * @param username
     * @return
     */
    @GetMapping("/v4/api/identity/users/detail-users/by-username/{username}")
    UserDetailDTO getUserDetailByUsername(@PathVariable(value = "username") String username);

    /**
     * 根据用户名获取基本用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/v4/api/identity/users/basic-users/by-username/{username}")
    UserBasicDTO getBasicUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 根据手机号获取用户基本信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/v4/api/identity/users/basic-users/by-phone/{phone}")
    UserBasicDTO getBasicUserByPhone(@PathVariable(value = "phone") String phone);

    /**
     * 根据ID获取基本用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/identity/users/basic-users/{id}")
    UserBasicDTO getBasicUserById(@PathVariable(value = "id") Long id);

    /**
     * 根据ID集合获取基本用户信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v4/api/identity/users/basic-users")
    List<UserBasicDTO> getBasicUsersByIds(@RequestParam(value = "ids") List<Long> ids);

    /**
     * 指定用户名是否已经注册
     *
     * @param username
     * @return
     */
    @GetMapping("/v4/api/identity/users/is-username-registered")
    Boolean isUsernameRegistered(
            @RequestParam(value = "username") String username
    );

    /**
     * 指定手机号是否已经注册
     *
     * @param phone
     * @return
     */
    @GetMapping("/v4/api/identity/users/is-phone-registered")
    Boolean isPhoneRegistered(
            @RequestParam(value = "phone") String phone
    );

    /**
     * 指定邮箱是否已经注册
     *
     * @param email
     * @return
     */
    @GetMapping("/v4/api/identity/users/is-email-registered")
    Boolean isEmailRegistered(
            @RequestParam(value = "email") String email
    );

    /**
     * 创建账号
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/identity/users/create-account")
    UserBasicDTO createAccount(
            @Valid @RequestBody CreateAccountCommand command
    );

    /**
     * 修改账号
     *
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/identity/users/{id}/modify-account")
    void modifyAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyAccountCommand command
    );

    /**
     * 修改账号密码
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/identity/users/{id}/modify-password")
    void modifyPassword(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyPasswordCommand command
    );

    /**
     * 密码登录
     */
    @PostMapping("/v4/api/identity/users/login-by-password")
    AccessTokenDTO loginByPassword(
            @Valid @RequestBody LoginByPasswordCommand command);

    /**
     * 获取token
     */
    @GetMapping("/v4/api/identity/users/getAccessToken")
    AccessTokenDTO getAccessToken(
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "user_id") Long userId);

}