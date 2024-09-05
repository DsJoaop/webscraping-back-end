package br.com.webscraping.scraper.strategy;

import br.com.webscraping.dto.CategoryDTO;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.extract.drogasil_pages.ProductsGrid;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.extract.drogasil_pages.CategoryGrid;
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
    public List<CategoryDTO> scrapeCategories(Long idPharmacy) {
        return categoryGrid.parseCategories(page, idPharmacy);
    }

    @Override
    public List<ProductDTO> scrapeProductsByCategoryAndPage(PharmacyResponseDTO pharmacy, CategoryDTO category, int totalPages) {
        return productsGrid.getProductsByCategoryAndPage(category.getUrl(), this.page, totalPages);
    }

    @Override
    public int getTotalPages(CategoryDTO category) {
        return productsGrid.getPagination(category.getUrl(), this.page);
    }
}
