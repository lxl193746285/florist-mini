package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.ChangePasswordRequest;
import com.tencent.wxcloudrun.dto.UpdateProfileRequest;
import com.tencent.wxcloudrun.dto.UserInfoVO;
import com.tencent.wxcloudrun.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户信息
     */
    public UserInfoVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return buildUserInfoVO(user);
    }

    /**
     * 修改用户资料
     */
    public void updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新基础信息
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getEmail() != null) {
            // 检查邮箱是否被其他用户使用
            User existUser = userMapper.selectByEmail(request.getEmail());
            if (existUser != null && !existUser.getId().equals(userId)) {
                throw new RuntimeException("邮箱已被其他用户使用");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getRealName() != null) {
            user.setRealName(request.getRealName());
        }

        userMapper.update(user);
    }

    /**
     * 修改密码
     */
    public void changePassword(Long userId, ChangePasswordRequest request) {
        // 验证两次密码是否一致
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次密码不一致");
        }

        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.update(user);
    }

    /**
     * 构建用户信息VO
     */
    private UserInfoVO buildUserInfoVO(User user) {
        UserInfoVO vo = UserInfoVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userType(user.getUserType())
                .userTypeName(user.getUserType() == 1 ? "管理员" : "会员")
                .phone(maskPhone(user.getPhone()))
                .email(maskEmail(user.getEmail()))
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .realName(user.getRealName())
                .gender(user.getGender())
                .build();

        // 查询角色
        List<Role> roles = roleMapper.selectByUserId(user.getId());
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getRoleCode());
        }
        vo.setRoles(roleNames);

        // 根据用户类型查询扩展信息
        if (user.getUserType() == 1) {
            // 管理员
            Admin admin = adminMapper.selectByUserId(user.getId());
            if (admin != null) {
                vo.setEmployeeNo(admin.getEmployeeNo());
                vo.setDepartment(admin.getDepartment());
                vo.setPosition(admin.getPosition());
                vo.setIsSuperAdmin(admin.getIsSuperAdmin() == 1);
            }
        } else {
            // 会员
            Member member = memberMapper.selectByUserId(user.getId());
            if (member != null) {
                vo.setMemberNo(member.getMemberNo());
                vo.setLevel(member.getLevel());
                vo.setLevelName(getMemberLevelName(member.getLevel()));
                vo.setPoints(member.getPoints());
                vo.setBalance(member.getBalance());
            }
        }

        return vo;
    }

    /**
     * 手机号脱敏
     */
    private String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 邮箱脱敏
     */
    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        if (parts[0].length() <= 2) {
            return email;
        }
        return parts[0].substring(0, 2) + "***@" + parts[1];
    }

    /**
     * 获取会员等级名称
     */
    private String getMemberLevelName(Integer level) {
        switch (level) {
            case 1: return "普通会员";
            case 2: return "银卡会员";
            case 3: return "金卡会员";
            case 4: return "钻石会员";
            default: return "未知等级";
        }
    }
}
