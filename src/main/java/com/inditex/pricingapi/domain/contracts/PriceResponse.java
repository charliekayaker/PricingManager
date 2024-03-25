package com.inditex.pricingapi.domain.contracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inditex.pricingapi.domain.models.constants.DateTime;
import com.inditex.pricingapi.domain.models.entities.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {

    @JsonProperty("product_id")
    private Long productID;

    @JsonProperty("brand_id")
    private Long brandID;

    @JsonProperty("price_list")
    private Long priceList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTime.DATE_FORMAT_PATTERN)
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTime.DATE_FORMAT_PATTERN)
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal price;

    public PriceResponse(Price price) {
        this.productID = price.getProduct().getId();
        this.brandID = price.getBrand().getId();
        this.priceList = price.getPriceListID();
        this.startDate = price.getStartDate();
        this.endDate = price.getEndDate();
        this.price = price.getPrice();
    }

    public Long getProductID() {
        return productID;
    }

    public Long getBrandID() {
        return brandID;
    }

    public Long getPriceList() {
        return priceList;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
