package br.com.webscraping.resources;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.repositories.PharmacyRepository;
import br.com.webscraping.services.PharmacyService;
import br.com.webscraping.utils.Factory;
import br.com.webscraping.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
public class PharmacyResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PharmacyService service;

    @Autowired
    private PharmacyRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private String username, password, bearerToken;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPharmacies;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalPharmacies = repository.count();

        username = "maria@gmail.com";
        password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/pharmacies/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result =
                mockMvc.perform(delete("/pharmacies/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPageWhenPage0Size10() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/pharmacies?page=0&size=10&sort=name,asc")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(countTotalPharmacies));
    }

    @Test
    public void updateShouldReturnPharmacyDTOWhenIdExists() throws Exception {
        PharmacyDTO pharmacyDTO = Factory.createPharmacyDTO();
        String expectedName = pharmacyDTO.getName();
        String expectedAddress = pharmacyDTO.getAddress();

        String jsonBody = objectMapper.writeValueAsString(pharmacyDTO);
        ResultActions result = mockMvc.perform(put("/pharmacies/{id}", existingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(expectedName));
        result.andExpect(jsonPath("$.address").value(expectedAddress));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        PharmacyDTO pharmacyDTO = Factory.createPharmacyDTO();
        String jsonBody = objectMapper.writeValueAsString(pharmacyDTO);
        ResultActions result = mockMvc.perform(put("/pharmacies/{id}", nonExistingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnPharmacyWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/pharmacies/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.address").exists());
    }


    @Test
    public void insertShouldReturnPharmacyDTOCreated() throws Exception {
        PharmacyDTO pharmacyDTO = Factory.createPharmacyDTO();
        String jsonBody = objectMapper.writeValueAsString(pharmacyDTO);
        ResultActions result = mockMvc.perform(post("/pharmacies")
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.address").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/pharmacies/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/pharmacies")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
