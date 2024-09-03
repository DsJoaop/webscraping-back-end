package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Product;

public class CategoryConverter {

    // Converte de CategoryDTO para Category (Entidade)
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setUrl(dto.getUrl());

        // Subcategorias
        if (dto.getSubcategories() != null) {
            dto.getSubcategories().forEach(subcategoryDTO -> {
                Category subcategory = toEntity(subcategoryDTO);
                category.getSubcategories().add(subcategory);
            });
        }

        // Produtos
        if (dto.getProducts() != null) {
            dto.getProducts().forEach(productDTO -> {
                Product product = ProductConverter.toEntity(productDTO);
                category.getProducts().add(product);
            });
        }

        return category;
    }

    // Converte de Category (Entidade) para CategoryDTO
    public static CategoryDTO toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());

        // Subcategorias
        if (entity.getSubcategories() != null) {
            entity.getSubcategories().forEach(subcategory -> {
                CategoryDTO subcategoryDTO = toDto(subcategory);
                dto.getSubcategories().add(subcategoryDTO);
            });
        }

        // Produtos
        if (entity.getProducts() != null) {
            entity.getProducts().forEach(product -> {
                ProductDTO productDTO = ProductConverter.toDTO(product);
                dto.getProducts().add(productDTO);
            });
        }

        // Farmácias
        if (entity.getPharmacies() != null) {
            entity.getPharmacies().forEach(pharmacy -> {
                PharmacyResponseDTO pharmacyDTO = PharmacyResponse.toDto(pharmacy);
                dto.getPharmacies().add(pharmacyDTO);
            });
        }

        return dto;
    }
}

