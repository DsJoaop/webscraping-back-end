package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private static final int CATEGORY_SCRAPE_DELAY = 36_000_000; // 10 horas
    private static final int PRODUCT_SCRAPE_DELAY = 18_000_000; // 5 horas
    private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);

    private final PharmacyService pharmacyService;
    private final CategoryScrapingService categoryScrapingService;
    private final ProductScrapingService productScrapingService;

    @Async
    @Scheduled(fixedDelay = CATEGORY_SCRAPE_DELAY)
    @Order(1)
    public void scrapeCategories() {
        processScraping("categorias",
                this::scrapeAndSaveCategories,
                this::scrapeProducts);
    }

    @Scheduled(fixedDelay = PRODUCT_SCRAPE_DELAY)
    @Order(2)
    public void scrapeProducts() {
        processScraping("produtos",
                this::scrapeAndSaveProducts,
                () -> logger.info("Scraping de produtos concluído!"));
    }

    private void scrapeAndSaveCategories(PharmacyDTO pharmacy) {
        categoryScrapingService.scrapeAndSaveCategories(pharmacy);
    }

    private void scrapeAndSaveProducts(PharmacyDTO pharmacy) {
        List<CategoryDTO> categories = pharmacyService.findAllCategoriesByPharmacy(pharmacy.getId());
        productScrapingService.scrapeAndSaveProducts(pharmacy, categories);
        logger.info("Produtos da farmácia {} atualizados com sucesso!", pharmacy.getName());
    }

    private void processScraping(String context, ScrapingAction action, Runnable onSuccess) {
        try {
            List<PharmacyDTO> pharmacies = pharmacyService.findAll();
            pharmacies.forEach(action::perform);
            onSuccess.run();
        } catch (Exception e) {
            logScrapingError(context, e);
        }
    }

    private void logScrapingError(String context, Exception e) {
        logger.error("Erro ao realizar scraping de {}: {}", context, e.getMessage());
    }

    @FunctionalInterface
    private interface ScrapingAction {
        void perform(PharmacyDTO pharmacy);
    }
}
