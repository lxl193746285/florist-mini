package com.qy.identity.app.infrastructure.persistence.mybatis;

import com.qy.identity.app.application.dto.EmployeeDO;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.repository.UserRepository;
import com.qy.identity.app.domain.valueobject.UserId;
import com.qy.identity.app.infrastructure.persistence.mybatis.converter.UserConverter;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserDO;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserLogDO;
import com.qy.identity.app.infrastructure.persistence.mybatis.mapper.UserLogMapper;
import com.qy.identity.app.infrastructure.persistence.mybatis.mapper.UserMapper;
import com.qy.rest.sequence.Sequence;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 用户资源库实现
 *
 * @author legendjw
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private UserMapper userMapper;
    private UserConverter userConverter;
    private Sequence sequence;
    private UserLogMapper userLogMapper;

    public UserRepositoryImpl(UserMapper userMapper, UserConverter userConverter, Sequence sequence,UserLogMapper userLogMapper) {
        this.userMapper = userMapper;
        this.userConverter = userConverter;
        this.sequence = sequence;
        this.userLogMapper = userLogMapper;
    }

    @Override
    public User findById(Long id) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserDO::getId, id)
                .eq(UserDO::getIsDeleted, 0)
                .last("limit 1");
        UserDO userDO = userMapper.selectOne(queryWrapper);
        return userConverter.toEntity(userDO);
    }

    @Override
    public User findByAccount(String account) {
        if (StringUtils.isBlank(account)) { return null; }
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .and(i -> i.eq(UserDO::getUsername, account).or().eq(UserDO::getPhone, account).or().eq(UserDO::getEmail, account))
                .eq(UserDO::getIsDeleted, 0)
                .last("limit 1");
        UserDO userDO = userMapper.selectOne(queryWrapper);
        return userConverter.toEntity(userDO);
    }

    @Override
    public User findByUsername(String username) {
        if (StringUtils.isBlank(username)) { return null; }
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getIsDeleted, 0)
                .last("limit 1");
        UserDO userDO = userMapper.selectOne(queryWrapper);
        return userConverter.toEntity(userDO);
    }

    @Override
    public User findByPhone(String phone) {
        if (StringUtils.isBlank(phone)) { return null; }
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserDO::getPhone, phone)
                .eq(UserDO::getIsDeleted, 0)
                .last("limit 1");
        UserDO userDO = userMapper.selectOne(queryWrapper);
        return userConverter.toEntity(userDO);
    }

    @Override
    public User findByEmail(String email) {
        if (StringUtils.isBlank(email)) { return null; }
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserDO::getEmail, email)
                .eq(UserDO::getIsDeleted, 0)
                .last("limit 1");
        UserDO userDO = userMapper.selectOne(queryWrapper);
        return userConverter.toEntity(userDO);
    }

    @Override
    public UserId saveUser(User user) {
        UserDO userDO = userConverter.toDO(user);
        if (userDO.getId() == null) {
            userDO.setId(sequence.nextId());
        }

        if (userMapper.selectById(userDO.getId()) == null) {
            userMapper.insert(userDO);
        }
        else {
            userMapper.updateById(userDO);
        }

        return new UserId(userDO.getId());
    }

    @Override
    public UserLogDO saveUserLog(UserLogDO userLogDO) {
        int insert = userLogMapper.insert(userLogDO);
        return userLogDO;
    }

    @Override
    public EmployeeDO getEmployeeDO(String username) {
        EmployeeDO employeeDO = userMapper.getEmployeeDO(username);
        return employeeDO;
    }
}
