package com.inditex.pricingapi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;
    private String error = "internal_server_error";
    private Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(Integer statusCode, String message, String error) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }
}
