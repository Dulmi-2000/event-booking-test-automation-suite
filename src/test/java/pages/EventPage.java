package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

public class EventPage {

    WebDriver driver;
    WebDriverWait wait;

    public EventPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators — Event listing page
    By eventCards = By.xpath("//a[contains(@href,'/events/')]");
    By getTicketsBtn = By.xpath("//a[contains(text(),'Get Tickets')] | //button[contains(text(),'Get Tickets')]");
    By featuredBadge = By.xpath("//*[contains(text(),'Featured')]");
    By lowStockBadge = By.xpath("//*[contains(text(),'Only') and contains(text(),'left')]");
    By emptyState = By.xpath("//*[contains(text(),'No Events Found')]");
    By eventsLabel = By.xpath("//*[contains(text(),'event') and contains(text(),'found')]");

    // Locators — Event detail page
    By quantityPlus = By.xpath("(//button[.//*[local-name()='svg']])[last()]");
    By quantityMinus = By.xpath("(//button[.//*[local-name()='svg']])[1]");
    By quantityValue = By.xpath("//span[contains(@class,'font-semibold')]");
    By buyTicketsBtn = By.xpath("//button[contains(text(),'Buy Tickets')]");
    By soldOutMsg = By.xpath("//*[contains(text(),'Sold Out')]");
    By totalPrice = By.xpath("//*[contains(text(),'Total')]/following-sibling::*");

    /** Navigate to events page */
    public void goToEventsPage() {
        driver.get("http://localhost:3000/events");
        try {
            Thread.sleep(1500);
        } catch (Exception ignored) {
        }
    }

    /** Navigate to events page filtered by category */
    public void goToEventsByCategory(String category) {
        driver.get("http://localhost:3000/events?category=" + category);
        try {
            Thread.sleep(1500);
        } catch (Exception ignored) {
        }
    }

    /** Count how many event cards are on the page */
    public int getEventCount() {
        try {
            List<WebElement> cards = driver.findElements(eventCards);
            return cards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /** Click the first Get Tickets button */
    public void clickFirstGetTickets() {
        wait.until(ExpectedConditions.elementToBeClickable(getTicketsBtn)).click();
    }

    /** Click Get Tickets at a specific index */
    public void clickGetTicketsAt(int index) {
        List<WebElement> buttons = driver.findElements(getTicketsBtn);
        buttons.get(index).click();
    }

    /** Get the events found label text e.g. "5 events found" */
    public String getEventsFoundText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(eventsLabel)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Check if empty state is shown */
    public boolean isEmptyStateDisplayed() {
        try {
            return driver.findElement(emptyState).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if featured badge is visible */
    public boolean isFeaturedBadgeDisplayed() {
        try {
            return driver.findElement(featuredBadge).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if low stock badge is visible */
    public boolean isLowStockDisplayed() {
        try {
            return driver.findElement(lowStockBadge).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if sold out message is shown on detail page */
    public boolean isSoldOut() {
        try {
            return driver.findElement(soldOutMsg).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get current quantity value on detail page */
    public int getCurrentQuantity() {
        try {
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityValue)).getText().trim();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return -1;
        }
    }

    /** Increase quantity by clicking + button */
    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(quantityPlus)).click();
        }
    }

    /** Decrease quantity by clicking - button */
    public void decreaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(quantityMinus)).click();
            } catch (TimeoutException e) {
                // Element not found or not clickable, skip
            }
        }
    }

    /** Click Buy Tickets button on detail page */
    public void clickBuyTickets() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(buyTicketsBtn)).click();
        } catch (TimeoutException e) {
            // Element not found or not clickable, skip
        }
    }

    /** Get total price text on detail page */
    public String getTotalPrice() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}