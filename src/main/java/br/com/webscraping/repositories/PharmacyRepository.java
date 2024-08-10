package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import br.com.webscraping.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
