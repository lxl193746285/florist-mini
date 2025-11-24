package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.rest.exception.ValidationException;
import lombok.Value;

/**
 * 手机号码
 *
 * @author legendjw
 */
@Value
public class PhoneNumber implements ValueObject {
    /**
     * 号码
     */
    private String number;

    /**
     * 指定手机号码是否是合法的
     *
     * @param number
     * @return
     */
    public static boolean isValid(String number) {
        String pattern = "^1[1-9]{1}\\d{9}$";
        return number.matches(pattern);
    }

    public PhoneNumber(String number) {
        number = number.trim();
        if (!isValid(number)) {
            throw new ValidationException("手机号码格式错误");
        }
        this.number = number;
    }
}