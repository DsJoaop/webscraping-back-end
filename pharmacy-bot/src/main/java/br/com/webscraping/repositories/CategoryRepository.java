package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByPharmacy(Pharmacy pharmacy);
}
