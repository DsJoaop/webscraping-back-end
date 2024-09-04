package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryResponseDTO;
import br.com.webscraping.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper extends EntityMapper<CategoryResponseDTO, Category>{
    @Override
    CategoryResponseDTO toDto(Category entity);

}