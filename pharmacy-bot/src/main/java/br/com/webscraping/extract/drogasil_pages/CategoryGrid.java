package br.com.webscraping.extract.drogasil_pages;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.enuns.raia_drogasil.CategorySelectors;
import br.com.webscraping.extract.base_page.BasePage;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CategoryGrid extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(CategoryGrid.class.getName());

    @Override
    protected void waitForPageLoad(Page page) {
        try {
            page.waitForSelector(CategorySelectors.CATEGORY.getSelector());
            LOGGER.info("Page loaded successfully");
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error waiting for page load: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage.get(), e);
        }
    }

    public List<CategoryDTO> parseCategories(Page page, Long idPharmacy) {
        List<CategoryDTO> categories = new ArrayList<>();
        try {
            open(CategorySelectors.LINK_CATEGORY.getSelector(), page);
            waitForPageLoad(page);

            List<ElementHandle> mainCategoryElements = page.querySelectorAll(CategorySelectors.MAIN_CATEGORY.getSelector());
            List<ElementHandle> subCategoryListElements = page.querySelectorAll(CategorySelectors.SUB_CATEGORY.getSelector());

            for (int i = 0; i < mainCategoryElements.size(); i++) {
                categories.add(parseMainCategory(mainCategoryElements.get(i), subCategoryListElements.get(i), idPharmacy));
            }
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error parsing categories: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage.get(), e);
        }
        return categories;
    }

    private CategoryDTO parseMainCategory(ElementHandle mainCategoryElement, ElementHandle subCategoryListElement, Long idPharmacy) {
        try {
            String mainCategoryName = mainCategoryElement.innerText();
            String mainCategoryLink = mainCategoryElement.querySelector("a").getAttribute("href");
            List<CategoryDTO> subcategories = parseSubCategories(subCategoryListElement, idPharmacy);
            return new CategoryDTO(mainCategoryName, mainCategoryLink, subcategories, idPharmacy);
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error parsing main category: " + e.getMessage();
            LOGGER.log(Level.WARNING, errorMessage.get(), e);
            return null;
        }
    }

    private List<CategoryDTO> parseSubCategories(ElementHandle subCategoryListElement, Long idPharmacy) {
        List<CategoryDTO> subcategories = new ArrayList<>();
        try {
            List<ElementHandle> subCategoryElements = subCategoryListElement.querySelectorAll("li");

            for (ElementHandle subCategoryElement : subCategoryElements) {
                String subCategoryName = subCategoryElement.innerText();
                String subCategoryLink = subCategoryElement.querySelector("a").getAttribute("href");
                subcategories.add(new CategoryDTO(subCategoryName, subCategoryLink, new ArrayList<>(), idPharmacy));
            }
        } catch (Exception e) {
            Supplier<String> errorMessage = () -> "Error parsing subcategories: " + e.getMessage();
            LOGGER.log(Level.WARNING, errorMessage.get(), e);
        }
        return subcategories;
    }
}