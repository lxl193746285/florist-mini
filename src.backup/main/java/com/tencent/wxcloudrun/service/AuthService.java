package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务
 */
@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    public LoginResponse login(LoginRequest request) {
        // 1. 查询用户
        User user = userMapper.selectByAccount(request.getAccount());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 记录登录失败日志
            saveLoginLog(user, false, "密码错误");
            throw new RuntimeException("密码错误");
        }

        // 3. 检查用户状态
        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已被禁用");
        }

        // 4. 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getUserType());

        // 5. 更新最后登录信息
        userMapper.updateLastLogin(user.getId(), "127.0.0.1");

        // 6. 记录登录成功日志
        saveLoginLog(user, true, "登录成功");

        // 7. 构建用户信息
        UserInfoVO userInfo = buildUserInfo(user);

        // 8. 返回登录响应
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration())
                .userInfo(userInfo)
                .build();
    }

    /**
     * 会员注册
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerMember(MemberRegisterRequest request) {
        // 1. 验证两次密码是否一致
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次密码不一致");
        }

        // 2. 检查用户名是否存在
        if (userMapper.countByUsername(request.getUsername()) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 3. 检查手机号是否存在
        if (userMapper.countByPhone(request.getPhone()) > 0) {
            throw new RuntimeException("手机号已被注册");
        }

        // 4. 创建用户基础信息
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(2); // 会员
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setStatus(1);
        userMapper.insert(user);

        // 5. 创建会员扩展信息
        Member member = new Member();
        member.setUserId(user.getId());
        member.setMemberNo(generateMemberNo());
        member.setLevel(1); // 默认普通会员
        member.setPoints(0);
        member.setBalance(BigDecimal.ZERO);
        member.setSource(request.getSource() != null ? request.getSource() : "web");
        member.setReferrerId(request.getReferrerId());
        memberMapper.insert(member);

        // 6. 分配默认会员角色
        Role memberRole = roleMapper.selectByRoleCode("MEMBER");
        if (memberRole != null) {
            roleMapper.insertUserRoles(user.getId(), new Long[]{memberRole.getId()});
        }
    }

    /**
     * 管理员注册（需要超级管理员权限，这里简化处理）
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerAdmin(AdminRegisterRequest request) {
        // 1. 检查用户名是否存在
        if (userMapper.countByUsername(request.getUsername()) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 检查手机号是否存在
        if (userMapper.countByPhone(request.getPhone()) > 0) {
            throw new RuntimeException("手机号已被注册");
        }

        // 3. 创建用户基础信息
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(1); // 管理员
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRealName(request.getRealName());
        user.setStatus(1);
        userMapper.insert(user);

        // 4. 创建管理员扩展信息
        Admin admin = new Admin();
        admin.setUserId(user.getId());
        admin.setEmployeeNo(request.getEmployeeNo());
        admin.setDepartment(request.getDepartment());
        admin.setPosition(request.getPosition());
        admin.setIsSuperAdmin(0);
        adminMapper.insert(admin);

        // 5. 分配角色
        if (request.getRoleIds() != null && request.getRoleIds().length > 0) {
            roleMapper.insertUserRoles(user.getId(), request.getRoleIds());
        } else {
            // 默认分配员工角色
            Role employeeRole = roleMapper.selectByRoleCode("EMPLOYEE");
            if (employeeRole != null) {
                roleMapper.insertUserRoles(user.getId(), new Long[]{employeeRole.getId()});
            }
        }
    }

    /**
     * 构建用户信息VO
     */
    private UserInfoVO buildUserInfo(User user) {
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
     * 生成会员编号
     */
    private String generateMemberNo() {
        String prefix = "M" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String memberNo = memberMapper.generateMemberNo(prefix);
        return memberNo != null ? memberNo : prefix + "000001";
    }

    /**
     * 保存登录日志
     */
    private void saveLoginLog(User user, boolean success, String message) {
        LoginLog log = new LoginLog();
        log.setUserId(user.getId());
        log.setUsername(user.getUsername());
        log.setUserType(user.getUserType());
        log.setLoginTime(LocalDateTime.now());
        log.setLoginIp("127.0.0.1");
        log.setStatus(success ? 1 : 0);
        log.setMessage(message);
        loginLogMapper.insert(log);
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
