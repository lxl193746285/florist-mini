package com.qy.rest.advice;

/**
 * 错误信息body
 *
 * @author legendjw
 */
public class ErrorBody {
    private int status;
    private String error;
    private String message;

    public ErrorBody(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
