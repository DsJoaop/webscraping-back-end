package br.com.webscraping.enuns.raiaDrogasil;

import lombok.Getter;

@Getter
public enum ProductSelectors {
    GRID_PRODUCTS("//div[contains(@class, 'gquykZ')]"),
    PRODUCT_ITEMS("div.product-item"),
    PAGINATION("//ul[@aria-label='pages']/li");

    private final String selector;

    ProductSelectors(String selector) {
        this.selector = selector;
    }

}

