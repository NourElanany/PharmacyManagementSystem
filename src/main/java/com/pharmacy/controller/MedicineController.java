package com.pharmacy.controller;

import com.pharmacy.dto.MedicineRequest;
import com.pharmacy.dto.MedicineResponse;
import com.pharmacy.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@Tag(name = "Medicines", description = "Endpoints for managing pharmacy medicines, inventory, and expiry tracking")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    @Operation(summary = "Get all medicines", description = "Retrieves a complete list of medicines in the pharmacy")
    public ResponseEntity<List<MedicineResponse>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medicine by ID")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search medicines by trade or generic name")
    public ResponseEntity<List<MedicineResponse>> searchMedicines(@RequestParam String query) {
        return ResponseEntity.ok(medicineService.searchMedicines(query));
    }

    @GetMapping("/alerts/low-stock")
    @Operation(summary = "Get medicines with low stock alert", description = "Returns medicines whose stock is at or below the minimum alert threshold")
    public ResponseEntity<List<MedicineResponse>> getLowStockMedicines() {
        return ResponseEntity.ok(medicineService.getLowStockMedicines());
    }

    @GetMapping("/alerts/expired")
    @Operation(summary = "Get expired medicines")
    public ResponseEntity<List<MedicineResponse>> getExpiredMedicines() {
        return ResponseEntity.ok(medicineService.getExpiredMedicines());
    }

    @GetMapping("/alerts/expiring-soon")
    @Operation(summary = "Get medicines expiring within N days")
    public ResponseEntity<List<MedicineResponse>> getExpiringSoon(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(medicineService.getExpiringSoon(days));
    }

    @PostMapping
    @Operation(summary = "Create a new medicine")
    public ResponseEntity<MedicineResponse> createMedicine(@Valid @RequestBody MedicineRequest request) {
        return new ResponseEntity<>(medicineService.createMedicine(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing medicine")
    public ResponseEntity<MedicineResponse> updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a medicine by ID")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
