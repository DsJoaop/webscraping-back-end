package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query(nativeQuery = true, value = """
				SELECT * FROM TB_CATEGORY WHERE PHARMACY_ID = :pharmacy
			""")
    List<Category> findCategoryByPharmacy(Long pharmacy);
}
