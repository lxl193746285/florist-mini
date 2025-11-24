package com.qy.identity.app.domain.exception;

import com.qy.rest.exception.ValidationException;

/**
 * 手机号已存在异常
 *
 * @author legendjw
 */
public class PhoneNumberExistException extends ValidationException {
    public PhoneNumberExistException() {
    }

    public PhoneNumberExistException(String message) {
        super(message);
    }

    public PhoneNumberExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberExistException(Throwable cause) {
        super(cause);
    }

    public PhoneNumberExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
