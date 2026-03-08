package id.ac.ui.cs.advprog.eshop2.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductPageFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void pageHeading_createProductPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl);
        String pageHeading = driver.findElement(By.tagName("h3")).getText();
        assertEquals("Create New Product", pageHeading);
    }

    @Test
    void nameInputField_isPresent(ChromeDriver driver) {
        driver.get(baseUrl);
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        assertTrue(nameInput.isDisplayed());
    }

    @Test
    void quantityInputField_isPresent(ChromeDriver driver) {
        driver.get(baseUrl);
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        assertTrue(quantityInput.isDisplayed());
    }

    @Test
    void submitButton_isPresent(ChromeDriver driver) {
        driver.get(baseUrl);
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertTrue(submitButton.isDisplayed());
        assertEquals("Submit", submitButton.getText());
    }

    @Test
    void createProduct_redirectsToProductList(ChromeDriver driver) {
        driver.get(baseUrl);

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("10");
        submitButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/product/list"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));
    }
}