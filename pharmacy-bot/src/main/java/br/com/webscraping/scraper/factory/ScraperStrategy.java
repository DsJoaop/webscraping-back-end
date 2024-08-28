package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryDTO> scrapeCategories() throws Exception;
    List<ProductDTO> scrapeProductsByCategoryAndPage(CategoryDTO categoryLink, int pageNumber) throws Exception;
}
