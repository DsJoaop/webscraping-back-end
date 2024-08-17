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

    public static Product createProduct() {
        return createProduct(1, createCategory(1));
    }

    public static Category createCategory() {
        return createCategory(1);
    }

    public static Pharmacy createPharmacy() {
        return createPharmacy(1);
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

    public static Pharmacy createPharmacy(int id) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId((long) id);
        pharmacy.setName("Pharmacy Name");
        pharmacy.setAddress("1234 Pharmacy St");
        pharmacy.setPhone("(123) 456-7890");
        pharmacy.setCity("City Name");
        pharmacy.setState("State Name");
        pharmacy.setZipCode("12345-678");
        pharmacy.setUrl("http://pharmacy.com");
        pharmacy.setImgUrl("http://pharmacy.com/logo.png");
        pharmacy.setCreatedAt(Instant.now());
        pharmacy.setUpdatedAt(Instant.now());

        List<Category> categories = createCategories(4, pharmacy);
        pharmacy.setCategories(categories);

        return pharmacy;
    }

    private static Product createProduct(int id, Category category) {
        Product product = new Product();
        populateProductFields(product, id);

        // Associa a categoria ao produto se fornecida
        if (category != null) {
            product.setCategory(category);
            category.getProducts().add(product);
        }

        return product;
    }

    private static void populateProductFields(Product product, int id) {
        product.setId((long) id);
        product.setName("Product Name " + id);
        product.setDescription("Product Description " + id);
        product.setPrice(100.0 + id);
        product.setImgUrl("http://img.com/img" + id + ".png");
        product.setUrl("http://product.com/" + id);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
    }

    public static Category createCategory(int id) {
        Category category = new Category();
        category.setId((long) id);
        category.setName("Category Name " + id);
        category.setUrl("https://category.com/" + id);

        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            products.add(createProduct(i, category));
        }
        category.setProducts(products);

        return category;
    }

    private static List<Category> createCategories(int numberOfCategories, Pharmacy pharmacy) {
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= numberOfCategories; i++) {
            Category category = createCategory(i);
            category.setPharmacy(pharmacy);
            categories.add(category);
        }
        return categories;
    }
}
