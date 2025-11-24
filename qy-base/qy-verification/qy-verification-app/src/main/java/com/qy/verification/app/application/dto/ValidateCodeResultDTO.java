package com.qy.verification.app.application.dto;

import java.io.Serializable;

/**
 * 验证结果
 *
 * @author legendjw
 */
public class ValidateCodeResultDTO implements Serializable {
    private boolean isValid;
    private String errorMessage;

    public ValidateCodeResultDTO(boolean isValid) {
        this.isValid = isValid;
    }

    public ValidateCodeResultDTO(boolean isValid, String errorMessage) {
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
