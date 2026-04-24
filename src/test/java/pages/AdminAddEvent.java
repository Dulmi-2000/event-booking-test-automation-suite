package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class AdminAddEvent {

    WebDriver driver;
    WebDriverWait wait;

    public AdminAddEvent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By titleField = By.id("title");
    By descriptionField = By.id("description");
    By categoryDropdown = By.id("category");
    By imageUrlField = By.id("imageUrl");
    By featuredCheckbox = By.xpath(("//button[@role='combobox']"));
    
    By venueField = By.id("venue");
    By locationField = By.id("location");
    By dateField = By.id("date");
    By timeField = By.id("time");
    By priceField = By.id("price");
    By totalTicketsField = By.id("totalTickets");
    By createEventButton = By.xpath("//button[contains(text(),'Create Event')]");
    By successMessage = By.xpath("//*[contains(text(),'Event created')]");

    // Open page
    public void goToEventsPage() {
        driver.get("http://localhost:3000/admin/events/new");
    }

    // Fill form
    public void fillEventForm(String title, String description, String category,
                              String imageUrl, boolean isFeatured,
                              String venue, String location,
                              String date, String time,
                              String price, String totalTickets) {

        WebElement titleEl = wait.until(ExpectedConditions.visibilityOfElementLocated(titleField));
        titleEl.clear();
        titleEl.sendKeys(title);

        driver.findElement(descriptionField).sendKeys(description);

        new Select(driver.findElement(categoryDropdown))
                .selectByVisibleText(category);

        driver.findElement(imageUrlField).sendKeys(imageUrl);

        WebElement checkbox = driver.findElement(featuredCheckbox);
        if (isFeatured != checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(venueField).sendKeys(venue);
        driver.findElement(locationField).sendKeys(location);
        driver.findElement(dateField).sendKeys(date);
        driver.findElement(timeField).sendKeys(time);
        driver.findElement(priceField).sendKeys(price);
        driver.findElement(totalTicketsField).sendKeys(totalTickets);
    }

    // Submit form
    public void submitEventForm() {
        driver.findElement(createEventButton).click();
    }

    // Verify success
    public boolean isEventCreatedSuccessfully() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/admin/events"),
                    ExpectedConditions.presenceOfElementLocated(successMessage)
            ));
            return driver.getPageSource().contains("QA Test Event");
        } catch (Exception e) {
            return false;
        }
    }
}