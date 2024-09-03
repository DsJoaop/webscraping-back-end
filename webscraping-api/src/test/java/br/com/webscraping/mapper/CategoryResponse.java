package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryResponseDTO;
import br.com.webscraping.entities.Category;

public class CategoryResponse {

    // Converte de Category para CategoryResponseDTO
    public static CategoryResponseDTO toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());

        return dto;
    }

    // Converte de CategoryResponseDTO para Category (Entidade)
    public static Category toEntity(CategoryResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setUrl(dto.getUrl());

        return category;
    }
}
