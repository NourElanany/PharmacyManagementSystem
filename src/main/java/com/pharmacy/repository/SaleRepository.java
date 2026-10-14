package com.pharmacy.repository;

import com.pharmacy.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByInvoiceNumber(String invoiceNumber);
    List<Sale> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Sale> findByCashierId(Long cashierId);
}
