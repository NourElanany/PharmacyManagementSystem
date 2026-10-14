package com.pharmacy.service;

import com.pharmacy.dto.MedicineRequest;
import com.pharmacy.dto.MedicineResponse;
import com.pharmacy.entity.Category;
import com.pharmacy.entity.Medicine;
import com.pharmacy.exception.ResourceNotFoundException;
import com.pharmacy.repository.CategoryRepository;
import com.pharmacy.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;

    public MedicineService(MedicineRepository medicineRepository, CategoryRepository categoryRepository) {
        this.medicineRepository = medicineRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<MedicineResponse> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
        return toResponse(medicine);
    }

    public List<MedicineResponse> searchMedicines(String query) {
        return medicineRepository.findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(query, query)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<MedicineResponse> getLowStockMedicines() {
        return medicineRepository.findLowStockMedicines().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<MedicineResponse> getExpiredMedicines() {
        return medicineRepository.findByExpiryDateBefore(LocalDate.now()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<MedicineResponse> getExpiringSoon(int days) {
        LocalDate now = LocalDate.now();
        LocalDate futureDate = now.plusDays(days);
        return medicineRepository.findByExpiryDateBetween(now, futureDate).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicineResponse createMedicine(MedicineRequest request) {
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        }

        Medicine medicine = new Medicine(
                request.getName(),
                request.getGenericName(),
                request.getBatchNumber(),
                request.getBarcode(),
                category,
                request.getPurchasePrice(),
                request.getSellingPrice(),
                request.getStockQuantity(),
                request.getMinStockAlert(),
                request.getExpiryDate()
        );

        return toResponse(medicineRepository.save(medicine));
    }

    @Transactional
    public MedicineResponse updateMedicine(Long id, MedicineRequest request) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            medicine.setCategory(category);
        } else {
            medicine.setCategory(null);
        }

        medicine.setName(request.getName());
        medicine.setGenericName(request.getGenericName());
        medicine.setBatchNumber(request.getBatchNumber());
        medicine.setBarcode(request.getBarcode());
        medicine.setPurchasePrice(request.getPurchasePrice());
        medicine.setSellingPrice(request.getSellingPrice());
        medicine.setStockQuantity(request.getStockQuantity());
        medicine.setMinStockAlert(request.getMinStockAlert());
        medicine.setExpiryDate(request.getExpiryDate());

        return toResponse(medicineRepository.save(medicine));
    }

    @Transactional
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }

    private MedicineResponse toResponse(Medicine medicine) {
        MedicineResponse res = new MedicineResponse();
        res.setId(medicine.getId());
        res.setName(medicine.getName());
        res.setGenericName(medicine.getGenericName());
        res.setBatchNumber(medicine.getBatchNumber());
        res.setBarcode(medicine.getBarcode());
        if (medicine.getCategory() != null) {
            res.setCategoryId(medicine.getCategory().getId());
            res.setCategoryName(medicine.getCategory().getName());
        }
        res.setPurchasePrice(medicine.getPurchasePrice());
        res.setSellingPrice(medicine.getSellingPrice());
        res.setStockQuantity(medicine.getStockQuantity());
        res.setMinStockAlert(medicine.getMinStockAlert());
        res.setExpiryDate(medicine.getExpiryDate());
        res.setExpired(medicine.isExpired());
        res.setLowStock(medicine.isLowStock());
        return res;
    }
}
