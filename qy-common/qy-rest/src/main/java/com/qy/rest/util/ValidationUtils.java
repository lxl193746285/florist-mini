package com.qy.rest.util;

import com.qy.rest.exception.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

/**
 * 验证工具类
 *
 * @author legendjw
 */
public class ValidationUtils {
    /**
     * 获取验证结果第一个字段错误（顺序按照实体字段顺序）
     *
     * @param result
     * @return
     */
    public static FieldError getFirstFieldError(BindingResult result) {
        if (result.hasErrors()) {
            for (Field declaredField : result.getTarget().getClass().getDeclaredFields()) {
                FieldError fieldError = result.getFieldError(declaredField.getName());
                if (fieldError != null) {
                    return fieldError;
                }
            }
        }
        return null;
    }

    /**
     * 处理验证结果
     *
     * @param result
     */
    public static void handleValidationResult(BindingResult result) {
        if (result.hasErrors()) {
            FieldError fieldError = ValidationUtils.getFirstFieldError(result);
            throw new ValidationException(fieldError != null ? fieldError.getDefaultMessage() : "未知验证错误");
        }
    }
}
