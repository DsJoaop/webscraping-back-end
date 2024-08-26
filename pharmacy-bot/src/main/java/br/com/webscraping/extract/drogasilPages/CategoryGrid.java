package br.com.webscraping.extract.drogasilPages;

import br.com.webscraping.dto.CategoryDTO;
import br.com.webscraping.enuns.raiaDrogasil.CategorySelectors;
import br.com.webscraping.extract.basePage.BasePage;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
            LOGGER.log(Level.SEVERE, "Error waiting for page load: " + e.getMessage(), e);
        }
    }

    public List<CategoryDTO> parseCategories(Page page) {
        List<CategoryDTO> categories = new ArrayList<>();
        try {
            open(CategorySelectors.LINK_CATEGORY.getSelector(), page);
            waitForPageLoad(page);

            List<ElementHandle> mainCategoryElements = page.querySelectorAll(CategorySelectors.MAIN_CATEGORY.getSelector());
            List<ElementHandle> subCategoryListElements = page.querySelectorAll(CategorySelectors.SUB_CATEGORY.getSelector());

            for (int i = 0; i < mainCategoryElements.size(); i++) {
                categories.add(parseMainCategory(mainCategoryElements.get(i), subCategoryListElements.get(i)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing categories: " + e.getMessage(), e);
        }
        return categories;
    }

    private CategoryDTO parseMainCategory(ElementHandle mainCategoryElement, ElementHandle subCategoryListElement) {
        try {
            String mainCategoryName = mainCategoryElement.innerText();
            String mainCategoryLink = mainCategoryElement.querySelector("a").getAttribute("href");
            List<CategoryDTO> subcategories = parseSubCategories(subCategoryListElement);
            return new CategoryDTO(mainCategoryName, mainCategoryLink, subcategories);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error parsing main category: " + e.getMessage(), e);
            return null;
        }
    }

    private List<CategoryDTO> parseSubCategories(ElementHandle subCategoryListElement) {
        List<CategoryDTO> subcategories = new ArrayList<>();
        try {
            List<ElementHandle> subCategoryElements = subCategoryListElement.querySelectorAll("li");

            for (ElementHandle subCategoryElement : subCategoryElements) {
                String subCategoryName = subCategoryElement.innerText();
                String subCategoryLink = subCategoryElement.querySelector("a").getAttribute("href");
                subcategories.add(new CategoryDTO(subCategoryName, subCategoryLink, new ArrayList<>()));
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error parsing subcategories: " + e.getMessage(), e);
        }
        return subcategories;
    }
}