package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    WebDriver driver;
    WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By signInBtn = By.xpath("//a[contains(text(),'Login')]");
    By formBtn = By.xpath("//a[contains(text(),'Sign up')]");
    By fullName = By.id("name");
    By email = By.id("email");
    By password = By.id("password");
    By confirmPassword = By.id("confirmPassword");
    By registerBtn = By.xpath("//button[contains(text(),'Create Account')]");
    By dashboard = By.xpath("//*[contains(text(),'Dashboard') or contains(text(),'Welcome')]");

    // Field-specific error locators (adjust IDs if different in HTML)
    By nameError = By.id("name-error");
    By emailError = By.id("email-error");
    By passwordError = By.id("password-error");
    By confirmPasswordError = By.id("confirmPassword-error");

    // General server-side error
    By generalError = By.id("generalError");

    /** Performs signup */
    public void signup(String fname, String mail, String pass, String confirmPass) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(signInBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(formBtn)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName));
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        wait.until(ExpectedConditions.visibilityOfElementLocated(password));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword));

        driver.findElement(fullName).clear();
        driver.findElement(fullName).sendKeys(fname);

        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(mail);

        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);

        driver.findElement(confirmPassword).clear();
        driver.findElement(confirmPassword).sendKeys(confirmPass);

        driver.findElement(registerBtn).click();
        Thread.sleep(500); // wait for response / error messages
    }

    /** Checks if signup was successful */
    public boolean isSignupSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboard));
            return driver.findElement(dashboard).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Server-side general error */
    public String getGeneralErrorMessage() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(generalError));
            return error.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Field-specific inline errors: name, email, password, passwordConfirm */
    public String getFieldErrorMessage(String field) {
        By locator;
        switch (field) {
            case "name": locator = nameError; break;
            case "email": locator = emailError; break;
            case "password": locator = passwordError; break;
            case "passwordConfirm": locator = confirmPasswordError; break;
            default: throw new IllegalArgumentException("Unknown field: " + field);
        }
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return error.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Old method: check general error by text (can be kept for backward compatibility) */
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