package com.qy.authorization.app.application.dto;

import java.io.Serializable;

/**
 * 验证结果
 *
 * @author legendjw
 */
public class ValidateClientResultDTO implements Serializable {
    private boolean isValid;
    private String errorMessage;

    public ValidateClientResultDTO(boolean isValid) {
        this.isValid = isValid;
    }

    public ValidateClientResultDTO(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
