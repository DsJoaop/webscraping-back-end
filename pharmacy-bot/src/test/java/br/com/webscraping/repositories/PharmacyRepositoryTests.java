package br.com.webscraping.repositories;

import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.entities.Category;
import br.com.webscraping.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

@DataJpaTest
public class PharmacyRepositoryTests {

    @Autowired
    private PharmacyRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;
    private final Factory factory = new Factory();

    private long existingId;
    private long nonExistingId;
    private long countTotalPharmacies;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalPharmacies = repository.count();
    }

    @Test
    public void savePharmacyWithCategories() {
        Pharmacy pharmacy = factory.createPharmacy();

        Set<Category> categories = new HashSet<>();
        for (Category category : pharmacy.getCategories()) {
            Optional<Category> categoryOpt = categoryRepository.findById(category.getId());
            categoryOpt.ifPresent(categories::add);
        }

        pharmacy.setCategories(categories);
        pharmacy.setId(null);
        pharmacy = repository.save(pharmacy);

        // Recuperando a farmácia do banco de dados para verificar o estado persistido
        Optional<Pharmacy> savedPharmacyOpt = repository.findById(pharmacy.getId());
        Assertions.assertTrue(savedPharmacyOpt.isPresent(), "Pharmacy should be present in the database");

        Pharmacy savedPharmacy = savedPharmacyOpt.get();

        // Verificando se a farmácia foi salva corretamente
        Assertions.assertEquals("Pharmacy Name", savedPharmacy.getName());
        Assertions.assertEquals("1234 Pharmacy St", savedPharmacy.getAddress());
        Assertions.assertEquals("(123) 456-7890", savedPharmacy.getPhone());
        Assertions.assertEquals("City Name", savedPharmacy.getCity());
        Assertions.assertEquals("State Name", savedPharmacy.getState());
        Assertions.assertEquals("12345-678", savedPharmacy.getZipCode());
        Assertions.assertEquals("https://pharmacy.com", savedPharmacy.getUrl());
        Assertions.assertEquals("https://pharmacy.com/logo.png", savedPharmacy.getImgUrl());
        Assertions.assertNotNull(savedPharmacy.getCreatedAt());
        Assertions.assertNotNull(savedPharmacy.getUpdatedAt());
        Assertions.assertEquals(countTotalPharmacies + 1, savedPharmacy.getId());
    }

    @Test
    public void updateShouldChangeAndPersistDataWhenIdExists() {
        // Recuperando a farmácia existente
        Pharmacy pharmacy = repository.findById(existingId)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));

        // Modificando os atributos
        pharmacy.setName("Updated Pharmacy Name");
        pharmacy.setAddress("Updated Address");
        pharmacy.setPhone("(987) 654-3210");
        pharmacy.setCity("Updated City");
        pharmacy.setState("Updated State");
        pharmacy.setZipCode("87654-321");
        pharmacy.setUrl("https://updatedpharmacy.com");
        pharmacy.setImgUrl("https://updatedpharmacy.com/logo.png");

        // Salvando as mudanças
        repository.save(pharmacy);

        // Verificando se as alterações foram persistidas corretamente
        Pharmacy updatedPharmacy = repository.findById(existingId)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));
        Assertions.assertEquals("Updated Pharmacy Name", updatedPharmacy.getName());
        Assertions.assertEquals("Updated Address", updatedPharmacy.getAddress());
        Assertions.assertEquals("(987) 654-3210", updatedPharmacy.getPhone());
        Assertions.assertEquals("Updated City", updatedPharmacy.getCity());
        Assertions.assertEquals("Updated State", updatedPharmacy.getState());
        Assertions.assertEquals("87654-321", updatedPharmacy.getZipCode());
        Assertions.assertEquals("https://updatedpharmacy.com", updatedPharmacy.getUrl());
        Assertions.assertEquals("https://updatedpharmacy.com/logo.png", updatedPharmacy.getImgUrl());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Pharmacy> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Pharmacy> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Pharmacy> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

}