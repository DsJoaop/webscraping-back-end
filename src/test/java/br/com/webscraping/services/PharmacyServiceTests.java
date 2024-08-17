package br.com.webscraping.services;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.PharmacyMapper;
import br.com.webscraping.repositories.PharmacyRepository;
import br.com.webscraping.repositories.CategoryRepository;
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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PharmacyServiceTests {

    @InjectMocks
    private PharmacyService service;

    @Mock
    private PharmacyRepository repository;

    @Mock
    private PharmacyMapper mapper;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Pharmacy> page;
    private Pharmacy pharmacy;
    private PharmacyDTO pharmacyDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 4L;
        pharmacy = Factory.createPharmacy();
        pharmacyDTO = Factory.createPharmacyDTO();
        page = new PageImpl<>(List.of(pharmacy));

        // PharmacyRepository
        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        when(repository.save(ArgumentMatchers.any())).thenReturn(pharmacy);

        when(repository.findById(existingId)).thenReturn(Optional.of(pharmacy));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        // PharmacyMapper
        when(mapper.toDto(pharmacy)).thenReturn(pharmacyDTO);
        when(mapper.toEntity(pharmacyDTO)).thenReturn(pharmacy);
        when(mapper.toDto(List.of(pharmacy))).thenReturn(List.of(pharmacyDTO));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }

    @Test
    public void deleteShouldCallRepositoryDeleteWhenIdExists() {
        service.delete(existingId);
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> service.delete(dependentId));
        verify(repository, times(1)).deleteById(dependentId);
    }

    @Test
    public void findAllShouldReturnList() {
        List<Pharmacy> pharmacies = List.of(pharmacy);
        when(repository.findAll()).thenReturn(pharmacies);

        List<PharmacyDTO> result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findByIdShouldReturnPharmacyDTOWhenIdExists() {
        PharmacyDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllPageShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        service.findAllPage(pageable);
        verify(repository, times(1)).findAll(pageable);
    }
}
