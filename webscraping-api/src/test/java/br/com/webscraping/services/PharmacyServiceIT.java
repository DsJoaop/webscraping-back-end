package br.com.webscraping.services;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.repositories.PharmacyRepository;
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
public class PharmacyServiceIT {
    @Autowired
    private PharmacyService service;
    @Autowired
    private PharmacyRepository repository;
    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPharmacies;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalPharmacies = repository.count();
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalPharmacies - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }

    @Test
    public void findAllPagedShouldReturnPageWhenPaged0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PharmacyDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalPharmacies, result.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPagedDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<PharmacyDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPagedShouldReturnSortedPagedWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<PharmacyDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Farmacia Central", result.getContent().get(0).getName()); // Ajuste conforme o nome esperado
        Assertions.assertEquals("Farmacia Popular", result.getContent().get(1).getName()); // Ajuste conforme o nome esperado
    }

    @Test
    public void updateShouldReturnPharmacyDTOWhenIdExists() {
        PharmacyDTO pharmacyDTO = service.findById(existingId);
        pharmacyDTO.setName("Teste");
        pharmacyDTO = service.update(existingId, pharmacyDTO);
        Assertions.assertEquals("Teste", pharmacyDTO.getName());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        PharmacyDTO pharmacyDTO = service.findById(existingId);
        pharmacyDTO.setName("Teste");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, pharmacyDTO));
    }

    @Test
    public void insertShouldReturnPharmacyDTOCreated() {
        PharmacyDTO pharmacyDTO = Factory.createPharmacyDTO();
        pharmacyDTO = service.insert(pharmacyDTO);
        Assertions.assertNotNull(pharmacyDTO.getId());
    }

    @Test
    public void findByIdShouldReturnPharmacyDTOWhenIdExists() {
        PharmacyDTO pharmacyDTO = service.findById(existingId);
        Assertions.assertNotNull(pharmacyDTO);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
    }
}
