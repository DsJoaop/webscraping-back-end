package br.com.webscraping.resources;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.services.CategoryService;
import br.com.webscraping.utils.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryResource.class)
public class CategoryResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    private CategoryDTO categoryDTO;
    private PageImpl<CategoryDTO> page;
    private long existingId;
    private long nonExistingId;
    private long dependentId;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 4L;
        categoryDTO = Factory.createCategoryDTO();
        page = new PageImpl<>(List.of(categoryDTO));

        when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
        when(service.findById(existingId)).thenReturn(categoryDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(categoryDTO);
        when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
        when(service.insert(ArgumentMatchers.any())).thenReturn(categoryDTO);

        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    @Test
    public void deleteShouldReturnDatabaseExceptionWhenIdDependent() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", dependentId));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnCategoryDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(post("/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void updateShouldReturnCategoryDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(put("/categories/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(put("/categories/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnCategoryWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/categories/{id}", existingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/categories/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
