package br.com.webscraping.mapper;


import br.com.webscraping.dto.CategoryResponseDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.entities.Pharmacy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryResponseDTO.class)
public interface PharmacyMapper extends EntityMapper<PharmacyDTO, Pharmacy> {

    @Override
    @Mapping(target = "categories", source = "categories")
    Pharmacy toEntity(PharmacyDTO dto);

    @Override
    @Mapping(target = "categories", source = "categories")
    PharmacyDTO toDto(Pharmacy entity);

}
