package com.inditex.pricingapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.models.entities.Brand;
import com.inditex.pricingapi.domain.models.entities.Price;
import com.inditex.pricingapi.domain.models.entities.Product;
import com.inditex.pricingapi.domain.models.enums.Currency;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/data-test.sql")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PriceRepositoryImplIntegrationTest {

    @Autowired
    private PriceRepositoryImpl repository;

    @Test
    void testFindTopByBrandAndProductAndDate_whenIsSuccess_ShouldReturnPriceItem() throws Exception {
        Brand brandZara = new Brand();
        brandZara.setId(1L);
        brandZara.setName("Zara");

        Product productCamisa = new Product();
        productCamisa.setId(35455L);
        productCamisa.setName("PANTALON CHUPIN MORADO");
        productCamisa.setCode("ABC111");
        productCamisa.setDescription("Este pantalon es lo mas top.");

        Price expectedPrice = new Price();
        expectedPrice.setBrand(brandZara);
        expectedPrice.setProduct(productCamisa);
        expectedPrice.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        expectedPrice.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
        expectedPrice.setPriceListID(2L);
        expectedPrice.setPrice(BigDecimal.valueOf(25.45));
        expectedPrice.setPriority(1);
        expectedPrice.setCurrency(Currency.EUR);

        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.of(2020, 6, 14, 16, 30, 0))
                .productID(35455L)
                .brandID(1L).build();

        Optional<Price> result = repository.findTopByBrandAndProductAndDate(searchParam);

        assertNotNull(result);
        assertEquals(expectedPrice.getBrand().getId(), result.get().getBrand().getId());
        assertEquals(expectedPrice.getBrand().getName(), result.get().getBrand().getName());
        assertEquals(expectedPrice.getProduct().getId(), result.get().getProduct().getId());
        assertEquals(expectedPrice.getProduct().getName(), result.get().getProduct().getName());
        assertEquals(expectedPrice.getProduct().getCode(), result.get().getProduct().getCode());
        assertEquals(expectedPrice.getProduct().getDescription(), result.get().getProduct().getDescription());
        assertEquals(expectedPrice.getStartDate(), result.get().getStartDate());
        assertEquals(expectedPrice.getEndDate(), result.get().getEndDate());
        assertEquals(expectedPrice.getCurrency(), result.get().getCurrency());
        assertEquals(expectedPrice.getPriority(), result.get().getPriority());
        assertEquals(expectedPrice.getPrice(), result.get().getPrice());
        assertEquals(expectedPrice.getPriceListID(), result.get().getPriceListID());
    }

    @Test
    void testFindTopByBrandAndProductAndDate_whenPriceNotExist_ShouldReturnEmptyValue() throws Exception {
        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.of(2020, 6, 14, 16, 30, 0))
                .productID(99L)
                .brandID(55L).build();

        Optional<Price> result = repository.findTopByBrandAndProductAndDate(searchParam);

        assertTrue(result.isEmpty());
    }
}
