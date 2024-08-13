// src/main/java/br/com/webscraping/dto/ProductDTO.java
package br.com.webscraping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imgUrl;
    private String url;
    private String category;
}