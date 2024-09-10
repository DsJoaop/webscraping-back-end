package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.CategoryMapper;
import br.com.webscraping.repositories.CategoryRepository;
import br.com.webscraping.repositories.PharmacyRepository;
import br.com.webscraping.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final ProductRepository productRepository;
    private final PharmacyRepository pharmacyRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = mapper.toEntity(dto);
        updateEntityAssociations(dto, entity);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            Category entity = mapper.toEntity(dto);
            entity.setId(id);
            updateEntityAssociations(dto, entity);
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
    public Page<CategoryDTO> findAllPaged(Pageable pageRequest) {
        return repository.findAll(pageRequest).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAllByPharmacy(Long id) {
        return mapper.toDto(repository.findCategoryByPharmacy(id));
    }

    private void updateEntityAssociations(CategoryDTO dto, Category entity) {
        updateEntityProducts(dto, entity);
        updateEntitySubcategories(dto, entity);
        updateEntityPharmacies(dto, entity);
    }

    private void updateEntityProducts(CategoryDTO dto, Category entity) {
        entity.getProducts().clear();
        if (dto.getProducts() != null) {
            dto.getProducts().forEach(productDTO ->
                    productRepository.findById(productDTO.getId())
                            .ifPresent(product -> {
                                product.setCategory(entity);
                                entity.getProducts().add(product);
                            })
            );
        }
    }

    private void updateEntitySubcategories(CategoryDTO dto, Category entity) {
        entity.getSubcategories().clear();
        if (dto.getSubcategories() != null) {
            dto.getSubcategories().forEach(subcategoryDTO -> {
                Category subcategory = subcategoryDTO.getId() != null
                        ? repository.findById(subcategoryDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"))
                        : mapper.toEntity(subcategoryDTO);

                if (subcategory.getId() == null) {
                    updateEntityAssociations(subcategoryDTO, subcategory);
                    subcategory.setParentCategory(entity);
                    subcategory = repository.save(subcategory);
                }
                subcategory.setParentCategory(entity);
                entity.getSubcategories().add(subcategory);
            });
        }
    }

    private void updateEntityPharmacies(CategoryDTO dto, Category entity) {
        entity.getPharmacies().clear();
        if (dto.getPharmacies() != null) {
            dto.getPharmacies().forEach(pharmacyDTO ->
                    pharmacyRepository.findById(pharmacyDTO.getId())
                            .ifPresent(pharmacy -> {
                                pharmacy.getCategories().add(entity);
                                entity.getPharmacies().add(pharmacy);
                            })
            );
        }
    }
}
