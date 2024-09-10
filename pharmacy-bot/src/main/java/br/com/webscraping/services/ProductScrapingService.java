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
    private final PlaywrightConfig playwrightConfig; // Injeta a configuração de Playwright
    private final Browser browser; // Browser compartilhado entre threads

    // Limita a execução a no máximo 2 threads ao mesmo tempo
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void scrapeAndSaveProducts(PharmacyResponseDTO pharmacy, List<CategoryDTO> categories) {
        ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacy.getName());

        // Processa cada categoria em uma nova thread com sua própria instância do Playwright
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (CategoryDTO category : categories) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                BrowserContext context = null;
                Page page = null;
                try {
                    // Cria um novo contexto e página para cada categoria
                    context = playwrightConfig.createBrowserContext(browser);
                    page = playwrightConfig.createPage(context);

                    logger.info("Iniciando scraping da categoria: {}", category.getName());
                    scrapeProductsInBatches(strategy, pharmacy, category, page);
                } catch (Exception e) {
                    logScrapingError("categoria: " + category.getName(), e);
                } finally {
                    if (page != null) {
                        page.close();
                    }
                    if (context != null) {
                        context.close();
                    }
                }
            }, executor);

            futures.add(future);
        }

        // Espera até que todas as categorias sejam processadas
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }

    private void scrapeProductsInBatches(ScraperStrategy strategy, PharmacyResponseDTO pharmacy, CategoryDTO category, Page page) {
        List<ProductDTO> accumulatedProducts = Collections.synchronizedList(new ArrayList<>());
        List<CompletableFuture<Void>> pageFutures = new ArrayList<>();

        try {
            int totalPages = strategy.getTotalPages(category);
            for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
                // Processa cada página de produtos em paralelo dentro da mesma categoria
                CompletableFuture<Void> pageFuture = CompletableFuture.runAsync(() -> {
                    List<ProductDTO> products = strategy.scrapeProductsByCategoryAndPage(pharmacy, category, currentPage);
                    synchronized (accumulatedProducts) {
                        accumulatedProducts.addAll(products);
                    }
                    if (accumulatedProducts.size() >= BATCH_SIZE) {
                        saveAccumulatedProducts(accumulatedProducts);
                    }
                });

                pageFutures.add(pageFuture);

                // Salva em lote após processar várias páginas
                if (shouldSaveBatch(currentPage, totalPages)) {
                    CompletableFuture.allOf(pageFutures.toArray(new CompletableFuture[0])).join();
                    saveAccumulatedProducts(accumulatedProducts);
                    pageFutures.clear();  // Limpa as tarefas concluídas
                }
            }

            // Salva os produtos remanescentes no final
            saveAccumulatedProducts(accumulatedProducts);
        } catch (Exception e) {
            logScrapingError("categoria: " + category.getName(), e);
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
        logger.error("Erro ao realizar scraping de {}: {}", context, e.getMessage());
    }
}
