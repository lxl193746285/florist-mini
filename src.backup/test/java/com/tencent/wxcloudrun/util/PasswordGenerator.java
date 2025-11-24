package com.tencent.wxcloudrun.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin123";
        String encoded = encoder.encode(password);
        System.out.println("密码: " + password);
        System.out.println("BCrypt哈希: " + encoded);

        // 验证测试
        boolean matches = encoder.matches(password, encoded);
        System.out.println("验证结果: " + matches);

        // 验证原有哈希
        String oldHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";
        boolean matchesOld = encoder.matches(password, oldHash);
        System.out.println("旧哈希验证: " + matchesOld);
    }
}
