package br.com.webscraping.extract.drogasil_pages;


import br.com.webscraping.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductExtractor {

    private static final Logger LOGGER = Logger.getLogger(ProductExtractor.class.getName());

    public static ProductDTO extractProduct(String productHtml) {
        try {
            Document doc = Jsoup.parse(productHtml);
            Element productCard = doc.body();

            String title = extractTitle(productCard);
            String link = extractLink(productCard);
            String brand = extractBrand(productCard);
            String quantity = extractQuantity(productCard);
            double rating = extractRating(productCard);
            int reviewsCount = extractReviewsCount(productCard);

            // Flexível extração de preços
            double priceFrom = extractPrice(productCard, "div[data-qa=price_from_item]", "div.price-from .price-number", "div.price.price-from span.price-number");
            double priceFinal = extractPrice(productCard, "div[data-qa=price_final_item]", "div.price-final .price-number", "div.price.price-final span.price-number");
            double discount = extractDiscount(productCard);

            // Se não houver preço final, retornar null
            if (priceFinal == 0.0) {
                return null;
            }

            return new ProductDTO(title, link, brand, quantity, rating, reviewsCount, priceFrom, priceFinal, discount);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing product card: {0}", e.getMessage());
            return null;
        }
    }

    private static String extractTitle(Element productCard) {
        try {
            return Objects.requireNonNull(productCard.selectFirst("a")).attr("title");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting title: {0}", e.getMessage());
            return "";
        }
    }

    private static String extractLink(Element productCard) {
        try {
            return Objects.requireNonNull(productCard.selectFirst("a")).attr("href");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting link: {0}", e.getMessage());
            return "";
        }
    }

    private static String extractBrand(Element productCard) {
        try {
            Element brandElement = productCard.selectFirst(".product-brand");
            return brandElement != null ? brandElement.text() : "";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting brand: {0}", e.getMessage());
            return "";
        }
    }

    private static String extractQuantity(Element productCard) {
        try {
            Element quantityElement = productCard.selectFirst(".additional-info");
            return quantityElement != null ? quantityElement.text() : "";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting quantity: {0}", e.getMessage());
            return "";
        }
    }

    private static double extractRating(Element productCard) {
        try {
            Element ratingElement = productCard.selectFirst("div.v_ratings__stars__enabled");
            if (ratingElement != null) {
                String styleAttribute = ratingElement.attr("style");
                String widthValue = styleAttribute.replaceAll("[^0-9.]", "");
                double width = Double.parseDouble(widthValue);
                return (width / 80.0) * 5.0;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting rating: {0}", e.getMessage());
        }
        return 0.0;
    }

    private static int extractReviewsCount(Element productCard) {
        try {
            Element reviewsCountElement = productCard.selectFirst("span.v_ratings__stars__reviewsCount");
            if (reviewsCountElement != null) {
                String reviewsText = reviewsCountElement.text();
                String reviewsCount = reviewsText.replaceAll("[^0-9]", "");
                return Integer.parseInt(reviewsCount);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting reviews count: {0}", e.getMessage());
        }
        return 0;
    }

    private static double extractPrice(Element productCard, String... cssSelectors) {
        for (String selector : cssSelectors) {
            try {
                Element priceElement = productCard.selectFirst(selector);
                if (priceElement != null) {
                    String priceText = priceElement.text().replace("R$", "").replace(",", ".");
                    return Double.parseDouble(priceText);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error extracting price with selector {0}: {1}", new Object[]{selector, e.getMessage()});
            }
        }
        return 0.0;
    }

    private static double extractDiscount(Element productCard) {
        try {
            Element discountElement = productCard.selectFirst("div.percent-tag");
            if (discountElement != null) {
                return Double.parseDouble(discountElement.text().replace("%", "")) / 100;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error extracting discount: {0}", e.getMessage());
        }
        return 0.0;
    }
}
