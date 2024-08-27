package br.com.webscraping.resources;


import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.repositories.ProductRepository;
import br.com.webscraping.services.ProductService;
import br.com.webscraping.utils.Factory;
import br.com.webscraping.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenUtil tokenUtil;

    private String username, password, bearerToken;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = repository.count();

        username = "maria@gmail.com";
        password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPageWhenPage0Size10() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/products?page=3&size=5&sort=name,asc").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(countTotalProducts));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        String expectedName = productDTO.getName();
        String expectedDescription = productDTO.getDescription();

        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(expectedName));
        result.andExpect(jsonPath("$.description").value(expectedDescription));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

}
