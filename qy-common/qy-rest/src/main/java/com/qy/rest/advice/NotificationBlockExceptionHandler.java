package com.qy.rest.advice;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Sentinel异常处理器，用于触发规则时发送通知
 */
@Component
public class NotificationBlockExceptionHandler implements BlockExceptionHandler {

    // 存储上次发送通知的时间，用于限流
    private final ConcurrentHashMap<String, Long> lastNotifyTimeMap = new ConcurrentHashMap<>();

    // 通知最小间隔（毫秒）
    private static final long NOTIFY_MIN_INTERVAL = TimeUnit.MINUTES.toMillis(5);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        String resource = request.getRequestURI();
        String ruleType = getRuleType(e);
        String message = buildMessage(resource, ruleType, e);

        // 记录日志
        logBlockException(resource, ruleType, e);

        // 发送通知（带频率限制）
        sendNotificationWithRateLimit(resource, ruleType, message);

        // 返回友好的错误响应
        response.setStatus(429);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("X-Message", "系统繁忙，请稍后再试");
        response.getWriter().write("{\"code\": 429, \"message\": \"系统繁忙，请稍后再试\"}");
    }

    private String getRuleType(BlockException e) {
        if (e instanceof FlowException) {
            return "流控规则";
        } else if (e instanceof DegradeException) {
            return "降级规则";
        } else if (e instanceof SystemBlockException) {
            return "系统规则";
        } else if (e instanceof AuthorityException) {
            return "授权规则";
        } else if (e instanceof ParamFlowException) {
            return "热点参数规则";
        } else {
            return "未知规则";
        }
    }

    private String buildMessage(String resource, String ruleType, BlockException e) {
        return String.format("Sentinel规则触发告警\n资源: %s\n规则类型: %s\n触发时间: %s\n异常信息: %s",
                resource, ruleType, new java.util.Date(), e.getMessage());
    }

    private void logBlockException(String resource, String ruleType, BlockException e) {
        // 这里可以使用您的日志框架
        System.err.printf("[Sentinel告警] 资源%s触发%s规则: %s%n", resource, ruleType, e.getMessage());
    }

    private void sendNotificationWithRateLimit(String resource, String ruleType, String message) {
        String key = resource + ":" + ruleType;
        long currentTime = System.currentTimeMillis();
        long lastTime = lastNotifyTimeMap.getOrDefault(key, 0L);

        // 检查是否达到通知最小间隔
        if (currentTime - lastTime > NOTIFY_MIN_INTERVAL) {
            lastNotifyTimeMap.put(key, currentTime);

            // 异步发送通知，避免阻塞请求处理
            new Thread(() -> {
                try {
                    sendNotification(message);
                } catch (Exception ex) {
                    System.err.println("发送通知失败: " + ex.getMessage());
                }
            }).start();
        }
    }

    private void sendNotification(String message) {
        // 这里实现具体的通知逻辑
        // 示例: 发送邮件
        sendEmailNotification(message);

        // 示例: 发送钉钉消息
        sendDingTalkNotification(message);

        // 示例: 调用Webhook
        sendWebhookNotification(message);
    }

    private void sendEmailNotification(String message) {
        // 实现邮件发送逻辑
        // 可以使用Spring的JavaMailSender
        System.out.println("发送邮件通知: " + message);
    }

    private void sendDingTalkNotification(String message) {
        // 实现钉钉机器人通知
        try {
            String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=YOUR_TOKEN";
            String jsonBody = String.format("{\"msgtype\": \"text\", \"text\": {\"content\": \"%s\"}}",
                    message.replace("\"", "\\\""));

            // 使用HTTP客户端发送请求
            // 这里省略具体实现，可以使用RestTemplate或HttpClient
            System.out.println("发送钉钉通知: " + message);
        } catch (Exception e) {
            System.err.println("发送钉钉通知失败: " + e.getMessage());
        }
    }

    private void sendWebhookNotification(String message) {
        // 实现通用Webhook通知
        System.out.println("发送Webhook通知: " + message);
    }
}