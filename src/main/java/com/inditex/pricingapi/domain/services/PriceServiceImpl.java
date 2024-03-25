package com.inditex.pricingapi.domain.services;

import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.exceptions.NotFoundApiException;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.repositories.PriceRepository;
import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PriceServiceImpl implements IPriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getByBrandAndProductAndDate(PriceSearchParam searchParam) throws ApiException {
        Optional<Price> price = priceRepository.findTopByBrandAndProductAndApplicationDate(
                searchParam.getBrandID(), searchParam.getProductID(), searchParam.getApplyAt());

        if (price.isEmpty()) {
            throw new NotFoundApiException("No price found for the given criteria.");
        }

        return price.get();
    }
}
