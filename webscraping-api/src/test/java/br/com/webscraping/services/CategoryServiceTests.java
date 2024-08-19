package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.CategoryMapper;
import br.com.webscraping.mapper.ProductMapper;
import br.com.webscraping.repositories.CategoryRepository;
import br.com.webscraping.repositories.ProductRepository;
import br.com.webscraping.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Category> page;
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 4L;
        category = Factory.createCategory();
        categoryDTO = Factory.createCategoryDTO();
        page = new PageImpl<>(List.of(category));

        // CategoryRepository
        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        when(repository.save(ArgumentMatchers.any())).thenReturn(category);

        when(repository.findById(existingId)).thenReturn(Optional.of(category));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        // CategoryMapper
        when(mapper.toDto(category)).thenReturn(categoryDTO);
        when(mapper.toEntity(categoryDTO)).thenReturn(category);
        when(mapper.toDto(List.of(category))).thenReturn(List.of(categoryDTO));

        // ProductMapper
        when(productMapper.toDto(ArgumentMatchers.anyList())).thenReturn(categoryDTO.getProducts());
        when(productMapper.toEntity(ArgumentMatchers.anyList())).thenReturn(category.getProducts());
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldCallRepositoryDeleteWhenIdExists() {
        service.delete(existingId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void findAllShouldReturnList() {
        List<Category> categories = List.of(category);
        when(repository.findAll()).thenReturn(categories);

        List<CategoryDTO> result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnCategoryDTOWhenIdExists() {
        CategoryDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void insertShouldSaveAndReturnCategoryDTO() {
        CategoryDTO result = service.insert(categoryDTO);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(category);
    }

    @Test
    public void updateShouldSaveAndReturnCategoryDTOWhenIdExists() {
        CategoryDTO result = service.update(existingId, categoryDTO);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(category);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, categoryDTO);
        });
    }

    @Test
    public void findAllPageShouldReturnPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        service.findAllPaged(pageable);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }
}