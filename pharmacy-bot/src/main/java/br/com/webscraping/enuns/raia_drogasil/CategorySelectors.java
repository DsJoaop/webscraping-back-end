package br.com.webscraping.enuns.raia_drogasil;

import lombok.Getter;

@Getter
public enum CategorySelectors {
    CATEGORY("//div[@class='sc-eda93bd6-1 gnnCtn'][.//ul[contains(@aria-label, 'Casa')]]"),
    MAIN_CATEGORY(CATEGORY.selector + "//h3"),
    SUB_CATEGORY(CATEGORY.selector + "//ul[preceding-sibling::h3]"),
    ACCEPT_COOKIES_BUTTON("#onetrust-accept-btn-handler"),
    LINK_CATEGORY("https://www.drogasil.com.br/categorias");

    private final String selector;

    CategorySelectors(String selector) {
        this.selector = selector;
    }

}
