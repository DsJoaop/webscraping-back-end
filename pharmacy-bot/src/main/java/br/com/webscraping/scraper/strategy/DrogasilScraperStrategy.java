package br.com.webscraping.scraper.strategy;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.extract.drogasilPages.ProductsGrid;
import br.com.webscraping.scraper.factory.ScraperStrategy;
import br.com.webscraping.extract.drogasilPages.CategoryGrid;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
@RequiredArgsConstructor
public class DrogasilScraperStrategy implements ScraperStrategy {
    private final Page page;
    private final CategoryGrid categoryGrid;
    private final ProductsGrid productsGrid;

    @Override
    public List<CategoryDTO> scrapeCategories() throws Exception{
        return categoryGrid.parseCategories(page);
    }

    @Override
    public List<ProductDTO> scrapeProductsByCategory(CategoryDTO category) throws Exception {
        List<ProductDTO> productsMain = new ArrayList<>();
        if (!category.getSubcategories().isEmpty()) {
            for (CategoryDTO subcategory : category.getSubcategories()) {
                System.out.println("Processing subcategory: " + subcategory.getName());
                List<ProductDTO> productsSub = productsGrid.getProducts(subcategory.getUrl(), page);
                subcategory.setProducts(productsSub);
                productsMain.addAll(productsSub);
                System.out.println("Finished processing subcategory: " + subcategory.getName() + ", products found: " + productsSub.size());
            }
        }
        return productsMain;
    }
}
