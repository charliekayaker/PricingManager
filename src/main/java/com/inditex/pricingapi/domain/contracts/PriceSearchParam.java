package com.inditex.pricingapi.domain.contracts;

import com.inditex.pricingapi.domain.models.constants.DateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PriceSearchParam {

    private LocalDateTime applyAt;
    private Long productID;
    private Long brandID;

    private PriceSearchParam(Builder builder) {
        this.applyAt = builder.applyAt;
        this.productID = builder.productID;
        this.brandID = builder.brandID;
    }

    public LocalDateTime getApplyAt() {
        return applyAt;
    }

    public Long getProductID() {
        return productID;
    }

    public Long getBrandID() {
        return brandID;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private LocalDateTime applyAt;
        private Long productID;
        private Long brandID;

        public Builder applyAt(LocalDateTime applyAt) {
            this.applyAt = applyAt;
            return this;
        }

        public Builder applyAt(String applyAtStr) {
            return applyAt(LocalDateTime.parse(applyAtStr, DateTimeFormatter.ofPattern(DateTime.DATE_FORMAT_PATTERN)));
        }

        public Builder productID(Long productID) {
            this.productID = productID;
            return this;
        }

        public Builder productID(String productIDStr) {
            return productID(Long.parseLong(productIDStr));
        }

        public Builder brandID(Long brandID) {
            this.brandID = brandID;
            return this;
        }

        public Builder brandID(String brandIDStr) {
            return brandID(Long.parseLong(brandIDStr));
        }

        public PriceSearchParam build() {
            return new PriceSearchParam(this);
        }
    }
}
