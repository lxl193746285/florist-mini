package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.util.CaptchaUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码服务
 */
@Service
public class CaptchaService {

    /**
     * 验证码缓存（实际项目建议使用Redis）
     * key: captchaKey, value: code
     */
    private final Map<String, CaptchaInfo> captchaCache = new ConcurrentHashMap<>();

    /**
     * 验证码有效期（毫秒）
     */
    private static final long CAPTCHA_EXPIRE_TIME = 5 * 60 * 1000; // 5分钟

    /**
     * 生成验证码
     */
    public Map<String, String> generateCaptcha() {
        // 生成验证码
        CaptchaUtil.CaptchaResult result = CaptchaUtil.generate();

        // 生成唯一key
        String captchaKey = UUID.randomUUID().toString();

        // 保存到缓存
        captchaCache.put(captchaKey, new CaptchaInfo(result.getCode(), System.currentTimeMillis()));

        // 清理过期验证码
        cleanExpiredCaptcha();

        // 返回结果
        Map<String, String> response = new HashMap<>();
        response.put("captchaKey", captchaKey);
        response.put("captchaImage", result.getImageBase64());
        return response;
    }

    /**
     * 验证验证码
     */
    public boolean validateCaptcha(String captchaKey, String code) {
        if (captchaKey == null || code == null) {
            return false;
        }

        CaptchaInfo info = captchaCache.get(captchaKey);
        if (info == null) {
            return false;
        }

        // 检查是否过期
        if (System.currentTimeMillis() - info.getCreateTime() > CAPTCHA_EXPIRE_TIME) {
            captchaCache.remove(captchaKey);
            return false;
        }

        // 验证码不区分大小写
        boolean valid = info.getCode().equalsIgnoreCase(code);

        // 验证后删除（一次性使用）
        if (valid) {
            captchaCache.remove(captchaKey);
        }

        return valid;
    }

    /**
     * 清理过期验证码
     */
    private void cleanExpiredCaptcha() {
        long now = System.currentTimeMillis();
        captchaCache.entrySet().removeIf(entry ->
                now - entry.getValue().getCreateTime() > CAPTCHA_EXPIRE_TIME
        );
    }

    /**
     * 验证码信息
     */
    private static class CaptchaInfo {
        private String code;
        private long createTime;

        public CaptchaInfo(String code, long createTime) {
            this.code = code;
            this.createTime = createTime;
        }

        public String getCode() {
            return code;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}
