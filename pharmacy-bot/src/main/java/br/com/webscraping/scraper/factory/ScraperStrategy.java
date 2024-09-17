package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import com.microsoft.playwright.Page;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryDTO> scrapeCategories(Long idPharmacy, Page page);
    List<ProductDTO> scrapeProductsByCategory(PharmacyResponseDTO pharmacy, CategoryDTO category, Page page);
    int getTotalPages(CategoryDTO categoryDTO, Page page);
}
