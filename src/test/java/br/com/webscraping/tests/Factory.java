package br.com.webscraping.tests;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.entities.Category;
import br.com.webscraping.mapper.CategoryMapper;
import br.com.webscraping.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

public class Factory {

    private static final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private static final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    public static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(100.0);
        product.setImgUrl("http://img.com/img.png");
        product.setUrl("http://product.com");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        Category category = createCategory();
        category.getProducts().add(product);
        product.setCategory(category);
        return product;
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Name");
        return category;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return productMapper.toDto(product);
    }
}
