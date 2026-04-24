package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            // {email, password, expectedResult, description, expectedErrorMsg}
            {"kavindyawijenayaka12@gmail.com", "123456789", true, "Valid login", ""},
            {"wrong@gmail.com", "123456", false, "Invalid email", "Invalid email or password"},
            {"test@gmail.com", "wrongpass", false, "Invalid password", "Invalid email or password"},
            {"", "123456", false, "Empty email", "Invalid email or password"},
            {"test@gmail.com", "", false, "Empty password", "Invalid email or password"},
            {"testgmail.com", "123456", false, "Invalid email format", "Invalid email or password"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, boolean expectedResult, String description, String expectedErrorMsg) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        if (expectedResult) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should succeed: " + description);
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(expectedErrorMsg), "Login should fail: " + description);
        }
    }
}