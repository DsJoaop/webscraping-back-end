package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.PharmacyResponseDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
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

    public void scrapeAndSaveProducts(PharmacyResponseDTO pharmacy, List<CategoryDTO> categories) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        categories.forEach(category -> scrapeProductsInBatches(strategy, pharmacy, category));
    }

    private void scrapeProductsInBatches(ScraperStrategy strategy, PharmacyResponseDTO pharmacy, CategoryDTO category) {
        ExecutorService executor = createExecutorService();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<ProductDTO> accumulatedProducts = Collections.synchronizedList(new ArrayList<>());

        try {
            int totalPages = strategy.getTotalPages(category);
            for (int page = 1; page <= totalPages; page++) {
                futures.add(scrapePageAsync(strategy, category, pharmacy, page, accumulatedProducts, executor));

                if (shouldSaveBatch(page, totalPages)) {
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                    saveAccumulatedProducts(accumulatedProducts);
                    futures.clear();
                }
            }

            saveAccumulatedProducts(accumulatedProducts);
        } catch (Exception e) {
            logScrapingError("categoria: " + category.getName(), e);
        } finally {
            shutdownExecutor(executor);
        }
    }

    private ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(2);
    }

    private CompletableFuture<Void> scrapePageAsync(ScraperStrategy strategy, CategoryDTO category,
                                                    PharmacyResponseDTO pharmacy, int page,
                                                    List<ProductDTO> accumulatedProducts,
                                                    ExecutorService executor) {
        return CompletableFuture.runAsync(() -> {
            List<ProductDTO> products = strategy.scrapeProductsByCategoryAndPage(pharmacy, category, page);
            accumulatedProducts.addAll(products);
            if (accumulatedProducts.size() >= BATCH_SIZE) {
                saveAccumulatedProducts(accumulatedProducts);
            }
        }, executor);
    }

    private boolean shouldSaveBatch(int currentPage, int totalPages) {
        return currentPage % 10 == 0 || currentPage == totalPages;
    }

    private void saveAccumulatedProducts(List<ProductDTO> accumulatedProducts) {
        if (!accumulatedProducts.isEmpty()) {
            productService.saveAll(new ArrayList<>(accumulatedProducts));
            accumulatedProducts.clear();
        }
    }

    private void shutdownExecutor(ExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
        }
    }

    private void logScrapingError(String context, Exception e) {
        logger.error("Erro ao realizar scraping de {}: {}", context, e.getMessage());
    }
}
