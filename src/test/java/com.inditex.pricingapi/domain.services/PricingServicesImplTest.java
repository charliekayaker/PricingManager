package com.inditex.pricingapi.domain.services;

class PricingServicesImplTest {
    private static PriceRepository mockedPriceRepository;
    private static PriceServiceImpl service;
    private Price priceItem1;
    private Price priceItem2;

    testGetByProductAndBrand_whenIsSuccess_shouldReturnPriceItem();

    casesOfSuccessByAppliedDate();

    testGetByProductAndBrand_whenPriceNotExists_shouldReturnNotFoundApiException();

    testGetByProductAndBrand_whenProviderFails_shouldReturnApiException();
}
