package com.inditex.pricingapi.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.pricingapi.domain.contracts.PriceResponse;
import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.exceptions.ApiException;
import com.inditex.pricingapi.domain.exceptions.BadRequestApiException;
import com.inditex.pricingapi.domain.exceptions.NotFoundApiException;
import com.inditex.pricingapi.domain.models.constants.DateTime;
import com.inditex.pricingapi.domain.models.entities.Brand;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.models.entities.Product;
import com.inditex.pricingapi.domain.services.IPriceService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

class PriceControllerTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateTime.DATE_FORMAT_PATTERN);

    private static IPriceService priceService;

    private static PriceController controller;

    private static final String PARAM_PRODUCT_ID = "35455";

    private static final String PARAM_BRAND_ID = "1";

    private static final String PARAM_APPLY_AT = "2020-06-14T18:30:00";

    private PriceSearchParam searchParam = PriceSearchParam.Builder()
            .brandID(1L)
            .productID(35455L)
            .applyAt(LocalDateTime.of(2020, 06, 14, 18, 30, 0)).build();

    @BeforeAll
    static void setup() {
        priceService = mock(IPriceService.class);

        controller = new PriceController(priceService);
    }

    @AfterEach
    void clean() {
        reset(priceService);
    }

    @Test
    void testSearchPrice_whenIsSuccess_shouldReturnPriceResponse() throws ApiException {
        // given
        Brand brandZara = new Brand();
        brandZara.setId(1L);

        Product productCamisa = new Product();
        productCamisa.setId(35455L);

        Price expectedPrice = new Price();
        expectedPrice.setBrand(brandZara);
        expectedPrice.setProduct(productCamisa);
        expectedPrice.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        expectedPrice.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
        expectedPrice.setPriceListID(2L);
        expectedPrice.setPrice(BigDecimal.valueOf(25.45));

        // when
        when(priceService.getByBrandAndProductAndDate(any())).thenReturn(expectedPrice);

        // then
        PriceResponse response = controller.searchPrice(PARAM_PRODUCT_ID, PARAM_BRAND_ID, PARAM_APPLY_AT).getBody();

        assertEquals(expectedPrice.getBrand().getId(), response.getBrandID());
        assertEquals(expectedPrice.getProduct().getId(), response.getProductID());
        assertEquals(expectedPrice.getStartDate(), response.getStartDate());
        assertEquals(expectedPrice.getEndDate(), response.getEndDate());
        assertEquals(expectedPrice.getPrice(), response.getPrice());
        assertEquals(expectedPrice.getPriceListID(), response.getPriceList());

        ArgumentCaptor<PriceSearchParam> searchParamCaptor = ArgumentCaptor.forClass(PriceSearchParam.class);
        verify(priceService, times(1)).getByBrandAndProductAndDate(searchParamCaptor.capture());
        PriceSearchParam capturedSearchParam = searchParamCaptor.getValue();
        assertEquals(searchParam.getApplyAt(), capturedSearchParam.getApplyAt());
        assertEquals(searchParam.getBrandID(), capturedSearchParam.getBrandID());
        assertEquals(searchParam.getProductID(), capturedSearchParam.getProductID());
    }

    @ParameterizedTest
    @MethodSource("invalidParams")
    void testSearchPrice_whenParametersAreInvalid_shouldThrowBadRequestApiException(
            String productID,
            String brandID,
            String applyAt,
            String errorMessage
    ) throws ApiException {
        BadRequestApiException badRequestException = assertThrows(BadRequestApiException.class,
                () -> controller.searchPrice(productID, brandID, applyAt));

        assertEquals(HttpStatus.BAD_REQUEST.value(), badRequestException.getStatusCode());
        assertEquals(badRequestException.getMessage(), errorMessage);
        assertEquals("bad_request", badRequestException.getError());
        verify(priceService, never()).getByBrandAndProductAndDate(any());
    }

    private static Collection invalidParams() {
        return Arrays.asList(new Object[][]{
                {PARAM_PRODUCT_ID, PARAM_BRAND_ID, "202222-12-01TXX", "invalid parameter {apply_at}"},
                {PARAM_PRODUCT_ID, "999XXX", PARAM_APPLY_AT, "invalid parameter {brand_id}"},
                {"999XXX", PARAM_BRAND_ID, PARAM_APPLY_AT, "invalid parameter {product_id}"},
        });
    }

    @Test
    void testSearchPrice_whenAnInternalErrorOccurs_shouldThrowApiException() throws ApiException {
        String errorMessage = "internal error ocurred";
        ApiException apiException = new ApiException(errorMessage);

        doThrow(apiException).when(priceService).getByBrandAndProductAndDate(any());

        ApiException exception = assertThrows(ApiException.class,
                () -> controller.searchPrice(PARAM_PRODUCT_ID, PARAM_BRAND_ID, PARAM_APPLY_AT));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getStatusCode());
        assertEquals(errorMessage, exception.getMessage());
        assertEquals("internal_server_error", exception.getError());
    }

    @Test
    void testSearchPrice_whenResourceNotExist_shouldThrowNoFoundApiException() throws ApiException {
        String errorMessage = "price not found";
        NotFoundApiException notFoundApiException = new NotFoundApiException(errorMessage);

        doThrow(notFoundApiException).when(priceService).getByBrandAndProductAndDate(any());

        NotFoundApiException exception = assertThrows(NotFoundApiException.class,
                () -> controller.searchPrice(PARAM_PRODUCT_ID, PARAM_BRAND_ID, PARAM_APPLY_AT));

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode());
        assertEquals(errorMessage, exception.getMessage());
        assertEquals("not_found", exception.getError());
    }
}
