package br.com.webscraping.mapper;

import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.entities.Pharmacy;

public class PharmacyResponse {
    public static PharmacyResponseDTO toDto(Pharmacy entity) {
        if (entity == null) {
            return null;
        }

        PharmacyResponseDTO dto = new PharmacyResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    // Converte de PharmacyResponseDTO para Pharmacy (Entidade)
    public static Pharmacy toEntity(PharmacyResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(dto.getId());
        pharmacy.setName(dto.getName());

        return pharmacy;
    }
}
