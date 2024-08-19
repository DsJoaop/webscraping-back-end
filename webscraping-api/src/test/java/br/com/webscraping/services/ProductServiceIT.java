package br.com.webscraping.services;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.repositories.ProductRepository;
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
public class ProductServiceIT {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;
    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = repository.count();
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findAllPagedShouldReturnPageWhenPaged0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPagedDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnSortedPagedWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Antisséptico Bucal 250ml", result.getContent().get(0).getName());
        Assertions.assertEquals("Balança Digital", result.getContent().get(1).getName());
        Assertions.assertEquals("Band-Aid", result.getContent().get(2).getName());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = service.findById(existingId);
        productDTO.setName("Teste");
        productDTO = service.update(existingId, productDTO);
        Assertions.assertEquals("Teste", productDTO.getName());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        ProductDTO productDTO = service.findById(existingId);
        productDTO.setName("Teste");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productDTO);
        });
    }

    @Test
    public void insertShouldReturnProductDTOCreated() {
        ProductDTO productDTO = Factory.createProductDTO();
        productDTO = service.insert(productDTO);
        Assertions.assertNotNull(productDTO.getId());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = service.findById(existingId);
        Assertions.assertNotNull(productDTO);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }
}
