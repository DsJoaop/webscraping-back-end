package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryDTO> scrapeCategories(Long idPharmacy) throws Exception;
    List<ProductDTO> scrapeProductsByCategoryAndPage(PharmacyResponseDTO pharmacy, CategoryDTO category, int totalPages);
    int getTotalPages(CategoryDTO categoryDTO) throws Exception;
}
