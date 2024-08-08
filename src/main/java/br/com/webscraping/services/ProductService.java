package br.com.webscraping.services;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import br.com.webscraping.exceptions.ResourceNotFoundException;
import br.com.webscraping.mapper.ProductMapper;
import br.com.webscraping.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductDTO create(ProductDTO dto){
        Product entity = mapper.toEntity(dto);
        repository.save(entity);

        return mapper.toDto(entity);
    }

    public ProductDTO update(Long id, ProductDTO dto){

        entityExistyById(id); //Se por acaso não achar a entidade, vai cair na exceção do entityExitsById, se não, vai passar de boa

        Product entity = mapper.toEntity(dto);
        entity.setId(id);  //Seta o id passado na URL, /api/products/update/1 , vai setar o 1 no id. Se não setar, ele cria outro ao invés de atualizar. Da pra fazer de outros jeitos tbm
        repository.save(entity);

        return mapper.toDto(entity);
    }

    public void delete(Long id){
        Product entity = findEntityById(id); // Se por acaso não achar a entidade, vai cair na exceção do findEntityById, se achar, vai excluir. ai não precisa fazer outra validação aqui
        repository.delete(entity);
    }

    public List<ProductDTO> findAll(){
        List<Product> productList = repository.findAll();
        return mapper.toDto(productList);  //O mapper converte List, por causa do EntityMapper, ai não precisa usar o stream do Java
    }

    public void entityExistyById(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(("Entity not found"));
        }
    }

    public Product findEntityById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Entity not found")));
    }

    public ProductDTO findById(Long id){
        Product entity = findEntityById(id);    // Se por acaso não achar a entidade, vai cair na exceção do findEntityById, se achar, retorna o objeto achado no banco
        return mapper.toDto(entity);
    }

}
