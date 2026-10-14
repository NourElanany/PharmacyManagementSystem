package com.pharmacy.service;

import com.pharmacy.dto.SaleItemRequest;
import com.pharmacy.dto.SaleRequest;
import com.pharmacy.dto.SaleResponse;
import com.pharmacy.entity.Category;
import com.pharmacy.entity.Medicine;
import com.pharmacy.entity.Sale;
import com.pharmacy.entity.User;
import com.pharmacy.entity.UserRole;
import com.pharmacy.exception.BadRequestException;
import com.pharmacy.exception.InsufficientStockException;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.SaleRepository;
import com.pharmacy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaleService saleService;

    private User cashier;
    private Medicine medicine;

    @BeforeEach
    void setUp() {
        cashier = new User("Sara Cashier", "sara", "sara@pharmacy.com", "pass123", "01234567890", UserRole.CASHIER);
        cashier.setId(1L);

        medicine = new Medicine(
                "Panadol Extra", "Paracetamol", "BAT-001", "6221234567",
                new Category("Analgesics", "Pain relief"),
                new BigDecimal("20.00"), new BigDecimal("30.00"),
                50, 10, LocalDate.now().plusMonths(6)
        );
        medicine.setId(10L);
    }

    @Test
    @DisplayName("Should successfully process sale and deduct inventory stock")
    void testProcessSale_Success() {
        SaleRequest request = new SaleRequest();
        request.setCashierId(1L);
        request.setCustomerName("Mohamed Ali");
        request.setItems(List.of(new SaleItemRequest(10L, 5)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(cashier));
        when(medicineRepository.findById(10L)).thenReturn(Optional.of(medicine));
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SaleResponse response = saleService.processSale(request);

        assertNotNull(response);
        assertEquals("Mohamed Ali", response.getCustomerName());
        assertEquals(new BigDecimal("150.00"), response.getTotalAmount()); // 5 * 30.00
        assertEquals(45, medicine.getStockQuantity()); // 50 - 5 = 45
        verify(medicineRepository, times(1)).save(medicine);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    @DisplayName("Should throw InsufficientStockException when requested quantity exceeds available stock")
    void testProcessSale_InsufficientStock() {
        SaleRequest request = new SaleRequest();
        request.setCashierId(1L);
        request.setItems(List.of(new SaleItemRequest(10L, 100))); // Requested 100, available 50

        when(userRepository.findById(1L)).thenReturn(Optional.of(cashier));
        when(medicineRepository.findById(10L)).thenReturn(Optional.of(medicine));

        assertThrows(InsufficientStockException.class, () -> saleService.processSale(request));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException when attempting to sell expired medicine")
    void testProcessSale_ExpiredMedicine() {
        medicine.setExpiryDate(LocalDate.now().minusDays(1)); // Expired yesterday

        SaleRequest request = new SaleRequest();
        request.setCashierId(1L);
        request.setItems(List.of(new SaleItemRequest(10L, 2)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(cashier));
        when(medicineRepository.findById(10L)).thenReturn(Optional.of(medicine));

        assertThrows(BadRequestException.class, () -> saleService.processSale(request));
        verify(saleRepository, never()).save(any(Sale.class));
    }
}
