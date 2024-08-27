package br.com.webscraping.extract.drogasilPages;


import br.com.webscraping.dto.ProductDTO;
import br.com.webscraping.enuns.raiaDrogasil.ProductSelectors;
import br.com.webscraping.extract.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProductsGrid extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(ProductsGrid.class.getName());
    private static final int MAX_RETRIES = 3;

    @Override
    protected void waitForPageLoad(Page page) {
        try {
            page.waitForSelector(ProductSelectors.GRID_PRODUCTS.getSelector());
            LOGGER.info("Page loaded successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error waiting for page load: " + e.getMessage(), e);
        }
    }

    public int getPagination(String baseUrl, Page page) {
        try {
            open(baseUrl, page);
            waitForPageLoad(page);

            Locator productItems = page.locator(ProductSelectors.PRODUCT_ITEMS.getSelector());
            if (productItems.count() == 0) {
                page.waitForSelector(ProductSelectors.PRODUCT_ITEMS.getSelector());
            }

            Locator paginationElements = page.locator(ProductSelectors.PAGINATION.getSelector());
            if (paginationElements.count() == 0) {
                return 1;
            }

            String lastPaginationText = paginationElements.nth(paginationElements.count() - 2).innerText();
            return Integer.parseInt(lastPaginationText);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while getting pagination for: " + baseUrl, e);
            return 1;
        }
    }

    public List<ProductDTO> getProducts(String baseUrl, Page page) {
        List<String> allProductHtml = new ArrayList<>();
        try {
            open(baseUrl, page);
            waitForPageLoad(page);
            int totalPages = getPagination(baseUrl, page);

            for (int i = 1; i <= totalPages; i++) {
                boolean success = false;
                for (int retryCount = 0; retryCount < MAX_RETRIES && !success; retryCount++) {
                    try {
                        open(baseUrl + (i > 1 ? "?p=" + i : ""), page);
                        waitForPageLoad(page);
                        allProductHtml.addAll(collectProductHtmlFromCurrentPage(page));
                        success = true;
                    } catch (TimeoutError e) {
                        LOGGER.log(Level.SEVERE, "Error processing page baseUrl: " + baseUrl + "?p=" + i, e);
                        if (retryCount < MAX_RETRIES - 1) {
                            LOGGER.info("Retrying to load page " + i + ", attempt " + (retryCount + 1));
                        } else {
                            LOGGER.severe("Failed to load page " + i + " after " + MAX_RETRIES + " attempts.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting products: " + e.getMessage(), e);
        }

        if (allProductHtml.isEmpty()) {
            LOGGER.info("No products found across all pages.");
        }

        return processProducts(allProductHtml);
    }

    private List<String> collectProductHtmlFromCurrentPage(Page page) {
        List<String> productHtmlList = new ArrayList<>();
        try {
            Locator productItems = page.locator(ProductSelectors.PRODUCT_ITEMS.getSelector());
            productItems.all().forEach(productItem -> productHtmlList.add(productItem.innerHTML()));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error collecting product HTML from current page: " + e.getMessage(), e);
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
                LOGGER.log(Level.WARNING, "Error processing product HTML: " + e.getMessage(), e);
            }
        }
        return products;
    }
}
