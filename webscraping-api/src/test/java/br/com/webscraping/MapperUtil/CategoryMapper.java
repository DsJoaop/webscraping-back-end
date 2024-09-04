package br.com.webscraping.MapperUtil;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.mapper.EntityMapper;
import br.com.webscraping.mapper.PharmacyResponseMapper;
import br.com.webscraping.mapper.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public abstract class CategoryMapper implements EntityMapper<CategoryDTO, Category> {

    private final PharmacyResponseMapper pharmacyResponseMapper = Mappers.getMapper(PharmacyResponseMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    @Mapping(target = "products", source = "products")
    @Mapping(target = "subcategories", source = "subcategories")
    public abstract Category toEntity(CategoryDTO dto);

    @Override
    @Mapping(target = "products", source = "products")
    @Mapping(target = "subcategories", source = "subcategories")
    @Mapping(target = "pharmacies", source = "pharmacies")
    public abstract CategoryDTO toDto(Category entity);


    protected PharmacyResponseMapper getPharmacyResponseMapper() {
        return pharmacyResponseMapper;
    }

    protected ProductMapper getProductMapper() {
        return productMapper;
    }
}
