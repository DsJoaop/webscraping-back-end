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
    public void deleteShouldDeleteObjectWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalCategories - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }

    @Test
    public void findAllPagedShouldReturnPageWhenPaged0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<CategoryDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalCategories, result.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPagedDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<CategoryDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnSortedPagedWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<CategoryDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Alimentos Saudáveis", result.getContent().get(0).getName());
        Assertions.assertEquals("Aparelhos de Saúde", result.getContent().get(1).getName());
        Assertions.assertEquals("Artigos Médicos", result.getContent().get(2).getName());
    }

    @Test
    public void updateShouldReturnCategoryDTOWhenIdExists() {
        CategoryDTO categoryDTO = service.findById(existingId);
        categoryDTO.setName("Teste");
        categoryDTO = service.update(existingId, categoryDTO);
        Assertions.assertEquals("Teste", categoryDTO.getName());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        CategoryDTO categoryDTO = service.findById(existingId);
        categoryDTO.setName("Teste");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, categoryDTO));
    }

    @Test
    public void insertShouldReturnCategoryDTOCreated() {
        CategoryDTO categoryDTO = Factory.createCategoryDTO();
        categoryDTO = service.insert(categoryDTO);
        Assertions.assertNotNull(categoryDTO.getId());
    }

    @Test
    public void findByIdShouldReturnCategoryDTOWhenIdExists() {
        CategoryDTO categoryDTO = service.findById(existingId);
        Assertions.assertNotNull(categoryDTO);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
    }
}
