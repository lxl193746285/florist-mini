package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.rest.exception.ValidationException;
import lombok.Value;

/**
 * 邮箱
 *
 * @author legendjw
 */
@Value
public class Email implements ValueObject {
    /**
     * 地址
     */
    private String address;

    /**
     * 指定邮箱地址是否是合法的
     *
     * @param address
     * @return
     */
    public static boolean isValid(String address) {
        String pattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return address.matches(pattern);
    }

    public Email(String address) {
        address = address.trim();
        if (!isValid(address)) {
            throw new ValidationException("邮箱地址格式错误");
        }
        this.address = address;
    }
}