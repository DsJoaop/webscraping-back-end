package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import br.com.webscraping.entities.Product;
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
import java.util.Optional;

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
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = mapper.toEntity(dto);
        copyDtoToEntityProducts(dto, entity);
        copyDtoToEntitySubcategories(dto, entity);
        copyDtoToEntityPharmacies(dto, entity);
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
            copyDtoToEntityProducts(dto, entity);
            copyDtoToEntitySubcategories(dto, entity);
            copyDtoToEntityPharmacies(dto, entity);
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
        Page<Category> list = repository.findAll(pageRequest);
        return list.map(mapper::toDto);
    }

    private void copyDtoToEntityProducts(CategoryDTO dto, Category entity) {
        if (dto.getProducts() != null) {
            entity.getProducts().clear();
            for (ProductDTO productDTO : dto.getProducts()) {
                Optional<Product> productOpt = productRepository.findById(productDTO.getId());
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    product.setCategory(entity);
                    entity.getProducts().add(product);
                }
            }
        }
    }


    private void copyDtoToEntitySubcategories(CategoryDTO dto, Category entity) {
        if (dto.getSubcategories() != null) {
            entity.getSubcategories().clear();
            for (CategoryDTO subcategoryDTO : dto.getSubcategories()) {
                Category subcategory;
                if (subcategoryDTO.getId() != null) {
                    subcategory = repository.findById(subcategoryDTO.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
                } else {
                    subcategory = mapper.toEntity(subcategoryDTO);
                }
                subcategory.setParentCategory(entity);
                entity.getSubcategories().add(subcategory);
            }
        }
    }

    private void copyDtoToEntityPharmacies(CategoryDTO dto, Category entity) {
        if (dto.getPharmacies() != null) {
            entity.getPharmacies().clear();
            for (PharmacyResponseDTO pharmacyDTO : dto.getPharmacies()) {
                Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(pharmacyDTO.getId());
                if (pharmacyOpt.isPresent()) {
                    Pharmacy pharmacy = pharmacyOpt.get();
                    pharmacy.getCategories().add(entity);
                    entity.getPharmacies().add(pharmacy);
                }
            }
        }
    }
}
