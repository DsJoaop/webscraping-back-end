package br.com.webscraping.scraper.strategy;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.extract.drogasilPages.CategoryGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
@RequiredArgsConstructor
public class DrogasilScraperStrategy implements ScraperStrategy {

    private final CategoryGrid categoryGrid;

    @Override
    public List<CategoryDTO> scrapeCategories() {
        return categoryGrid.parseCategories(null);
    }

    @Override
    public List<ProductDTO> scrapeProducts() {
        return null;
    }
}
