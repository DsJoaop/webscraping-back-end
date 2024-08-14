package br.com.webscraping.resources;

import br.com.webscraping.repositories.ProductRepository;
import br.com.webscraping.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;
    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 4L;
        countTotalProducts = repository.count();
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingId));

    }

}
