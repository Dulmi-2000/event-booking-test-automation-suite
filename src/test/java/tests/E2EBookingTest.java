//package tests;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.E2EBooking.CategoryPage;
//import pages.E2EBooking.CheckoutPage;
//import pages.E2EBooking.EventPage;
//import pages.E2EBooking.LandingPage;
//import pages.E2EBooking.SuccessPage;
//
//public class E2EBookingTest extends BaseTest {
//  @Test
//  public void testFullBookingFlow() {
//
//      // Landing
//      LandingPage landing = new LandingPage(driver);
//      landing.selectFirstCategory();
//
//      // Events list (Category page)
//      CategoryPage category = new CategoryPage(driver);
//      Assert.assertTrue(category.isEventListVisible(), "Events not loaded");
//
//      category.selectFirstEvent();
//
//      // Event page
//      EventPage event = new EventPage(driver);
//      event.selectValidTickets(); // ensures <= max
//      event.clickBuy();
//
//      // Checkout
//      CheckoutPage checkout = new CheckoutPage(driver);
//      checkout.completePayment();
//
//      // Success page
//      SuccessPage success = new SuccessPage(driver);
//      Assert.assertTrue(success.isSuccessDisplayed(), "Payment failed");
//
//      // Browse more
//      success.clickMyBookingsBtn();
//
//      // Back to events list
//      Assert.assertTrue(category.isEventListVisible(), "Did not return to events page");
//  }}
