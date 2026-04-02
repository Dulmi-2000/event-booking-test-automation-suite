package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By signInBtn = By.xpath("//a[contains(text(),'Sign In')]"); // Nav bar button to open login form
    By email = By.id("email");
    By password = By.id("password");
    By loginBtn = By.xpath("//button[contains(text(),'Sign In')]"); // Login button inside form

    // No fixed ID in Next.js + Tailwind — use text for error messages and dashboard
    By dashboard = By.xpath("//*[contains(text(),'Dashboard') or contains(text(),'Welcome')]"); // adjust to your dashboard text

    /**
     * Performs login
     */
    public void login(String user, String pass) throws InterruptedException {
        // Open login form
        wait.until(ExpectedConditions.elementToBeClickable(signInBtn)).click();

        // Wait for fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        wait.until(ExpectedConditions.visibilityOfElementLocated(password));

        // Enter credentials
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);

        // Click login
        driver.findElement(loginBtn).click();

        // Wait a bit for page response
        Thread.sleep(1000);
    }

    /**
     * Checks if login was successful by looking for dashboard text
     */
    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboard));
            return driver.findElement(dashboard).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if login failed by looking for error message div with text
     */
    public boolean isErrorMessageDisplayed(String expectedText) {
        try {
            By errorMsg = By.xpath("//div[contains(text(),'" + expectedText + "')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
            return driver.findElement(errorMsg).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}