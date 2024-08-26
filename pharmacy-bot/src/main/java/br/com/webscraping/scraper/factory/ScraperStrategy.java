package br.com.webscraping.scraper.factory;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<CategoryDTO> scrapeCategories();
    List<ProductDTO> scrapeProducts();
}
