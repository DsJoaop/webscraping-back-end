package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.CategoryScrapingDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryScrapingDTO> scrapeCategories() throws Exception;
    List<ProductDTO> scrapeProductsByCategoryAndPage(PharmacyDTO pharmacy, CategoryDTO category, int totalPages);
    int getTotalPages(CategoryDTO categoryDTO) throws Exception;
}
