package com.inditex.pricingapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.pricingapi.domain.contracts.PriceSearchParam;
import com.inditex.pricingapi.domain.models.entities.Price;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class PriceRepositoryImplTest {

    private static EntityManager entityManagerMock;

    private PriceRepositoryImpl repository;

    private String searchQuery = "FROM Price p WHERE p.brand.id = :brandId AND p.product.id = :productId AND " +
            ":applyAt BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1";

    @BeforeEach
    void setup() {
        entityManagerMock = mock(EntityManager.class);
        repository = new PriceRepositoryImpl();
        repository.setEntityManager(entityManagerMock);
    }

    @AfterEach
    void clean() {
        reset(entityManagerMock);
    }

    @Test
    void testFindTopByBrandAndProductAndDate_whenIsSuccess_shouldReturnOptionalPrice() {
        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.of(2020, 6, 14, 16, 30, 0))
                .productID(35455L)
                .brandID(1L).build();

        Price expectedPrice = new Price();
        expectedPrice.setId(1L);
        expectedPrice.setPrice(BigDecimal.valueOf(25.5));

        Query queryMock = mock(Query.class);
        Stream streamMock = mock(Stream.class);

        when(entityManagerMock.createQuery(searchQuery)).thenReturn(queryMock);
        when(queryMock.setParameter("brandId", searchParam.getBrandID())).thenReturn(queryMock);
        when(queryMock.setParameter("productId", searchParam.getProductID())).thenReturn(queryMock);
        when(queryMock.setParameter("applyAt", searchParam.getApplyAt())).thenReturn(queryMock);
        when(queryMock.getResultStream()).thenReturn(streamMock);
        when(streamMock.findFirst()).thenReturn(Optional.of(expectedPrice));

        Optional<Price> priceResult = repository.findTopByBrandAndProductAndDate(searchParam);

        assertNotNull(priceResult);
        assertEquals(expectedPrice.getId(), priceResult.get().getId());
        assertEquals(expectedPrice.getPrice(), priceResult.get().getPrice());

        ArgumentCaptor<String> hqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(entityManagerMock, times(1)).createQuery(hqlCaptor.capture());
        String queryHQL = hqlCaptor.getValue();
        assertEquals(searchQuery, queryHQL);
    }

    @Test
    void testFindTopByBrandAndProductAndDate_whenDataBaseThrowsAnException_shouldThrowsException() {
        PriceSearchParam searchParam = new PriceSearchParam.Builder()
                .applyAt(LocalDateTime.of(2020, 6, 14, 16, 30, 0))
                .productID(35455L)
                .brandID(1L).build();

        HibernateException dataSourceException = mock(HibernateException.class);

        doThrow(dataSourceException).when(entityManagerMock).createQuery(searchQuery);

        Exception exceptionThrown = assertThrows(HibernateException.class, () -> repository.findTopByBrandAndProductAndDate(searchParam));

        assertEquals(dataSourceException, exceptionThrown);
        verify(entityManagerMock, times(1)).createQuery(searchQuery);
    }
}
