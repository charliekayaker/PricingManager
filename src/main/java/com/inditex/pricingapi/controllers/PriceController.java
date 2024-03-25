package com.inditex.pricingapi.controllers;

import com.inditex.pricingapi.controllers.validators.SearchParamValidator;
import com.inditex.pricingapi.domain.contracts.PriceResponse;
import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.services.IPriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private IPriceService priceService;

    @Autowired
    public PriceController(IPriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/search")
    public ResponseEntity<PriceResponse> searchPrice(
            @RequestParam("product_id") String productID,
            @RequestParam("brand_id") String brandID,
            @RequestParam("apply_at") String applyAt) throws ApiException {

        SearchParamValidator.validate(productID, brandID, applyAt);

        PriceSearchParam searchParam = PriceSearchParam.Builder()
                .productID(productID)
                .brandID(brandID)
                .applyAt(applyAt).build();

        Price price = priceService.getByBrandAndProductAndDate(searchParam);

        PriceResponse response = new PriceResponse(price);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
