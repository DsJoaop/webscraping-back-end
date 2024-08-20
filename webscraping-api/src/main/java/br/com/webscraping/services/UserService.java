package br.com.webscraping.services;

import br.com.webscraping.dto.RoleDTO;
import br.com.webscraping.dto.UserDTO;
import br.com.webscraping.dto.UserInsertDTO;
import br.com.webscraping.entities.Role;
import br.com.webscraping.entities.User;
import br.com.webscraping.exceptions.DatabaseException;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.UserMapper;
import br.com.webscraping.repositories.RoleRepository;
import br.com.webscraping.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = mapper.toEntity(dto);
        copyDtoToEntityRole(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            User entity = mapper.toEntity(dto);
            copyDtoToEntityRole(dto, entity);
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
    public Page<UserDTO> findAllPaged(Pageable pageRequest) {
        Page<User> list = repository.findAll(pageRequest);
        return list.map(mapper::toDto);
    }

    private void copyDtoToEntityRole(UserDTO dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.getRoles().clear();
        for (RoleDTO catDto : dto.getRoles()) {
            Optional<Role> Role = roleRepository.findById(catDto.getId());
            Role.ifPresent(entity.getRoles()::add);
        }
    }

}
