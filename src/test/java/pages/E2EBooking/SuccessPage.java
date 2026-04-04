package pages.E2EBooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPage {

    WebDriver driver;

    public SuccessPage(WebDriver driver) {
        this.driver = driver;
    }

    By successMessage = By.xpath("(//h3[normalize-space()='Booking Confirmed!'])[1]");
    By myBookingsBtn = By.xpath("(//a[normalize-space()='View My Bookings'])[1]");

    public boolean isSuccessDisplayed() {
        return driver.findElement(successMessage).isDisplayed();
    }

    public void clickMyBookingsBtn() {
        driver.findElement(myBookingsBtn).click();
    }
}