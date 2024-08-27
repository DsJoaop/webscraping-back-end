package br.com.webscraping.scraper.factory;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScraperStrategyFactory {

    private final Map<String, ScraperStrategy> strategies;

    public ScraperStrategyFactory(Map<String, ScraperStrategy> strategies) {
        this.strategies = strategies;
    }

    public ScraperStrategy getStrategy(String pharmacyName) {
        return strategies.get(pharmacyName.toLowerCase() + "ScraperStrategy");
    }
}
