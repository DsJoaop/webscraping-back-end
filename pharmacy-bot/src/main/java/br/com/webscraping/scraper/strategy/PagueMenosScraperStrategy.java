package br.com.webscraping.scraper.strategy;
import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import com.microsoft.playwright.Page;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PagueMenosScraperStrategy implements ScraperStrategy {
    private final Page page;


    @Override
    public List<CategoryDTO> scrapeCategories(Long idPharmacy) throws Exception {
        return List.of();
    }

    @Override
    public List<ProductDTO> scrapeProductsByCategoryAndPage(PharmacyResponseDTO pharmacy, CategoryDTO category, int totalPages) {
        return List.of();
    }

    @Override
    public int getTotalPages(CategoryDTO categoryDTO) throws Exception {
        return 0;
    }
}
