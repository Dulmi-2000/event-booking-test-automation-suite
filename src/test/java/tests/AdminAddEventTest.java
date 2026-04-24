package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminAddEventTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:3000");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loginAsAdmin() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Login')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("admin@eventtickets.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("admin123");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
        Thread.sleep(1000);
    }

    @Test
    public void testAdminCanAccessNewEventPage() throws InterruptedException {
        loginAsAdmin();
        driver.get("http://localhost:3000/admin/events/new");
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title"))).isDisplayed(),
                "Admin should be able to access new event form");
    }

    @Test
    public void testCreateEvent() throws InterruptedException {
        loginAsAdmin();
        driver.get("http://localhost:3000/admin/events/new");

        String title = "QA Test Event " + System.currentTimeMillis();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")))
                .sendKeys(title);

        driver.findElement(By.id("description")).sendKeys("This is a test event");
        driver.findElement(By.id("imageUrl")).sendKeys("https://example.com/image.jpg");

     // Open dropdown
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='category']/following::button[@role='combobox'][1]")
        )).click();

        // Option locator (keep flexible for Radix)
        By musicOption = By.xpath(
                "//div[@role='option' and (normalize-space()='Music' or @data-value='Music')]"
        );

        // WAIT for visibility (NOT just presence)
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(musicOption));

        // Ensure it is in viewport
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", option);

        // Small stability buffer for animation (IMPORTANT for Radix UI)
        Thread.sleep(300);

        // Try normal click first, fallback to JS click
        try {
            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", option);
        }

        driver.findElement(By.id("venue")).sendKeys("Test Venue");
        driver.findElement(By.id("location")).sendKeys("Colombo");

        // React-controlled HTML5 date/time fields: use native setter + input/change events.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "const setNativeValue = (el, value) => {" +
                "  const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "  setter.call(el, value);" +
                "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "};" +
                "setNativeValue(document.getElementById('date'), '2026-05-01');" +
                "setNativeValue(document.getElementById('time'), '18:00');");

        wait.until(ExpectedConditions.attributeToBe(By.id("date"), "value", "2026-05-01"));
        wait.until(ExpectedConditions.attributeToBe(By.id("time"), "value", "18:00"));
        driver.findElement(By.id("price")).sendKeys("1000");
        driver.findElement(By.id("tickets")).sendKeys("100");

        driver.findElement(By.xpath("//button[contains(text(),'Create Event')]")).click();

        // /admin/events/new also contains "/admin/events", so require exact list route.
        wait.until(ExpectedConditions.urlMatches(".*/admin/events(\\?.*)?$"));
        Assert.assertFalse(
                driver.getCurrentUrl().contains("/admin/events/new"),
                "Create did not complete. Still on form page: " + driver.getCurrentUrl());

        // Verify on the list page, not by raw page source from the form.
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[contains(normalize-space(),\"" + title + "\")]")));
        Assert.assertTrue(
                driver.findElements(By.xpath("//h3[contains(normalize-space(),\"" + title + "\")]")).size() > 0,
                "Created event title was not found in admin events listing");
    }
}
