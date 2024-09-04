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

    private String imgUrl;

    @NotBlank(message = "URL is required")
    private String url;

    private String brand;

    private String quantity;

    @Positive(message = "Rating must be a positive value")
    private double rating;

    @Positive(message = "Reviews count must be a positive value")
    private int reviewsCount;

    @Positive(message = "Price from must be a positive value")
    private double priceFrom;

    @Positive(message = "Price final must be a positive value")
    private double priceFinal;

    private double discount;

    public ProductDTO(String name, String url, String brand, String quantity, double rating, int reviewsCount, double priceFrom, double priceFinal, double discount) {
        this.name = name;
        this.url = url;
        this.brand = brand;
        this.quantity = quantity;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.priceFrom = priceFrom;
        this.priceFinal = priceFinal;
        this.discount = discount;
    }

}