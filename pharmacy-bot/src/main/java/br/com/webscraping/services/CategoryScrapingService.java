package br.com.webscraping.services;

import br.com.webscraping.config.PlaywrightConfig;
import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryScrapingService.class);
    private final ScraperStrategyFactory scraperStrategyFactory;
    private final CategoryService categoryService;
    private final PlaywrightConfig playwrightConfig;
    private final Browser browser;

    public void scrapeAndSaveCategories(PharmacyResponseDTO pharmacy) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        try (BrowserContext context = playwrightConfig.createBrowserContext(browser); Page page = playwrightConfig.createPage(context) ){
            List<CategoryDTO> categories = strategy.scrapeCategories(pharmacy.getId(), page);
            for (CategoryDTO category : categories) {
                category.getPharmacies().add(pharmacy);
                categoryService.insert(category);
            }
        } catch (Exception e) {
            logger.error("Erro ao realizar scraping das categorias da farmácia {}: {}", pharmacy.getName(), e.getMessage());
        }
    }

    public List<CategoryDTO> findAllCategoriesByPharmacy(Long id) {
        return categoryService.findAllByPharmacy(id);
    }
}
