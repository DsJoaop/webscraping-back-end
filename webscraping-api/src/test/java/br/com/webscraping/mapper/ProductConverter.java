package br.com.webscraping.mapper;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {

    // Converte de Product para ProductDTO
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImgUrl(product.getImgUrl());
        dto.setUrl(product.getUrl());
        dto.setBrand(product.getBrand());
        dto.setQuantity(product.getQuantity());
        dto.setRating(product.getRating());
        dto.setReviewsCount(product.getReviewsCount());
        dto.setPriceFrom(product.getPriceFrom());
        dto.setPriceFinal(product.getPriceFinal());
        dto.setDiscount(product.getDiscount());

        return dto;
    }

    // Converte de ProductDTO para Product
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImgUrl(dto.getImgUrl());
        product.setUrl(dto.getUrl());
        product.setBrand(dto.getBrand());
        product.setQuantity(dto.getQuantity());
        product.setRating(dto.getRating());
        product.setReviewsCount(dto.getReviewsCount());
        product.setPriceFrom(dto.getPriceFrom());
        product.setPriceFinal(dto.getPriceFinal());
        product.setDiscount(dto.getDiscount());

        return product;
    }

    // Converte uma lista de Products para uma lista de ProductDTOs
    public static List<ProductDTO> toDTOList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(ProductConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Converte uma lista de ProductDTOs para uma lista de Products
    public static List<Product> toEntityList(List<ProductDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(ProductConverter::toEntity)
                .collect(Collectors.toList());
    }
}
