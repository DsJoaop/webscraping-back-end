package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends  EntityMapper<CategoryDTO, Category> {
}
