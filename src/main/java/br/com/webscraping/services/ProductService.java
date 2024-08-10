package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Product;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.ProductMapper;
import br.com.webscraping.repositories.CategoryRepository;
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
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDto(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = mapper.toEntity(dto);
        copyDtoToEntityProducts(dto, entity);
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            Product entity = mapper.toEntity(dto);
            entity.setId(id);
            entity = repository.save(entity);
            return toDto(entity);
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
    public Page<ProductDTO> findAllPage(Pageable pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(mapper::toDto);
    }

    private void copyDtoToEntityProducts(ProductDTO dto, Product entity) {
        if (dto.getCategory() != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategory().getId());
            categoryOpt.ifPresent(entity::setCategory);
        }
    }

    private ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        } else {
            ProductDTO productDTO = mapper.toDto(entity);
            if (entity.getCategory() != null) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(entity.getCategory().getId());
                categoryDTO.setName(entity.getCategory().getName());
                categoryDTO.setUrl(entity.getCategory().getUrl());
                productDTO.setCategory(categoryDTO);
            }
            return productDTO;
        }
    }
}
