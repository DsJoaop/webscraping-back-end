
package br.com.webscraping.resources;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pharmacies")
public class PharmacyResource {
    private final PharmacyService service;
    private final PagedResourcesAssembler<PharmacyDTO> pagedResourcesAssembler;


    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PharmacyDTO>>> findAll(Pageable peageable) {
        Page<PharmacyDTO> list = service.findAllPaged(peageable);
        PagedModel<EntityModel<PharmacyDTO>> pagedModel = pagedResourcesAssembler.toModel(list);
        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacyDTO> findById(@PathVariable Long id) {
        PharmacyDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PharmacyDTO> update(@PathVariable Long id, @RequestBody PharmacyDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PharmacyDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PharmacyDTO> insert(@RequestBody PharmacyDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
