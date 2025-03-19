package org.example.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    public static void initializeDriver() {
        String browserType = ConfigReader.getProperty("browser.type");
        String browserMode = ConfigReader.getProperty("browser.mode");
        boolean clearCache = Boolean.parseBoolean(ConfigReader.getProperty("browser.clear.cache"));

        switch (browserType.toLowerCase()) {
            case "chrome":
                driver.set(createChromeDriver(browserMode, clearCache, false));
                break;
            case "firefox":
                driver.set(createFirefoxDriver(browserMode, clearCache));
                break;
            case "headless":
                driver.set(createChromeDriver(browserMode, clearCache, true));
                break;
            default:
                throw new RuntimeException("Unsupported browser type: " + browserType);
        }

        // Configure common settings
        driver.get().manage().window().maximize();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    private static WebDriver createChromeDriver(String mode, boolean clearCache, boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Configure Chrome options
        if (headless) {
            options.addArguments("--headless=new");
        }
        if ("incognito".equalsIgnoreCase(mode)) {
            options.addArguments("--incognito");
        }
        if (clearCache) {
            options.addArguments("--disable-cache");
            options.addArguments("--disable-application-cache");
            options.addArguments("--disable-offline-load-stale-cache");
            options.addArguments("--disk-cache-size=0");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        // Additional recommended options for stability
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(String mode, boolean clearCache) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();

        // Configure Firefox options
        if ("incognito".equalsIgnoreCase(mode)) {
            options.addArguments("-private");
        }
        if (clearCache) {
            profile.setPreference("browser.cache.disk.enable", false);
            profile.setPreference("browser.cache.memory.enable", false);
            profile.setPreference("browser.cache.offline.enable", false);
            profile.setPreference("network.cookie.cookieBehavior", 2);
        }

        options.setProfile(profile);
        return new FirefoxDriver(options);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public static void clearCookiesAndCache() {
        if (driver.get() != null) {
            driver.get().manage().deleteAllCookies();
        }
    }
} 