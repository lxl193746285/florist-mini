package com.qy.base.interfaces.identity.api;

import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.identity.app.application.command.CreateAccountCommand;
import com.qy.identity.app.application.command.LoginByPasswordCommand;
import com.qy.identity.app.application.command.ModifyAccountCommand;
import com.qy.identity.app.application.command.ModifyPasswordCommand;
import com.qy.identity.app.application.dto.UserBasicDTO;
import com.qy.identity.app.application.dto.UserDetailDTO;
import com.qy.identity.app.application.service.AccountCommandService;
import com.qy.identity.app.application.service.UserCommandService;
import com.qy.identity.app.application.service.UserQueryService;
import com.qy.identity.app.domain.valueobject.UserId;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户内部服务接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/api/identity/users")
public class UserApiController {
    private AccountCommandService accountCommandService;
    private UserCommandService userCommandService;
    private UserQueryService userQueryService;
    private HttpServletRequest request;

    public UserApiController(AccountCommandService accountCommandService, UserCommandService userCommandService,
                             UserQueryService userQueryService, HttpServletRequest request) {
        this.accountCommandService = accountCommandService;
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.request = request;
    }

    /**
     * 根据用户名获取用户详情
     *
     * @param username
     * @return
     */
    @GetMapping("/detail-users/by-username/{username}")
    public ResponseEntity<UserDetailDTO> getUserDetailByUsername(
            @PathVariable(value = "username") String username
    ) {
        UserDetailDTO userDetailDTO = userQueryService.getUserDetailByUsername(username);
        return ResponseUtils.ok().body(userDetailDTO);
    }

    /**
     * 根据用户名获取用户基本信息
     *
     * @param username
     * @return
     */
    @GetMapping("/basic-users/by-username/{username}")
    public ResponseEntity<UserBasicDTO> getBasicUserByUsername(
            @PathVariable(value = "username") String username
    ) {
        UserBasicDTO userBasicDTO = userQueryService.getBasicUserByUsername(username);
        return ResponseUtils.ok().body(userBasicDTO);
    }

    /**
     * 根据手机号获取用户基本信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/basic-users/by-phone/{phone}")
    public ResponseEntity<UserBasicDTO> getBasicUserByPhone(
            @PathVariable(value = "phone") String phone
    ) {
        UserBasicDTO userBasicDTO = userQueryService.getBasicUserByPhone(phone);
        return ResponseUtils.ok().body(userBasicDTO);
    }

    /**
     * 根据ID获取用户基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/basic-users/{id}")
    public ResponseEntity<UserBasicDTO> getBasicUserById(
            @PathVariable(value = "id") Long id
    ) {
        UserBasicDTO userBasicDTO = userQueryService.getBasicUserById(id);
        return ResponseUtils.ok().body(userBasicDTO);
    }

    /**
     * 根据ID集合获取用户名基本信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/basic-users")
    public ResponseEntity<List<UserBasicDTO>> getBasicUsersByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return ResponseUtils.ok().body(userQueryService.getBasicUsers(ids));
    }

    /**
     * 指定用户名是否已经注册
     *
     * @param username
     * @return
     */
    @GetMapping("/is-username-registered")
    public ResponseEntity<Boolean> isUsernameRegistered(
            @RequestParam(value = "username") String username
    ) {
        return ResponseUtils.ok().body(userQueryService.isUsernameRegistered(username));
    }

    /**
     * 指定手机号是否已经注册
     *
     * @param phone
     * @return
     */
    @GetMapping("/is-phone-registered")
    public ResponseEntity<Boolean> isPhoneRegistered(
            @RequestParam(value = "phone") String phone
    ) {
        return ResponseUtils.ok().body(userQueryService.isPhoneRegistered(phone));
    }

    /**
     * 指定邮箱是否已经注册
     *
     * @param email
     * @return
     */
    @GetMapping("/is-email-registered")
    public ResponseEntity<Boolean> isEmailRegistered(
            @RequestParam(value = "email") String email
    ) {
        return ResponseUtils.ok().body(userQueryService.isEmailRegistered(email));
    }

    /**
     * 创建账号
     *
     * @param command
     * @return
     */
    @PostMapping("/create-account")
    public ResponseEntity<UserBasicDTO> createAccount(
            @Valid @RequestBody CreateAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        UserId userId = userCommandService.createAccount(command);

        return ResponseUtils.ok("创建账号成功").body(userQueryService.getBasicUserById(userId.getId()));
    }

    /**
     * 修改账号
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}/modify-account")
    public ResponseEntity<Object> modifyAccount(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyAccountCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUserId(id);
        userCommandService.modifyAccount(command);

        return ResponseUtils.ok("修改账号成功").build();
    }

    /**
     * 修改账号密码
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}/modify-password")
    public ResponseEntity<Object> modifyPassword(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUserId(id);
        userCommandService.modifyUserPassword(command);

        return ResponseUtils.ok("账号密码修改成功").build();
    }

    /**
     * 密码登录
     */
    @PostMapping("/login-by-password")
    public ResponseEntity<AccessTokenDTO> loginByPassword(
            @Valid @RequestBody LoginByPasswordCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        AccessTokenDTO accessTokenDTO = accountCommandService.loginByPassword(command, request);
        return ResponseUtils.created("登录成功").body(accessTokenDTO);
    }

    /**
     * 获取token
     * @param clientId
     * @param userId
     * @param response
     * @return
     */
    @GetMapping("/getAccessToken")
    public AccessTokenDTO getAccessToken(
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "user_id") Long userId,
            HttpServletResponse response
    ) {
        AccessTokenDTO accessTokenDTO = accountCommandService.generateAccessToken(clientId, userId);
        return accessTokenDTO;
    }

}