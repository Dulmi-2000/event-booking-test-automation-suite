package tests;

import pages.EventPage;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EventPageTest extends BaseTest {

    // ── Listing page tests ──────────────────────────────────────────────

    @Test
    public void testEventsAreDisplayed() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        Assert.assertTrue(eventPage.getEventCount() > 0,
            "At least one event should be displayed");
    }

    @Test
    public void testEventCountMatchesLabel() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();

        String label = eventPage.getEventsFoundText();
        Assert.assertFalse(label.isEmpty(),
            "Events found label should be visible");

        int labelCount = Integer.parseInt(label.replaceAll("\\D", ""));
        Assert.assertEquals(eventPage.getEventCount(), labelCount,
            "Card count should match the events found label");
    }

    @Test
    public void testGetTicketsNavigatesToDetailPage() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();
        Assert.assertTrue(driver.getCurrentUrl().contains("/events/"),
            "Should navigate to event detail page");
    }

    @Test
    public void testFeaturedBadgeIsVisible() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        Assert.assertTrue(eventPage.isFeaturedBadgeDisplayed(),
            "At least one Featured badge should be visible");
    }

    @Test
    public void testLowStockBadgeShowsWhenApplicable() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        // informational only
        System.out.println("Low stock badge visible: " + eventPage.isLowStockDisplayed());
    }

    // ── Category filter tests ───────────────────────────────────────────

    @DataProvider(name = "categoryFilters")
    public Object[][] categoryFilters() {
        return new Object[][] {
            {"Concerts"},
            {"Theatre"},
            {"Sports"},
            {"Family & Other"},
        };
    }

    @Test(dataProvider = "categoryFilters")
    public void testCategoryFilterShowsResultsOrEmptyState(String category) {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsByCategory(category);

        boolean hasCards      = eventPage.getEventCount() > 0;
        boolean hasEmptyState = eventPage.isEmptyStateDisplayed();

        Assert.assertTrue(hasCards || hasEmptyState,
            "Should show events or empty state for: " + category);
    }

    // ── Detail page tests ───────────────────────────────────────────────

    @Test
    public void testQuantityDefaultsToOne() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();
        Assert.assertEquals(eventPage.getCurrentQuantity(), 1,
            "Default quantity should be 1");
    }

    @Test
    public void testIncreaseQuantity() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();
        eventPage.increaseQuantity(2);
        Assert.assertEquals(eventPage.getCurrentQuantity(), 3,
            "Quantity should be 3 after increasing twice");
    }

    @Test
    public void testDecreaseQuantityNotBelowOne() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();
        eventPage.decreaseQuantity(5);
        Assert.assertEquals(eventPage.getCurrentQuantity(), 1,
            "Quantity should not go below 1");
    }

    @Test
    public void testTotalPriceUpdatesWithQuantity() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();

        String priceQty1 = eventPage.getTotalPrice();
        eventPage.increaseQuantity(1);
        String priceQty2 = eventPage.getTotalPrice();

        Assert.assertNotEquals(priceQty1, priceQty2,
            "Total price should change when quantity changes");
    }

    @Test
    public void testBuyTicketsRedirectsToLoginWhenNotLoggedIn() {
        EventPage eventPage = new EventPage(driver);
        eventPage.goToEventsPage();
        eventPage.clickFirstGetTickets();
        eventPage.clickBuyTickets();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
            "Should redirect to login when not authenticated");
    }
}