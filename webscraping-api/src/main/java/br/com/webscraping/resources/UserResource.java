package br.com.webscraping.resources;

import br.com.webscraping.dto.UserDTO;
import br.com.webscraping.dto.UserInsertDTO;
import br.com.webscraping.dto.UserUpdateDTO;
import br.com.webscraping.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService service;
    private final PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(Pageable pageable) {
        Page<UserDTO> list = service.findAllPaged(pageable);
        PagedModel<EntityModel<UserDTO>> pagedModel = pagedResourcesAssembler.toModel(list);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

}
