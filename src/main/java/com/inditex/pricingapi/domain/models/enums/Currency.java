package com.inditex.pricingapi.domain.models.enums;

public enum Currency {
    EUR,
    GBP;

    @Override
    public String toString() {
        return this.name();
    }
}
