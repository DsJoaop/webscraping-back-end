package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.repositories.CategoryRepository;
import br.com.webscraping.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CategoryServiceIT {

    @Autowired
    private CategoryService service;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private Factory factory;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalCategories;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalCategories = repository.count();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPagedDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<CategoryDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }


    @Test
    public void insertShouldReturnCategoryDTOCreated() {
        CategoryDTO categoryDTO = factory.createCategoryDTO();
        categoryDTO = service.insert(categoryDTO);
        Assertions.assertNotNull(categoryDTO.getId());
    }


    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
    }
}
