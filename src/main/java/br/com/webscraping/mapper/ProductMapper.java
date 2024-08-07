package br.com.webscraping.mapper;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Override
    @Mapping(target = "categories", source = "categories")
    ProductDTO toDto(Product entity);

    @Override
    @Mapping(target = "categories", source = "categories")
    Product toEntity(ProductDTO dto);

    void updateEntityFromDto(ProductDTO dto, @MappingTarget Product entity);
}
