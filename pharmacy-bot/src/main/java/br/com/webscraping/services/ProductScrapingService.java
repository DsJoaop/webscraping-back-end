package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
import br.com.webscraping.config.PlaywrightConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ProductScrapingService {

    private static final int BATCH_SIZE = 100;
    private static final Logger logger = LoggerFactory.getLogger(ProductScrapingService.class);

    private final ScraperStrategyFactory scraperStrategyFactory;
    private final ProductService productService;
    private final PlaywrightConfig playwrightConfig;
    private final Browser browser;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void scrapeAndSaveProducts(PharmacyResponseDTO pharmacy, List<CategoryDTO> categories) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (CategoryDTO category : categories) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try (BrowserContext context = playwrightConfig.createBrowserContext(browser);
                     Page page = playwrightConfig.createPage(context)) {

                    logger.info("Iniciando scraping da categoria: {}", category.getName());
                    scrapeProducts(strategy, pharmacy, category, page);
                } catch (Exception e) {
                    logScrapingError("categoria: " + category.getName(), e);
                }
            }, executor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }

    private void scrapeProducts(ScraperStrategy strategy, PharmacyResponseDTO pharmacy, CategoryDTO category, Page page) {
        List<ProductDTO> accumulatedProducts = Collections.synchronizedList(new ArrayList<>());

        try {
            // Chama diretamente o método que já trata a paginação
            List<ProductDTO> products = strategy.scrapeProductsByCategory(pharmacy, category, page);
            accumulatedProducts.addAll(products);

            // Salva os produtos quando o batch estiver completo
            saveAccumulatedProducts(accumulatedProducts);

        } catch (Exception e) {
            logScrapingError("categoria: " + category.getName(), e);
        }
    }

    private void saveAccumulatedProducts(List<ProductDTO> accumulatedProducts) {
        if (!accumulatedProducts.isEmpty()) {
            productService.saveAll(new ArrayList<>(accumulatedProducts));
            accumulatedProducts.clear();
        }
    }

    private void logScrapingError(String context, Exception e) {
        logger.error("Erro ao realizar scraping de {}: {}", context, e.getMessage());
    }
}
