package br.com.webscraping.utils;

import br.com.webscraping.MapperUtil.CategoryMapper;
import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.mapper.PharmacyMapper;
import br.com.webscraping.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Factory {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final PharmacyMapper pharmacyMapper = Mappers.getMapper(PharmacyMapper.class);
    private long productId = 1L;
    private long categoryId = 1L;
    private long pharmacyId = 1L;

    public Product createProduct() {
        Product product = new Product();
        product.setId(productId++);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPriceFinal(100.0);
        product.setImgUrl("http://img.com/img.png");
        product.setUrl("http://product.com/");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setRating(1.0);
        product.setReviewsCount(1);
        product.setPriceFrom(10.0);

        Category category = createCategoryAUX();
        category.addProduct(product);

        return product;
    }

    public Category createCategory() {
        Category category = new Category();
        category.setId(categoryId++);
        category.setName("Category Name");
        category.setUrl("https://category.com/");

        Product product = createProductAUX(category);
        Pharmacy pharmacy = createPharmacyAUX();
        category.addProduct(product);
        category.addPharmacy(pharmacy);

        return category;
    }

    private Pharmacy createPharmacyAUX() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(pharmacyId++);
        pharmacy.setName("Pharmacy Name");
        pharmacy.setAddress("1234 Pharmacy St");
        pharmacy.setPhone("(123) 456-7890");
        pharmacy.setCity("City Name");
        pharmacy.setState("State Name");
        pharmacy.setZipCode("12345-678");
        pharmacy.setUrl("https://pharmacy.com");
        pharmacy.setImgUrl("https://pharmacy.com/logo.png");
        pharmacy.setCreatedAt(Instant.now());
        pharmacy.setUpdatedAt(Instant.now());

        return pharmacy;
    }

    public Pharmacy createPharmacy() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(pharmacyId++);
        pharmacy.setName("Pharmacy Name");
        pharmacy.setAddress("1234 Pharmacy St");
        pharmacy.setPhone("(123) 456-7890");
        pharmacy.setCity("City Name");
        pharmacy.setState("State Name");
        pharmacy.setZipCode("12345-678");
        pharmacy.setUrl("https://pharmacy.com");
        pharmacy.setImgUrl("https://pharmacy.com/logo.png");
        pharmacy.setCreatedAt(Instant.now());
        pharmacy.setUpdatedAt(Instant.now());

        // Cria uma categoria e associa à farmácia
        Category category = createCategory();
        pharmacy.addCategory(category);

        return pharmacy;
    }

    public ProductDTO createProductDTO() {
        return productMapper.toDto(createProduct());
    }

    public CategoryDTO createCategoryDTO() {
        return categoryMapper.toDto(createCategory());
    }

    public PharmacyDTO createPharmacyDTO() {
        return pharmacyMapper.toDto(createPharmacy());
    }

    // Método auxiliar para criar um produto com uma categoria associada
    private Product createProductAUX(Category category) {
        Product product = new Product();
        product.setId(productId++);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPriceFinal(100.0);
        product.setImgUrl("http://img.com/img.png");
        product.setUrl("http://product.com/");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setRating(1.0);
        product.setReviewsCount(1);
        product.setPriceFrom(10.0);

        // Sincronize a relação bidirecional com a categoria
        category.addProduct(product);

        return product;
    }

    private Category createCategoryAUX() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Name");
        category.setUrl("https://category.com/");
        return category;
    }
}
