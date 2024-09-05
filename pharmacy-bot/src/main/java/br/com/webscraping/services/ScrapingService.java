package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private static final int CATEGORY_SCRAPE_DELAY = 36_000_000; // 10 horas
    private static final int PRODUCT_SCRAPE_DELAY = 18_000_000; // 5 horas
    private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);

    private final PharmacyScrapingService pharmacyScrapingService;
    private final CategoryScrapingService categoryScrapingService;
    private final ProductScrapingService productScrapingService;

    @Scheduled(fixedDelay = CATEGORY_SCRAPE_DELAY)
    @Order(1)
    public void scrapeCategories() {
        try {
            List<PharmacyResponseDTO> pharmacies = pharmacyScrapingService.findAll();
            pharmacies.forEach(this::scrapeAndSaveCategories);
            scrapeProducts();
        } catch (Exception e) {
            logScrapingError("categorias", e);
        }
    }

    @Scheduled(fixedDelay = PRODUCT_SCRAPE_DELAY)
    @Order(2)
    public void scrapeProducts() {
        try {
            List<PharmacyResponseDTO> pharmacies = pharmacyScrapingService.findAll();
            pharmacies.forEach(this::scrapeAndSaveProducts);
            logger.info("Scraping de produtos concluído!");
        } catch (Exception e) {
            logScrapingError("produtos", e);
        }
    }

    private void scrapeAndSaveCategories(PharmacyResponseDTO pharmacy) {
        categoryScrapingService.scrapeAndSaveCategories(pharmacy);
    }

    private void scrapeAndSaveProducts(PharmacyResponseDTO pharmacy) {
        List<CategoryDTO> categories = categoryScrapingService.findAllCategoriesByPharmacy(pharmacy.getId());
        productScrapingService.scrapeAndSaveProducts(pharmacy, categories);
        logger.info("Produtos da farmácia {} atualizados com sucesso!", pharmacy.getName());
    }

    private void logScrapingError(String context, Exception e) {
        logger.error("Erro ao realizar scraping de {}: {}", context, e.getMessage());
    }
}
