package com.inditex.pricingapi.domain.services;

import org.junit.jupiter.api.Test;

class PricingServicesImplTest {
    private static PriceRepository mockedPriceRepository;
    private static PriceServiceImpl service;
    private Price priceItem1;
    private Price priceItem2;

    @Test
    testGetByProductAndBrand_whenIsSuccess_shouldReturnPriceItem() throws Exception{}

    @Test
    casesOfSuccessByAppliedDate() throws Exception{}

    @Test
    testGetByProductAndBrand_whenPriceNotExists_shouldReturnNotFoundApiException() throws Exception{}

    @Test
    testGetByProductAndBrand_whenProviderFails_shouldReturnApiException() throws Exception{}
}
