package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, PharmacyResponseMapper.class, CategoryMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Override
    @Mapping(target = "products", source = "products")
    @Mapping(target = "subcategories", source = "subcategories")
    Category toEntity(CategoryDTO dto);

    @Override
    @Mapping(target = "products", source = "products")
    @Mapping(target = "subcategories", source = "subcategories")
    @Mapping(target = "pharmacies", source = "pharmacies")
    CategoryDTO toDto(Category entity);

}
