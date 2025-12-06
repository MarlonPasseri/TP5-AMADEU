package com.example.tp5integrado.web;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste Selenium simples para ser executado pós-deploy (tag selenium).
 * Requer ChromeDriver disponível no PATH no ambiente de CI/CD.
 */
@Tag("selenium")
class SeleniumSmokeTest {

    @Test
    void homepageDeveConterTituloSistemaIntegrado() {
        String baseUrl = System.getProperty("app.baseUrl", "http://localhost:8080");
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(baseUrl + "/");
            WebElement header = driver.findElement(By.tagName("h1"));
            assertTrue(header.getText().contains("Sistema Integrado"));
        } finally {
            driver.quit();
        }
    }
}
