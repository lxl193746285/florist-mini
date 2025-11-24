package com.qy.security.permission.scope;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注权限规则范围
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface SecurityPermissionScope {
    /**
     * 范围唯一标示
     *
     * @return
     */
    String value() default "";
}
