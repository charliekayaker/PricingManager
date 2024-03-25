package com.inditex.pricingapi.domain.models.entities;

import com.inditex.pricingapi.domain.models.enums.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.EnumType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "price_list_id")
    private Long priceListID;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer priority;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private static final long serialVersionUID = 1L;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPriceListID() {
        return priceListID;
    }

    public void setPriceListID(Long priceListID) {
        this.priceListID = priceListID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
