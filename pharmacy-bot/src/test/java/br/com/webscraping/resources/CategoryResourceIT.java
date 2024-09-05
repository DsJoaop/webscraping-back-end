package br.com.webscraping.resources;


import br.com.webscraping.repositories.CategoryRepository;
import br.com.webscraping.services.CategoryService;
import br.com.webscraping.utils.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Factory factory;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
    }



    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPageWhenPage0Size10() throws Exception {
        ResultActions result = mockMvc.perform(get("/categories?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }



    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/categories/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnCategoryDTOCreated() throws Exception {
        ResultActions result = mockMvc.perform(post("/categories")
                .content(objectMapper.writeValueAsString(factory.createCategoryDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }


    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(put("/categories/{id}", nonExistingId)
                .content(objectMapper.writeValueAsString(factory.createCategoryDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPageWhenPage1Size5() throws Exception {
        ResultActions result = mockMvc.perform(get("/categories?page=1&size=5")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    /*
     Testa se o método insert retorna status 400 Bad Request quando os dados são inválidos.

    @Test
    public void insertShouldReturnBadRequestWhenDataIsInvalid() throws Exception {
        CategoryDTO invalidCategory = new CategoryDTO();
        invalidCategory.setName(""); // Nome inválido
        invalidCategory.setUrl(""); // URL inválida

        ResultActions result = mockMvc.perform(post("/categories")
                .content(objectMapper.writeValueAsString(invalidCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void updateShouldReturnBadRequestWhenDataIsInvalid() throws Exception {
        CategoryDTO invalidCategory = new CategoryDTO();
        invalidCategory.setName(""); // Nome inválido

        ResultActions result = mockMvc.perform(put("/categories/{id}", existingId)
                .content(objectMapper.writeValueAsString(invalidCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isBadRequest());
    }
     */
}
