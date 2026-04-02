package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
    	
    	driver= new ChromeDriver();
		driver.get("http://localhost:3000");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}