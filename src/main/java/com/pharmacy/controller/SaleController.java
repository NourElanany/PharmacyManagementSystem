package com.pharmacy.controller;

import com.pharmacy.dto.SaleRequest;
import com.pharmacy.dto.SaleResponse;
import com.pharmacy.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales & Invoicing", description = "Endpoints for point-of-sale transactions, invoice generation, and sales history")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    @Operation(summary = "Get all sales transactions")
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale details by ID")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping("/invoice/{invoiceNumber}")
    @Operation(summary = "Find invoice by invoice number")
    public ResponseEntity<SaleResponse> getSaleByInvoiceNumber(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(saleService.getSaleByInvoiceNumber(invoiceNumber));
    }

    @PostMapping
    @Operation(summary = "Process a new sale / generate invoice", description = "Validates stock, prevents expired drug sales, automatically deducts inventory, and returns a detailed invoice")
    public ResponseEntity<SaleResponse> processSale(@Valid @RequestBody SaleRequest request) {
        return new ResponseEntity<>(saleService.processSale(request), HttpStatus.CREATED);
    }
}
