package br.com.webscraping.mapper;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Override
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDTO dto);

    @Override
    ProductDTO toDto(Product entity);
}
