package com.qy.identity.app.application.service;

import com.qy.identity.app.application.dto.UserBasicDTO;
import com.qy.identity.app.application.dto.UserDetailDTO;

import java.util.List;

/**
 * 用户查询服务
 *
 * @author legendjw
 */
public interface UserQueryService {
    /**
     * 根据用户名查找用户详情
     *
     * @param username
     * @return
     */
    UserDetailDTO getUserDetailByUsername(String username);

    /**
     * 根据用户名获取用户基本信息
     *
     * @param username
     * @return
     */
    UserBasicDTO getBasicUserByUsername(String username);

    /**
     * 根据手机号获取用户基本信息
     *
     * @param phone
     * @return
     */
    UserBasicDTO getBasicUserByPhone(String phone);

    /**
     * 根据用户ID获取用户基本信息
     *
     * @param userId
     * @return
     */
    UserBasicDTO getBasicUserById(Long userId);

    /**
     * 根据用户ID集合获取多个用户基本信息
     *
     * @param userIds
     * @return
     */
    List<UserBasicDTO> getBasicUsers(List<Long> userIds);

    /**
     * 指定用户名是否已经注册
     *
     * @param username
     * @return
     */
    boolean isUsernameRegistered(String username);

    /**
     * 指定手机号是否已经注册
     *
     * @param phone
     * @return
     */
    boolean isPhoneRegistered(String phone);

    /**
     * 指定邮箱是否已经注册
     *
     * @param email
     * @return
     */
    boolean isEmailRegistered(String email);
}
