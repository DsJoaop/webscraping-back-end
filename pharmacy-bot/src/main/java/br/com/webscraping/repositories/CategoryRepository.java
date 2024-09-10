package br.com.webscraping.repositories;

import br.com.webscraping.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query(nativeQuery = true, value = """
        SELECT c.*
        FROM TB_CATEGORY c
        JOIN TB_PHARMACY_CATEGORY pc ON c.ID = pc.CATEGORY_ID
        WHERE pc.PHARMACY_ID = :pharmacyId
    """)
    List<Category> findCategoryByPharmacy(@Param("pharmacyId") Long pharmacyId);

}

