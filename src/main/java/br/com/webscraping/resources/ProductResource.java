package br.com.webscraping.resources;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // Gera um construtor para todos os campos com "final"
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService service;

    // Alternativamente, você poderia remover a anotação @RequiredArgsConstructor e usar @Autowired
    // para a injeção de dependência em cada campo individualmente. Nesse caso, o código seria algo assim:
    //
    // @Autowired
    // private ProductService service;
    //
    // Com @Autowired, você deve anotar cada campo com @Autowired, o que pode ser mais verboso.

    @PostMapping("/create") //Acessar /api/products/create e enviar o corpo nessa URL
    public ResponseEntity<ProductDTO> create(ProductDTO dto){
        ProductDTO newProduct = service.create(dto);
        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("/update/{id}") // /api/products/update/{id}, não precisa do /value, coisa do passado. oque tem { } significa que é um PathVariable,
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){
        ProductDTO newProduct = service.create(dto);
        return ResponseEntity.ok().body(newProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(Long id){
        ProductDTO productDTO = service.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping // aqui não precisa de nada pq é a própria URL base, /api/products
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> productDTOList = service.findAll();
        return ResponseEntity.ok().body(productDTOList);

        //Alternativa: eu gosto mais do jeito abaixo, mas tanto faz, os 2 são a mesma coisa
        //O primeiro parametro é o body (oque vai retornar no JSON), e o segundo o status HTTP que vai retornar
        //return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }





}
