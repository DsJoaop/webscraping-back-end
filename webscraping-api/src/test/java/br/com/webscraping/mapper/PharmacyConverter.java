package br.com.webscraping.mapper;


import br.com.webscraping.dto.CategoryResponseDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;

public class PharmacyConverter {

    public static Pharmacy toEntity(PharmacyDTO dto) {
        if (dto == null) {
            return null;
        }

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(dto.getId());
        pharmacy.setName(dto.getName());
        pharmacy.setAddress(dto.getAddress());
        pharmacy.setPhone(dto.getPhone());
        pharmacy.setCity(dto.getCity());
        pharmacy.setState(dto.getState());
        pharmacy.setZipCode(dto.getZipCode());
        pharmacy.setUrl(dto.getUrl());
        pharmacy.setImgUrl(dto.getImgUrl());


        if (dto.getCategories() != null) {
            dto.getCategories().forEach(categoryDTO -> {
                Category category = CategoryResponse.toEntity(categoryDTO);
                pharmacy.getCategories().add(category);
            });
        }

        return pharmacy;
    }

    // Converte de Pharmacy (Entidade) para PharmacyDTO
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

        // Converter as categorias associadas
        if (entity.getCategories() != null) {
            entity.getCategories().forEach(category -> {
                CategoryResponseDTO categoryDTO = CategoryResponse.toDto(category);
                dto.getCategories().add(categoryDTO);
            });
        }

        return dto;
    }
}
