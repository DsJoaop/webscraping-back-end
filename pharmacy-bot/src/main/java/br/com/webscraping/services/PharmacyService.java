package br.com.webscraping.services;


import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.CategoryResponseDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.PharmacyMapper;
import br.com.webscraping.repositories.CategoryRepository;
import br.com.webscraping.repositories.PharmacyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository repository;
    private final PharmacyMapper mapper;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<PharmacyDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional(readOnly = true)
    public PharmacyDTO findById(Long id) {
        Optional<Pharmacy> obj = repository.findById(id);
        Pharmacy entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public PharmacyDTO insert(PharmacyDTO dto) {
        Pharmacy entity = mapper.toEntity(dto);
        copyDtoToEntityCategory(dto, entity);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public PharmacyDTO update(Long id, PharmacyDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            Pharmacy entity = mapper.toEntity(dto);
            copyDtoToEntityCategory(dto, entity);
            entity.setId(id);
            entity = repository.save(entity);
            return mapper.toDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional(readOnly = true)
    public Page<PharmacyDTO> findAllPaged(Pageable pageRequest) {
        Page<Pharmacy> list = repository.findAll(pageRequest);
        return list.map(mapper::toDto);
    }

    private void copyDtoToEntityCategory(PharmacyDTO dto, Pharmacy entity) {
        entity.getCategories().clear();
        for (CategoryResponseDTO catDto : dto.getCategories()) {
            Optional<Category> category = categoryRepository.findById(catDto.getId());
            if (category.isPresent()) {
                Category category1 = category.get();
                category1.getPharmacies().add(entity);
                entity.getCategories().add(category1);
            }
        }
    }
}
