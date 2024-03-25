package com.inditex.pricingapi.controllers.validators;

import com.inditex.pricingapi.domain.exceptions.BadRequestApiException;
import com.inditex.pricingapi.domain.models.constants.DateTime;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchParamValidator {

    public static void validate(String productID, String brandID, String applyAt) throws BadRequestApiException {
        try {
            LocalDateTime.parse(applyAt, DateTimeFormatter.ofPattern(DateTime.DATE_FORMAT_PATTERN));
        } catch (DateTimeException e) {
            throw new BadRequestApiException("invalid parameter {apply_at}");
        }

        try {
            Long.parseLong(productID);
        } catch (NumberFormatException e) {
            throw new BadRequestApiException("invalid parameter {product_id}");
        }

        try {
            Long.parseLong(brandID);
        } catch (NumberFormatException e) {
            throw new BadRequestApiException("invalid parameter {brand_id}");
        }
    }
}
