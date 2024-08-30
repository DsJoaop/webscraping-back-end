package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private static final int CATEGORY_SCRAPE_DELAY = 36000000;
    private static final int PRODUCT_SCRAPE_DELAY = 18000000;
    private static final int BATCH_SIZE = 100;

    private final ScraperStrategyFactory scraperStrategyFactory;
    private final PharmacyService pharmacyService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Async
    @Scheduled(fixedDelay = CATEGORY_SCRAPE_DELAY)
    @Order(1)
    public void scrapeCategories() {
        try {
            List<PharmacyDTO> pharmacies = pharmacyService.findAll();
            for (PharmacyDTO pharmacy : pharmacies) {
                scrapeAndSaveCategories(pharmacy);
            }
            System.out.println("Categorias atualizadas com sucesso!");
            scrapeProducts();
        } catch (Exception e) {
            logScrapingError("categorias", e);
        }
    }

    @Async
    @Scheduled(fixedDelay = PRODUCT_SCRAPE_DELAY)
    @Order(2)
    public void scrapeProducts() {
        try {
            List<PharmacyDTO> pharmacies = pharmacyService.findAll();
            for (PharmacyDTO pharmacy : pharmacies) {
                scrapeAndSaveProducts(pharmacy);
            }
            System.out.println("Produtos atualizados com sucesso!");
        } catch (Exception e) {
            logScrapingError("produtos", e);
        }
    }

    private void scrapeAndSaveCategories(PharmacyDTO pharmacy)  {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        try {
            List<CategoryDTO> categories = strategy.scrapeCategories();
            categories.forEach(categoryService::insert);

        } catch (Exception e) {
            logScrapingError("Error: ", e);
        }
    }

    private void scrapeAndSaveProducts(PharmacyDTO pharmacy) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());
        List<CategoryDTO> categories = pharmacyService.findAllCategoriesByPharmacy(pharmacy.getId());
        categories.forEach(category -> scrapeProductsInBatches(strategy, category));
    }

    private void scrapeProductsInBatches(ScraperStrategy strategy, CategoryDTO category) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<ProductDTO> accumulatedProducts = new ArrayList<>();

        try {
            int totalPages = strategy.getTotalPages(category);
            for (int page = 1; page <= totalPages; page++) {
                int finalPage = page;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    List<ProductDTO> products = strategy.scrapeProductsByCategoryAndPage(category, finalPage);
                    synchronized (accumulatedProducts) {
                        accumulatedProducts.addAll(products);
                        if (accumulatedProducts.size() >= BATCH_SIZE) {
                            saveAccumulatedProducts(accumulatedProducts);
                        }
                    }
                }, executor);
                futures.add(future);

                if (shouldSaveBatch(page, totalPages)) {
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                    saveAccumulatedProducts(accumulatedProducts);
                    futures.clear();
                }
            }

            // Salva qualquer produto remanescente após a última página
            if (!accumulatedProducts.isEmpty()) {
                saveAccumulatedProducts(accumulatedProducts);
            }
        } catch (Exception e) {
            logScrapingError("categoria: " + category.getName(), e);
        } finally {
            executor.shutdown();
        }
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

    private void logScrapingError(String context, Exception e) {
        System.err.println("Erro ao realizar scraping de " + context + ": " + e.getMessage());
    }
}
