package br.com.webscraping.extract.drogasil_pages;

import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.enuns.raia_drogasil.ProductSelectors;
import br.com.webscraping.extract.base_page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProductsGrid extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(ProductsGrid.class.getName());

    @Override
    protected void waitForPageLoad(Page page) {
        try {
            page.waitForSelector(ProductSelectors.GRID_PRODUCTS.getSelector());
            LOGGER.info("Page loaded successfully");
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error waiting for page load: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage.get(), e);
        }
    }

    public int getPagination(String baseUrl, Page page) {
        try {
            open(baseUrl, page);
            Thread.sleep(5000);
            waitForPageLoad(page);

            Locator paginationElements = page.locator(ProductSelectors.PAGINATION.getSelector());
            if (paginationElements.count() == 0) {
                return 1;
            }

            String lastPaginationText = paginationElements.nth(paginationElements.count() - 2).innerText();
            return Integer.parseInt(lastPaginationText);
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Exception occurred while getting pagination for: " + baseUrl + ", error: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage.get(), e);
            return 1;
        }
    }

    public List<ProductDTO> getProductsByCategoryAndPage(String baseUrl, Page page, int pageNumber){
        List<String> productHtmlList = new ArrayList<>();
        String pageUrl = baseUrl + (pageNumber > 1 ? "?p=" + pageNumber : "");
        try {
            open(pageUrl, page);
            waitForPageLoad(page);
            productHtmlList.addAll(collectProductHtmlFromCurrentPage(page));
        } catch (TimeoutError e) {
            Supplier<String> errorMessage = () -> "Error processing page: " + pageUrl + ", error: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage.get(), e);
        }
        page.close();
        return processProducts(productHtmlList);
    }

    private List<String> collectProductHtmlFromCurrentPage(Page page) {
        List<String> productHtmlList = new ArrayList<>();
        try {
            Locator productItems = page.locator(ProductSelectors.PRODUCT_ITEMS.getSelector());
            productItems.all().forEach(productItem -> productHtmlList.add(productItem.innerHTML()));
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error collecting product HTML from current page: " + e.getMessage();
            LOGGER.log(Level.WARNING, errorMessage.get(), e);
        }
        return productHtmlList;
    }

    public List<ProductDTO> processProducts(List<String> productHtmlList) {
        List<ProductDTO> products = new ArrayList<>();
        for (String productHtml : productHtmlList) {
            try {
                ProductDTO product = ProductExtractor.extractProduct(productHtml);
                if (product != null) {
                    products.add(product);
                }
            } catch (Exception e) {
                Supplier<String> errorMessage = () -> "Error processing product HTML: " + e.getMessage();
                LOGGER.log(Level.WARNING, errorMessage.get(), e);
            }
        }
        return products;
    }
}