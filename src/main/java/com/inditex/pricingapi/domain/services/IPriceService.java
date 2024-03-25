package com.inditex.pricingapi.domain.services;

import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.contracts.PriceSearchParam;

public interface IPriceService {

    Price getByBrandAndProductAndDate(PriceSearchParam searchParam) throws ApiException;
}
