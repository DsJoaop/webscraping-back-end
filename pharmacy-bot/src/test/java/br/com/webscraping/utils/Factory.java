package br.com.webscraping.utils;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.mapper.CategoryConverter;
import br.com.webscraping.mapper.PharmacyConverter;
import br.com.webscraping.mapper.ProductConverter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Factory {

    private static long productId = 1L;
    private static long categoryId = 1L;
    private static long pharmacyId = 1L;

    public static Product createProduct() {
        Product product = new Product();
        product.setId(productId++);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(100.0);
        product.setImgUrl("http://img.com/img.png");
        product.setUrl("http://product.com/");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        // Cria uma categoria e associa ao produto
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Name");
        category.setUrl("https://category.com/");

        return product;
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setId(categoryId++);
        category.setName("Category Name");
        category.setUrl("https://category.com/");

        // Cria um produto e adiciona à categoria
        Product product = createProductAUX(category);
        category.getProducts().add(product);
        product.setCategory(category);

        return category;
    }

    public static Pharmacy createPharmacy() {
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

        // Cria uma categoria e adiciona à farmácia
        Category category = createCategory();
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        pharmacy.setCategories(categories);

        return pharmacy;
    }

    public static ProductDTO createProductDTO() {
        return ProductConverter.toDto(createProduct());
    }

    public static CategoryDTO createCategoryDTO() {
        return CategoryConverter.toDto(createCategory());
    }

    public static PharmacyDTO createPharmacyDTO() {
        return PharmacyConverter.toDto(createPharmacy());
    }

    private static Product createProductAUX(Category category) {
        Product product = new Product();
        product.setId(productId++);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(100.0);
        product.setImgUrl("http://img.com/img.png");
        product.setUrl("http://product.com/");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setCategory(category);

        return product;
    }
}
