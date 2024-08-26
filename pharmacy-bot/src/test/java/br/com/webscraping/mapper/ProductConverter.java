package br.com.webscraping.mapper;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;

public class ProductConverter {

    public static ProductDTO toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImgUrl(product.getImgUrl());
        productDTO.setUrl(product.getUrl());
        productDTO.setBrand(product.getBrand());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setRating(product.getRating());
        productDTO.setReviewsCount(product.getReviewsCount());
        productDTO.setPriceFrom(product.getPriceFrom());
        productDTO.setPriceFinal(product.getPriceFinal());
        productDTO.setDiscount(product.getDiscount());

        return productDTO;
    }

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
        product.setUrl(productDTO.getUrl());
        product.setBrand(productDTO.getBrand());
        product.setQuantity(productDTO.getQuantity());
        product.setRating(productDTO.getRating());
        product.setReviewsCount(productDTO.getReviewsCount());
        product.setPriceFrom(productDTO.getPriceFrom());
        product.setPriceFinal(productDTO.getPriceFinal());
        product.setDiscount(productDTO.getDiscount());

        return product;
    }
}