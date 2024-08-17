package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import br.com.webscraping.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repository;
    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private Category category = Factory.createCategory();

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = repository.count();
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        category.setId(null);
        category = repository.save(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(countTotalProducts + 1, category.getId());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Category result = repository.findById(existingId).get();
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Category result = repository.findById(nonExistingId).orElse(null);
        Assertions.assertNull(result);
    }
}
