package br.com.webscraping.extract.basePage;

import com.microsoft.playwright.Page;
import lombok.NoArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor(force = true)
public abstract class BasePage {
    private static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());

    protected void open(String url, Page page) {
        try {
            page.navigate(url);
            page.waitForURL(url);
            LOGGER.info("Navigated to " + url);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error navigating to " + url + ": " + e.getMessage(), e);
        }
    }

    public void close(Page page) {
        if (page != null) {
            try {
                page.context().close();
                LOGGER.info("Page closed successfully");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error closing page: " + e.getMessage(), e);
            }
        }
    }


    protected abstract void waitForPageLoad(Page page);
}
