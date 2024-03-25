package com.inditex.pricingapi.controllers;

import com.inditex.pricingapi.domain.exceptions.ApiErrorResponse;
import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.exceptions.BadRequestApiException;
import com.inditex.pricingapi.domain.exceptions.NotFoundApiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleApiException(ApiException e) {
        return new ApiErrorResponse(e.getStatusCode(), e.getMessage(), e.getError());
    }

    @ExceptionHandler(value = {NotFoundApiException.class, HttpClientErrorException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleResourceNotFoundApiException(NotFoundApiException e) {
        return new ApiErrorResponse(e.getStatusCode(), e.getMessage(), e.getError());
    }

    @ExceptionHandler(BadRequestApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestApiException(BadRequestApiException e) {
        return new ApiErrorResponse(e.getStatusCode(), e.getMessage(), e.getError());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGlobalExceptions(Exception e) {
        return new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                "internal_server_error");
    }
}
