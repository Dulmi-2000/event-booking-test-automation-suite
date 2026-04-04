package pages.E2EBooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    //card details
    By cardname = By.id("name");
    By cardNumber = By.id("mockPay");
    By cardExpireDate = By.id("expiry");
    By cardCVC = By.id("cvc");
    By payButton = By.xpath("(//button[normalize-space()='Confirm Payment'])[1]");

    public void completePayment() {
        driver.findElement(payButton).click();
    }
}
