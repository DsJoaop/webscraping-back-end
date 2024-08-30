package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryDTO> scrapeCategories() throws Exception;
    List<ProductDTO> scrapeProductsByCategoryAndPage(CategoryDTO category, int totalPages);
    int getTotalPages(CategoryDTO categoryDTO) throws Exception;
}
