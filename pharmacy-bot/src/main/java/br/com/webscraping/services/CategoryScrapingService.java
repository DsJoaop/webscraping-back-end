package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryScrapingDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
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

    public void scrapeAndSaveCategories(PharmacyDTO pharmacy) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        try {
            List<CategoryScrapingDTO> categories = strategy.scrapeCategories();
            categories.forEach(categoryService::insert);
        } catch (Exception e) {
            logger.error("Erro ao realizar scraping das categorias da farmácia {}: {}", pharmacy.getName(), e.getMessage());
        }
    }
}
