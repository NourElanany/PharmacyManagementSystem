package com.pharmacy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pharmacyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pharmacy Management System API")
                        .description("RESTful Backend for Pharmacy Management, Inventory Control, Expiry Alerts, and Point-of-Sale (POS) Invoicing.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nour")
                                .email("nour@example.com"))
                        .license(new License().name("Apache 2.0")));
    }
}
