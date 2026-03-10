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
class EditProductPageFunctionalTest {

    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;
    private String productListUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
        productListUrl = String.format("%s/product/list", baseUrl);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        String pageTitle = driver.getTitle();
        assertEquals("Edit Product", pageTitle);
    }

    @Test
    void pageHeading_editProductPage_isCorrect(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        String pageHeading = driver.findElement(By.tagName("h3")).getText();
        assertEquals("Edit Product", pageHeading);
    }

    @Test
    void nameInputField_isPresent(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        assertTrue(nameInput.isDisplayed());
    }

    @Test
    void quantityInputField_isPresent(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        assertTrue(quantityInput.isDisplayed());
    }

    @Test
    void updateButton_isPresent(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        WebElement updateButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertTrue(updateButton.isDisplayed());
        assertEquals("Update", updateButton.getText());
    }

    @Test
    void cancelButton_isPresent(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Get the edit link from product list and click it
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Verify
        WebElement cancelButton = driver.findElement(By.linkText("Cancel"));
        assertTrue(cancelButton.isDisplayed());
    }

    @Test
    void editProduct_redirectsToProductList(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Original Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Navigate to edit page
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Edit the product
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement updateButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.clear();
        nameInput.sendKeys("Updated Product");
        quantityInput.clear();
        quantityInput.sendKeys("200");
        updateButton.click();

        // Verify - should redirect to product list page
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/product/list"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));
    }

    @Test
    void cancelButton_redirectsToProductList(ChromeDriver driver) throws Exception {
        // Setup: Create a product first
        driver.get(String.format("%s/product/create", baseUrl));
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Navigate to edit page
        driver.get(productListUrl);
        WebElement editLink = driver.findElement(By.linkText("Edit"));
        editLink.click();

        // Click cancel button
        WebElement cancelButton = driver.findElement(By.linkText("Cancel"));
        cancelButton.click();

        // Verify - should redirect to product list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));
    }
}