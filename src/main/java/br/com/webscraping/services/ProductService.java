package br.com.webscraping.services;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.ProductMapper;
import br.com.webscraping.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            mapper.toEntity(dto);
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

    public Page<ProductDTO> findAllPage(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(mapper::toDto);
    }
}