package com.pharmacy.config;

import com.pharmacy.entity.Category;
import com.pharmacy.entity.Medicine;
import com.pharmacy.entity.User;
import com.pharmacy.entity.UserRole;
import com.pharmacy.repository.CategoryRepository;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository,
                                          CategoryRepository categoryRepository,
                                          MedicineRepository medicineRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                return; // Already initialized
            }

            // 1. Seed Users
            User admin = new User("Dr. Nour Admin", "nour_admin", "admin@pharmacy.com", "admin123", "01012345678", UserRole.ADMIN);
            User pharmacist = new User("Ahmed Pharmacist", "ahmed_ph", "ahmed@pharmacy.com", "pass123", "01123456789", UserRole.PHARMACIST);
            User cashier = new User("Sara Cashier", "sara_cashier", "sara@pharmacy.com", "pass123", "01234567890", UserRole.CASHIER);
            userRepository.save(admin);
            userRepository.save(pharmacist);
            userRepository.save(cashier);

            // 2. Seed Categories
            Category analgesics = categoryRepository.save(new Category("Analgesics & Antipyretics", "Pain relief and fever reducers"));
            Category antibiotics = categoryRepository.save(new Category("Antibiotics", "Bacterial infection treatments"));
            Category vitamins = categoryRepository.save(new Category("Vitamins & Supplements", "Immune boosters and nutritional supplements"));
            Category chronic = categoryRepository.save(new Category("Chronic Diseases", "Hypertension, Diabetes, and Heart conditions"));

            // 3. Seed Medicines
            medicineRepository.save(new Medicine(
                    "Panadol Extra", "Paracetamol + Caffeine", "BAT-2024-001", "622300123456",
                    analgesics, new BigDecimal("35.00"), new BigDecimal("45.00"),
                    120, 15, LocalDate.now().plusMonths(18)
            ));

            medicineRepository.save(new Medicine(
                    "Augmentin 1g", "Amoxicillin + Clavulanic Acid", "BAT-2024-002", "622300123457",
                    antibiotics, new BigDecimal("85.00"), new BigDecimal("105.00"),
                    45, 10, LocalDate.now().plusMonths(12)
            ));

            medicineRepository.save(new Medicine(
                    "Vitamin C 1000mg", "Ascorbic Acid", "BAT-2024-003", "622300123458",
                    vitamins, new BigDecimal("25.00"), new BigDecimal("35.00"),
                    8, 10, LocalDate.now().plusMonths(6) // Low stock example (8 <= 10)
            ));

            medicineRepository.save(new Medicine(
                    "Concor 5mg", "Bisoprolol Fumarate", "BAT-2024-004", "622300123459",
                    chronic, new BigDecimal("40.00"), new BigDecimal("52.50"),
                    60, 10, LocalDate.now().plusDays(15) // Expiring soon example (15 days)
            ));
        };
    }
}
