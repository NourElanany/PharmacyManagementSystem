package com.pharmacy.repository;

import com.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(String name, String genericName);

    Optional<Medicine> findByBarcode(String barcode);

    List<Medicine> findByCategoryId(Long categoryId);

    // Find all medicines that have expired before a given date
    List<Medicine> findByExpiryDateBefore(LocalDate date);

    // Find all medicines expiring in a specific range (e.g. in the next 30 days)
    List<Medicine> findByExpiryDateBetween(LocalDate start, LocalDate end);

    // Custom JPQL query for low stock medicines
    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity <= m.minStockAlert")
    List<Medicine> findLowStockMedicines();
}
