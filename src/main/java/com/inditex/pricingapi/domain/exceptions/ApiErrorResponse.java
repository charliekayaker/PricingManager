package com.inditex.pricingapi.domain.exceptions;

public class ApiErrorResponse {

    private Integer code;
    private String message;
    private String error;

    public ApiErrorResponse(Integer code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
