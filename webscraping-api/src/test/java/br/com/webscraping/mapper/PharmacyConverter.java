package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;

import java.util.ArrayList;
import java.util.List;

public class PharmacyConverter {


    public static PharmacyDTO toDto(Pharmacy entity) {
        if (entity == null) {
            return null;
        }

        PharmacyDTO dto = new PharmacyDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZipCode(entity.getZipCode());
        dto.setUrl(entity.getUrl());
        dto.setImgUrl(entity.getImgUrl());

        // Converte a lista de categorias associadas
        if (entity.getCategories() != null) {
            List<CategoryDTO> categoriesDto = new ArrayList<>();
            for (Category category : entity.getCategories()) {
                categoriesDto.add(CategoryConverter.toDto(category));
            }
            dto.setCategories(categoriesDto);
        }

        return dto;
    }


    public static Pharmacy toEntity(PharmacyDTO dto) {
        if (dto == null) {
            return null;
        }

        Pharmacy entity = new Pharmacy();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setUrl(dto.getUrl());
        entity.setImgUrl(dto.getImgUrl());

        // Converte a lista de categorias associadas
        if (dto.getCategories() != null) {
            List<Category> categories = new ArrayList<>();
            for (CategoryDTO categoryDto : dto.getCategories()) {
                categories.add(CategoryConverter.toEntity(categoryDto));
            }
            entity.setCategories(categories);
        }

        return entity;
    }
}
