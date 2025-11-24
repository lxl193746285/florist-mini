package com.qy.identity.app.application.service.impl;

import com.qy.identity.app.application.assembler.UserAssembler;
import com.qy.identity.app.application.dto.UserBasicDTO;
import com.qy.identity.app.application.dto.UserDetailDTO;
import com.qy.identity.app.application.service.UserQueryService;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.repository.UserRepository;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserDO;
import com.qy.identity.app.infrastructure.persistence.mybatis.mapper.UserMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户查询服务实现
 *
 * @author legendjw
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private UserMapper userMapper;

    public UserQueryServiceImpl(UserRepository userRepository, UserAssembler userAssembler, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailDTO getUserDetailByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? userAssembler.toUserDetail(user) : null;
    }

    @Override
    public UserBasicDTO getBasicUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? userAssembler.toBasicUser(user) : null;
    }

    @Override
    public UserBasicDTO getBasicUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        return user != null ? userAssembler.toBasicUser(user) : null;
    }

    @Override
    public UserBasicDTO getBasicUserById(Long userId) {
        User user = userRepository.findById(userId);
        return user != null ? userAssembler.toBasicUser(user) : null;
    }

    @Override
    public List<UserBasicDTO> getBasicUsers(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(UserDO::getId, userIds).eq(UserDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED);
        List<UserDO> userDOS = userMapper.selectList(queryWrapper);
        return userDOS.stream().map(d -> userAssembler.toBasicUser(d)).collect(Collectors.toList());
    }

    @Override
    public boolean isUsernameRegistered(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean isPhoneRegistered(String phone) {
        return userRepository.findByPhone(phone) != null;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
