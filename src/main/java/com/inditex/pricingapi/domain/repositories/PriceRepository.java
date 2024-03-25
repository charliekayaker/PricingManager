package com.inditex.pricingapi.domain.repositories;

import com.inditex.pricingapi.domain.models.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE p.brand.id = :brandId AND p.product.id = :productId " +
            "AND :applyAt BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC")
    Optional<Price> findTopByBrandAndProductAndApplicationDate(Long brandId, Long productId, LocalDateTime applyAt);
}
