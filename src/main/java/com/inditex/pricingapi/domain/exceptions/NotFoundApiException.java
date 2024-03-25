package com.inditex.pricingapi.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundApiException extends ApiException {

    private static final String ERROR = "not_found";
    private static final Integer STATUS_CODE = HttpStatus.NOT_FOUND.value();

    public NotFoundApiException(String message) {
        super(STATUS_CODE, message, ERROR);
    }
}
