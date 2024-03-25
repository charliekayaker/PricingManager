package com.inditex.pricingapi.domain.services;

import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.exceptions.NotFoundApiException;
import com.inditex.pricingapi.domain.models.entities.Brand;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.models.entities.Product;
import com.inditex.pricingapi.domain.repositories.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PricingServicesImplTest {

    private static PriceRepository mockedPriceRepository;
    private static PriceServiceImpl service;
    private static Price priceItem1;
    private static Price priceItem2;

    @BeforeAll
    static void setup() {
        mockedPriceRepository = mock(PriceRepository.class);

        Brand brandZara = new Brand();
        brandZara.setId(1L);
        brandZara.setName("Zara");

        Product productCamisa = new Product();
        productCamisa.setId(35455L);
        productCamisa.setName("CAMISA LINO MANGA LARGA");
        productCamisa.setCode("ABC-12345");
        productCamisa.setDescription("Esta camisa está confeccionada en 100% lino europeo.");

        priceItem1 = new Price();
        priceItem1.setBrand(brandZara);
        priceItem1.setProduct(productCamisa);
        priceItem1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        priceItem1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        priceItem1.setPriceListID(1L);
        priceItem1.setPrice(BigDecimal.valueOf(35.5));
        priceItem1.setPriority(0);

        priceItem2 = new Price();
        priceItem2.setBrand(brandZara);
        priceItem2.setProduct(productCamisa);
        priceItem2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        priceItem2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
        priceItem2.setPriceListID(2L);
        priceItem2.setPrice(BigDecimal.valueOf(25.45));
        priceItem2.setPriority(1);

        service = new PriceServiceImpl(mockedPriceRepository);
    }

    @AfterEach
    void clean() {
        reset(mockedPriceRepository);
    }

    @ParameterizedTest(name = "applyAt: {0} - expectedPrice: {1}")
    @MethodSource("casesOfSuccessByAppliedDate")
    void testGetByProductAndBrand_whenIsSuccess_shouldReturnPriceItem(LocalDateTime applyAt, Price expectedPrice) throws Exception {
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

    private static Collection casesOfSuccessByAppliedDate() {
        return Arrays.asList(new Object[][]{
                {LocalDateTime.of(2020, 6, 14, 15, 0, 0), priceItem2},
                {LocalDateTime.of(2020, 6, 14, 16, 30, 0), priceItem2},
                {LocalDateTime.of(2020, 6, 14, 18, 30, 0), priceItem2},
                {LocalDateTime.of(2020, 9, 5, 10, 30, 0), priceItem1},
        });
    }

    @Test
    void testGetByProductAndBrand_whenPriceNotExists_shouldReturnNotFoundApiException() throws Exception {
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
    void testGetByProductAndBrand_whenProviderFails_shouldReturnApiException() throws Exception {
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
