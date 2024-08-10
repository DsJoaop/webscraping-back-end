package br.com.webscraping.repositories;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
