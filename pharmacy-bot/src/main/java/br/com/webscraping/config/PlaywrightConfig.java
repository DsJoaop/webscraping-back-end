package br.com.webscraping.config;

import com.microsoft.playwright.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

@Configuration
public class PlaywrightConfig {

    @Bean
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean
    public Browser browser(Playwright playwright) {
        BrowserType browserType = playwright.chromium();
        BrowserType.LaunchOptions launchOptions = createLaunchOptions();
        return browserType.launch(launchOptions);
    }

    @Bean
    public BrowserContext browserContext(Browser browser) {
        Browser.NewContextOptions contextOptions = createContextOptions();
        return browser.newContext(contextOptions);
    }

    @Bean
    public Page page(BrowserContext browserContext) {
        Page page = browserContext.newPage();
        page.setDefaultNavigationTimeout(120000);
        return page;
    }

    private BrowserType.LaunchOptions createLaunchOptions() {
        return new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(Arrays.asList(
                        "--no-sandbox",
                        "--disable-extensions",
                        "--disable-popup-blocking",
                        "--metrics-recording-only"
                ));
    }

    private Browser.NewContextOptions createContextOptions() {
        return new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .setViewportSize(1280, 720)
                .setExtraHTTPHeaders(Map.of(
                        "Accept-Language", "en-US,en;q=0.9",
                        "Accept-Encoding", "gzip, deflate, br"
                ))
                .setBypassCSP(true);
    }
}
