package com.inditex.pricingapi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestApiException extends ApiException {
    private static final String ERROR = "bad_request";
    private static final Integer STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public BadRequestApiException(String message) {
        super(STATUS_CODE, message, ERROR);
    }
}
