package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.rest.exception.ValidationException;
import lombok.Value;

/**
 * 身份证号
 *
 * @author legendjw
 */
@Value
public class IdNumber implements ValueObject {
    private String idNumber;

    public static boolean isValid(String idNumber) {
        String pattern = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        return idNumber.matches(pattern);
    }

    public IdNumber(String idNumber) {
        if (!isValid(idNumber)) {
            throw new ValidationException("身份账号格式错误");
        }

        this.idNumber = idNumber;
    }
}
