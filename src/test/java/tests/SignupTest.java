package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SignupPage;

public class SignupTest extends BaseTest {

    @DataProvider(name = "signupData")
    public Object[][] signupData() {
        return new Object[][] {
            // {fullName, email, password, confirmPassword, expectedResult, expectedErrorMsg}
            {"Kavindya","kavindya@gmail.com", "123456789","123456789", true, ""}, // new user, positive
            {"Kavindya Wijenayaka","kavindyawijenayaka12@gmail.com", "123456789","123456789", false, "Existing User"}, // all existing, general error
            {"Kavindya Wijenayaka","kavindyawijenayaka12@gmail.com", "123iiiiii","123iiiiii", false, "Existing User"}, // name & email existing
            {"Kavindya Wijenayaka","kavindya12@gmail.com", "123iiiiii","123iiiiii", false, "Existing User Name"}, // name existing
            {"Wijenayaka","kavindya12@gmail.com", "123iiiiii","123iiiiii", false, "Existing User email"}, // email existing
            {"Saman", "saman@gmail.com", "12345678","12345kkg", false, "Mismatching password"}, // password mismatch
            {"Test","test@gmail.com", "wrong", "wrong", false, "Invalid email or password"}, // invalid password format
            {"","test@gmail.com", "12345678","12345678", false, "Required"}, // empty name → field error
            {"User", "", "12345678","12345678", false, "Required"}, // empty email → field error
            {"Test User","test@gmail.com", "","", false, "Required"}, // empty password → field error
            {"Test User","testgmail.com", "12345678","12345678", false, "Invalid email format"} // invalid email → field error
        };
    }

    @Test(dataProvider = "signupData")
    public void testSignup(String fullName, String email, String password, String confirmPassword,
                           boolean expectedResult, String expectedErrorMsg) throws InterruptedException {

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(fullName, email, password, confirmPassword);

        if (expectedResult) {
            // Positive test: __signup__ should succeed
            Assert.assertTrue(signupPage.isSignupSuccessful(), "Signup should succeed for valid input.");
        } else {
            // Negative test: check the right error message
            String actualError = "";

            // Server-side general errors
            if (expectedErrorMsg.contains("Existing")) {
                actualError = signupPage.getGeneralErrorMessage();
            }
            // Field-specific errors
            else if (expectedErrorMsg.equals("Mismatching password")) {
                actualError = signupPage.getFieldErrorMessage("passwordConfirm");
            }
            else if (expectedErrorMsg.equals("Required")) {
                if (fullName.isEmpty()) {
                    actualError = signupPage.getFieldErrorMessage("name");
                } else if (email.isEmpty()) {
                    actualError = signupPage.getFieldErrorMessage("email");
                } else if (password.isEmpty()) {
                    actualError = signupPage.getFieldErrorMessage("password");
                }
            }
            else if (expectedErrorMsg.equals("Invalid email format")) {
                actualError = signupPage.getFieldErrorMessage("email");
            }
            else if (expectedErrorMsg.equals("Invalid email or password")) {
                actualError = signupPage.getFieldErrorMessage("password");
                if (actualError.isEmpty()) {
                    actualError = signupPage.getGeneralErrorMessage();
                }
            }

            Assert.assertEquals(actualError, expectedErrorMsg,
                    "Error message mismatch for input: " + fullName + ", " + email);
        }
    }
}