package br.com.webscraping.enuns.raiaDrogasil;

import lombok.Getter;

@Getter
public enum CategorySelectors {
    CATEGORY("//div[contains(@class, 'kEvwPR')][.//a[contains(@title, 'DIU')]]"),
    MAIN_CATEGORY(CATEGORY.selector + "//h3"),
    SUB_CATEGORY(CATEGORY.selector + "//ul[preceding-sibling::h3]"),
    ACCEPT_COOKIES_BUTTON("#onetrust-accept-btn-handler"),
    LINK_CATEGORY("https://www.drogasil.com.br/categorias");

    private final String selector;

    CategorySelectors(String selector) {
        this.selector = selector;
    }

}
