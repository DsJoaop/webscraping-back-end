package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Product;
import br.com.webscraping.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.*;

@DataJpaTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ProductRepository productRepository;
    private final Factory factory = new Factory();

    private long existingId;
    private long nonExistingId;
    private long countTotalCategories;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalCategories = repository.count();
    }

    @Test
    public void saveCategoryWithProducts() {
        Category category = factory.createCategory();

        Set<Product> products = new HashSet<>();
        for (Product product : category.getProducts()) {
            Optional<Product> productOpt = productRepository.findById(product.getId());
            productOpt.ifPresent(products::add);
        }

        category.setProducts(products);
        category.setId(null);
        category = repository.save(category);

        // Recuperando a categoria do banco de dados para verificar o estado persistido
        Optional<Category> savedCategoryOpt = repository.findById(category.getId());
        Assertions.assertTrue(savedCategoryOpt.isPresent(), "Category should be present in the database");

        Category savedCategory = savedCategoryOpt.get();

        // Verificando se a categoria foi salva corretamente
        Assertions.assertEquals("Category Name", savedCategory.getName());
        Assertions.assertEquals("https://category.com/", savedCategory.getUrl());
        Assertions.assertEquals(countTotalCategories + 1, category.getId());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Category> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Category> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }
}
