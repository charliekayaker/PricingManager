package com.inditex.pricingapi.domain.services;

import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.models.entities.Price;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PricingServicesImplTest {

    private static PriceRepository mockedPriceRepository;
    private static PriceServiceImpl service;
    private Price priceItem1;
    private Price priceItem2;

    @Test
    void testGetByProductAndBrand_whenIsSuccess_shouldReturnPriceItem() throws Exception{
        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(applyAt)
                .productID(35455L)
                .brandID(1L).build();

        when(mockedPriceRepository.findTopByBrandAndProductAndDate(searchParam)).thenReturn(Optional.of(expectedPrice));

        Price priceResult = service.getByBrandAndProductAndDate(searchParam);

        assertEquals(expectedPrice.getBrand(), priceResult.getBrand());
        assertEquals(expectedPrice.getProduct(), priceResult.getProduct());
        assertEquals(expectedPrice.getStartDate(), priceResult.getStartDate());
        assertEquals(expectedPrice.getEndDate(), priceResult.getEndDate());
        assertEquals(expectedPrice.getCurrency(), priceResult.getCurrency());
        assertEquals(expectedPrice.getPriority(), priceResult.getPriority());
        assertEquals(expectedPrice.getPrice(), priceResult.getPrice());
        assertEquals(expectedPrice.getPriceListID(), priceResult.getPriceListID());
        verify(mockedPriceRepository, times(1)).findTopByBrandAndProductAndDate(searchParam);
    }

    @Test
    void casesOfSuccessByAppliedDate() throws Exception{
        return Arrays.asList(new Object[][]{
                {LocalDateTime.of(2020, 6, 14, 15, 0, 0), priceItem2},
                {LocalDateTime.of(2020, 6, 14, 16, 30, 0), priceItem2},
                {LocalDateTime.of(2020, 6, 14, 18, 30, 0), priceItem2},
                {LocalDateTime.of(2020, 9, 5, 10, 30, 0), priceItem1},
        });
    }

    @Test
    void testGetByProductAndBrand_whenPriceNotExists_shouldReturnNotFoundApiException() throws Exception{
        String errorMessage = "price not found";
        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.now())
                .productID(43555L)
                .brandID(1L).build();

        when(mockedPriceRepository.findTopByBrandAndProductAndDate(searchParam)).thenReturn(Optional.ofNullable(null));

        ApiException exception = assertThrows(NotFoundApiException.class, () -> service.getByBrandAndProductAndDate(searchParam));

        assertEquals(errorMessage, exception.getMessage());
        verify(mockedPriceRepository, times(1)).findTopByBrandAndProductAndDate(searchParam);
    }

    @Test
    void testGetByProductAndBrand_whenProviderFails_shouldReturnApiException() throws Exception{
        String errorMessage = "any database error";

        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.now())
                .productID(2L)
                .brandID(1L).build();

        Exception mockedException = mock(RuntimeException.class);

        when(mockedException.getMessage()).thenReturn(errorMessage);
        doThrow(mockedException).when(mockedPriceRepository).findTopByBrandAndProductAndDate(searchParam);

        ApiException exception = assertThrows(ApiException.class, () -> service.getByBrandAndProductAndDate(searchParam));

        assertEquals(errorMessage, exception.getMessage());
        verify(mockedPriceRepository, times(1)).findTopByBrandAndProductAndDate(searchParam);
    }
}
