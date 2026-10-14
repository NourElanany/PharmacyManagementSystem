package com.pharmacy.service;

import com.pharmacy.dto.*;
import com.pharmacy.entity.Medicine;
import com.pharmacy.entity.Sale;
import com.pharmacy.entity.SaleItem;
import com.pharmacy.entity.User;
import com.pharmacy.exception.BadRequestException;
import com.pharmacy.exception.InsufficientStockException;
import com.pharmacy.exception.ResourceNotFoundException;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.SaleRepository;
import com.pharmacy.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, MedicineRepository medicineRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }

    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return toResponse(sale);
    }

    public SaleResponse getSaleByInvoiceNumber(String invoiceNumber) {
        Sale sale = saleRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + invoiceNumber));
        return toResponse(sale);
    }

    @Transactional
    public SaleResponse processSale(SaleRequest request) {
        User cashier = userRepository.findById(request.getCashierId())
                .orElseThrow(() -> new ResourceNotFoundException("Cashier not found with id: " + request.getCashierId()));

        Sale sale = new Sale();
        sale.setInvoiceNumber(generateInvoiceNumber());
        sale.setCashier(cashier);
        sale.setCustomerName(request.getCustomerName() != null ? request.getCustomerName() : "Walk-in Customer");
        sale.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH");

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleItemRequest itemReq : request.getItems()) {
            Medicine medicine = medicineRepository.findById(itemReq.getMedicineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + itemReq.getMedicineId()));

            // Pharmacy safety check: prevent selling expired medicine
            if (medicine.isExpired()) {
                throw new BadRequestException("Cannot sell expired medicine: " + medicine.getName() + " (Expired on: " + medicine.getExpiryDate() + ")");
            }

            // Stock check
            if (medicine.getStockQuantity() < itemReq.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for medicine '" + medicine.getName()
                        + "'. Available: " + medicine.getStockQuantity() + ", Requested: " + itemReq.getQuantity());
            }

            // Deduct inventory
            medicine.setStockQuantity(medicine.getStockQuantity() - itemReq.getQuantity());
            medicineRepository.save(medicine);

            // Create line item
            SaleItem saleItem = new SaleItem(medicine, itemReq.getQuantity(), medicine.getSellingPrice());
            sale.addItem(saleItem);

            totalAmount = totalAmount.add(saleItem.getTotalPrice());
        }

        sale.setTotalAmount(totalAmount);
        BigDecimal discount = request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO;
        sale.setDiscount(discount);
        BigDecimal netAmount = totalAmount.subtract(discount);
        sale.setNetAmount(netAmount.compareTo(BigDecimal.ZERO) > 0 ? netAmount : BigDecimal.ZERO);

        Sale savedSale = saleRepository.save(sale);
        return toResponse(savedSale);
    }

    private String generateInvoiceNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "INV-" + datePart + "-" + randomPart;
    }

    private SaleResponse toResponse(Sale sale) {
        SaleResponse res = new SaleResponse();
        res.setId(sale.getId());
        res.setInvoiceNumber(sale.getInvoiceNumber());
        if (sale.getCashier() != null) {
            res.setCashierName(sale.getCashier().getFullName());
        }
        res.setCustomerName(sale.getCustomerName());
        res.setTotalAmount(sale.getTotalAmount());
        res.setDiscount(sale.getDiscount());
        res.setNetAmount(sale.getNetAmount());
        res.setPaymentMethod(sale.getPaymentMethod());
        res.setCreatedAt(sale.getCreatedAt());

        List<SaleItemResponse> itemResponses = sale.getItems().stream().map(item -> {
            SaleItemResponse itemRes = new SaleItemResponse();
            itemRes.setId(item.getId());
            itemRes.setMedicineId(item.getMedicine().getId());
            itemRes.setMedicineName(item.getMedicine().getName());
            itemRes.setQuantity(item.getQuantity());
            itemRes.setUnitPrice(item.getUnitPrice());
            itemRes.setTotalPrice(item.getTotalPrice());
            return itemRes;
        }).collect(Collectors.toList());

        res.setItems(itemResponses);
        return res;
    }
}
