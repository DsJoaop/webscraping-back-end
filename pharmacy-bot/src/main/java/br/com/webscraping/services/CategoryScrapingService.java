package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;

import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
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

    public void scrapeAndSaveCategories(PharmacyResponseDTO pharmacy) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        try {
            List<CategoryDTO> categories = strategy.scrapeCategories(pharmacy.getId());
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