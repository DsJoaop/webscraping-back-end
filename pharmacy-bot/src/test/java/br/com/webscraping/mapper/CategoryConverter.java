package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static CategoryDTO toDto(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setUrl(category.getUrl());
        categoryDTO.setProducts(toProductDtoList(category.getProducts()));
        categoryDTO.setSubcategories(toCategoryDtoList(category.getSubcategories()));

        return categoryDTO;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setUrl(categoryDTO.getUrl());
        category.setProducts(toProductEntityList(categoryDTO.getProducts()));
        category.setSubcategories(toCategoryEntityList(categoryDTO.getSubcategories()));

        return category;
    }

    private static List<ProductDTO> toProductDtoList(List<Product> products) {
        return products.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }

    private static List<Product> toProductEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(ProductConverter::toEntity)
                .collect(Collectors.toList());
    }

    private static List<CategoryDTO> toCategoryDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryConverter::toDto)
                .collect(Collectors.toList());
    }

    private static List<Category> toCategoryEntityList(List<CategoryDTO> categoryDTOs) {
        return categoryDTOs.stream()
                .map(CategoryConverter::toEntity)
                .collect(Collectors.toList());
    }
}
