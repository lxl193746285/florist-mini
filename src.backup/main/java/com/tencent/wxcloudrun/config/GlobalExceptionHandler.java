package com.tencent.wxcloudrun.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 将错误信息放在 response header 中，符合 RESTful 最佳实践
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 设置错误消息到响应头
     */
    private void setErrorMessage(HttpServletResponse response, String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            response.setHeader("X-Message", encodedMessage);
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to encode error message", e);
            response.setHeader("X-Message", "Internal server error");
        }
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException(RuntimeException e, HttpServletResponse response) {
        logger.error("Business exception: ", e);
        setErrorMessage(response, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleValidationException(MethodArgumentNotValidException e, HttpServletResponse response) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.error("Validation exception: {}", message);
        setErrorMessage(response, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Void> handleBindException(BindException e, HttpServletResponse response) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.error("Bind exception: {}", message);
        setErrorMessage(response, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {
        logger.error("Illegal argument exception: ", e);
        setErrorMessage(response, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e, HttpServletResponse response) {
        logger.error("Unhandled exception: ", e);
        setErrorMessage(response, "服务器内部错误");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
