// src/main/java/br/com/webscraping/dto/ProductDTO.java
package br.com.webscraping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @Positive(message = "Price must be a positive value")
    private double price;
    private String imgUrl;
    private String url;
}