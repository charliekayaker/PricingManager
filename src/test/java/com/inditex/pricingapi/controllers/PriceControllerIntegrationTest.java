package com.inditex.pricingapi.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    public PriceControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @MethodSource("casesForRealTesting")
    public void testPricesSearch_whenIsSuccess_ShouldReturnStatusOk(
            String productID,
            String brandID,
            String applyAt,
            String responseJSON
    ) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/search")
                        .param("product_id", productID)
                        .param("brand_id", brandID)
                        .param("apply_at", applyAt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJSON));
    }

    private static Collection casesForRealTesting() {
        String responseJSONCase1 = """
                {
                    "price": 35.5,
                    "product_id": 35455,
                    "brand_id": 1,
                    "price_list": 1,
                    "start_date": "2020-06-14T00:00:00",
                    "end_date": "2020-12-31T23:59:59"
                }""";
        String responseJSONCase2 = """
                {
                	"price": 25.45,
                	"product_id": 35455,
                	"brand_id": 1,
                	"price_list": 2,
                	"start_date": "2020-06-14T15:00:00",
                	"end_date": "2020-06-14T18:30:00"
                }""";
        String responseJSONCase3 = """
                {
                	"price": 35.50,
                	"product_id": 35455,
                	"brand_id": 1,
                	"price_list": 1,
                	"start_date": "2020-06-14T00:00:00",
                	"end_date": "2020-12-31T23:59:59"
                }""";
        String responseJSONCase4 = """
                {
                	"price": 30.50,
                	"product_id": 35455,
                	"brand_id": 1,
                	"price_list": 3,
                	"start_date": "2020-06-15T00:00:00",
                	"end_date": "2020-06-15T11:00:00"
                }""";
        String responseJSONCase5 = """
                {
                	"price": 38.95,
                	"product_id": 35455,
                	"brand_id": 1,
                	"price_list": 4,
                	"start_date": "2020-06-15T16:00:00",
                	"end_date": "2020-12-31T23:59:59"
                }""";

        return Arrays.asList(new Object[][]{
                {"35455", "1", "2020-06-14T10:00:00", responseJSONCase1},
                {"35455", "1", "2020-06-14T16:00:00", responseJSONCase2},
                {"35455", "1", "2020-06-14T21:00:00", responseJSONCase3},
                {"35455", "1", "2020-06-15T10:00:00", responseJSONCase4},
                {"35455", "1", "2020-06-16T21:00:00", responseJSONCase5},
        });
    }

    @Test
    public void testPricesSearch_whenAParameterIsInvalid_ShouldReturnBadRequestApiException() throws Exception {
        String apiErrorResponseJSON = """
                {
                     "code": 400,
                     "message": "invalid parameter {product_id}",
                     "error": "bad_request"
                }
                    """;

        mockMvc.perform(MockMvcRequestBuilders.get("/prices/search")
                        .param("product_id", "XXXXX")
                        .param("brand_id", "1")
                        .param("apply_at", "2020-06-14T10:00:00"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(apiErrorResponseJSON));
    }
}
