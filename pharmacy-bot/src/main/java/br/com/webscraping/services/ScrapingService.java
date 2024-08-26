package br.com.webscraping.services;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.PharmacyDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.scraper.factory.ScraperStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final ScraperStrategyFactory scraperStrategyFactory;
    private final PharmacyService pharmacyService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Scheduled(fixedDelay = 36000000)
    public void scrapingCategory() {
        try {
            List<PharmacyDTO> listParmacies = pharmacyService.findAll();
            for (PharmacyDTO pharmacyDTO : listParmacies) {
                ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacyDTO.getName());
                List<CategoryDTO> categories = strategy.scrapeCategories();
                for (CategoryDTO categoryDTO : categories) {
                    categoryService.insert(categoryDTO);
                }
            }
            System.out.println("Categorias atualizadas com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao realizar scraping de categorias: " + e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 18000000)
    public void scrapingProduct() {
        try {
            List<PharmacyDTO> listParmacies = pharmacyService.findAll();
            for (PharmacyDTO pharmacyDTO : listParmacies) {
                ScraperStrategy strategy = scraperStrategyFactory.getStrategy(pharmacyDTO.getName());
                List<ProductDTO> products = strategy.scrapeProducts();
                for (ProductDTO productDTO : products) {
                    productService.insert(productDTO);
                }
            }
            System.out.println("Produtos atualizados com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao realizar scraping de produtos: " + e.getMessage());
        }
    }
}
