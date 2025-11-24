package com.qy.verification.app.infrastructure.persistence.mybatis.converter;

import org.springframework.stereotype.Component;

/**
 * 布尔类型到BYTE映射
 *
 * @author legendjw
 */
@Component
public class BooleanAsByteMapper {
    public Byte asByte(Boolean bool) {
        return bool ? (byte) 1 : (byte) 0;
    }

    public Boolean asBoolean(Byte bool) {
        return bool.intValue() == 1 ? true : false;
    }
}