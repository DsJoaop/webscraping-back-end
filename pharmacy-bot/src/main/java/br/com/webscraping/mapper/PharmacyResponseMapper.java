package br.com.webscraping.mapper;

import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.entities.Pharmacy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PharmacyResponseMapper extends EntityMapper<PharmacyResponseDTO, Pharmacy>{
    @Override
    PharmacyResponseDTO toDto(Pharmacy entity);

}