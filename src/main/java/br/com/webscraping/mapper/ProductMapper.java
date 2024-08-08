package br.com.webscraping.mapper;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CategoryMapper.class)
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Override
    @Mapping(source = "category", target = "category", qualifiedByName = "toDtoWithoutProducts")
    ProductDTO toDto(Product entity);

    @Override
    @Mapping(source = "category", target = "category", qualifiedByName = "toEntityWithoutProducts")
    Product toEntity(ProductDTO dto);
}
