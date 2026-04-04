package pages.E2EBooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class CategoryPage {

    WebDriver driver;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    By eventCards = By.xpath("(//div)[24]");

    public void selectFirstEvent() {
        driver.findElements(eventCards).get(0).click();
    }

    public boolean isEventListVisible() {
        return driver.findElements(eventCards).size() > 0;
    }
}
