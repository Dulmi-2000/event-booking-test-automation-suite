package pages.E2EBooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class LandingPage {
 
	WebDriver driver;

	
    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Click first category 
    By firstCategory = By.xpath("(//span[normalize-space()='Sports'])[1]");

    public void selectFirstCategory() {
        driver.findElement(firstCategory).click();
    }
    
}
