package com.ebay.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;






public class EbayTest {

    private static final String URL = "https://www.ebay.com";
    private static final String searchBar_element_id = "gh-ac";
    private static final String searchButton_element_id = "gh-btn";
    private static final String addToCartButton_element_id = "atcBtn_btn_1";
    private static final String CartIcon_element_id = "gh-cart-n";
    private static final String firstBook_element_xpath = "(//div[@class='s-item__image-wrapper image-treatment']//img)[3]";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/cocoa/Downloads/chromedriver-mac-x64/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            // Open the eBay.com
            driver.get(URL);
            driver.manage().window().maximize();

            // search for "book"
            WebElement searchBar = driver.findElement(By.id(searchBar_element_id));
            searchBar.sendKeys("book");
            WebElement searchButton = driver.findElement(By.id(searchButton_element_id));
            searchButton.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement firstBook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(firstBook_element_xpath)));
            firstBook.click();

            // Switch to the new tab that was opened
            Set<String> windowHandles = driver.getWindowHandles();
            Iterator<String> iterator = windowHandles.iterator();
            String parentWindowHandle = iterator.next();

            // Switch to the new tab (the second tab)
            String childWindowHandle = iterator.next();
            driver.switchTo().window(childWindowHandle);

            //CLick to "Add to cart"
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(addToCartButton_element_id)));
            addToCartButton.click();
            WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(CartIcon_element_id)));
            String cartCount = cartIcon.getText();

            // Verify the cart count
            if (cartCount.equals("1")) {
                System.out.println("Test Passed: Item was successfully added to the cart.");
            } else {
                System.out.println("Test Failed: Cart count is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
