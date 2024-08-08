package br.com.webscraping.mapper;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = ProductMapper.class)
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Override
    @Mapping(source = "products", target = "products", ignore = true)
    CategoryDTO toDto(Category entity);

    @Override
    @Mapping(source = "products", target = "products", ignore = true)
    Category toEntity(CategoryDTO dto);

    @Named("toDtoWithoutCategory")
    default ProductDTO toDtoWithoutCategory(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImgUrl(product.getImgUrl());
        productDTO.setUrl(product.getUrl());
        return productDTO;
    }

    @Named("toEntityWithoutCategory")
    default Product toEntityWithoutCategory(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());
        product.setUrl(productDTO.getUrl());
        return product;
    }
}
