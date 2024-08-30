package br.com.webscraping.scraper.strategy;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.extract.drogasilPages.ProductsGrid;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.extract.drogasilPages.CategoryGrid;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DrogasilScraperStrategy implements ScraperStrategy {
    private final Page page;
    private final CategoryGrid categoryGrid;
    private final ProductsGrid productsGrid;

    @Override
    public List<CategoryDTO> scrapeCategories() throws Exception {
        return categoryGrid.parseCategories(page);
    }

    @Override
    public List<ProductDTO> scrapeProductsByCategoryAndPage(CategoryDTO category, int totalPages){
        return productsGrid.getProductsByCategoryAndPage(category.getUrl(), page, totalPages);
    }

    public int getTotalPages(CategoryDTO categoryDTO) throws Exception {
        return productsGrid.getPagination(categoryDTO.getUrl(), page);
    }
}
