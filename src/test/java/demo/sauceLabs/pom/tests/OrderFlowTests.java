package demo.sauceLabs.pom.tests;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.sauceLabs.pom.base.BaseTest;
import demo.sauceLabs.pom.pages.AddressPage;
import demo.sauceLabs.pom.pages.CartPage;
import demo.sauceLabs.pom.pages.CheckOutPage;
import demo.sauceLabs.pom.pages.HomePage;
import demo.sauceLabs.pom.pages.LoginPage;
import demo.sauceLabs.pom.pages.OrderConfirmationPage;
import demo.sauceLabs.pom.reports.ExtentLogger;
import demo.sauceLabs.pom.utils.ConfigLoader;

public class OrderFlowTests extends BaseTest{

	@Test(description = "Verify that user can Add Items to Cart, Enter Address Details and Place Order")
	public void addToCart() throws InterruptedException {
		Boolean status;
		LoginPage loginPage = new LoginPage(getDriver());
		HomePage homePage = new HomePage(getDriver());
		CartPage cartPage = new CartPage(getDriver());
		AddressPage addressPage = new AddressPage(getDriver());
		CheckOutPage checkOutPage = new CheckOutPage(getDriver());
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(getDriver());
		HashMap<String, String> priceInHomePage = new HashMap<String, String>();
		HashMap<String, String> priceInCartPage = new HashMap<String, String>();
		String items = ConfigLoader.getConfig().getItems();
		SoftAssert softAssert = new SoftAssert();
		
		/* Login to Application */
		status = loginPage.loginToApplication();
		Assert.assertEquals(status, true);
		validateStatusReportLog(status, "Logged in to App successfully", "Unable to login");
		
		/* Add the items to cart */
		status = homePage.addItemsToCart(items);
		Assert.assertEquals(status, true);
		validateStatusReportLog(status, "Items Added to Cart", "Unable to add Items to the Cart");
		
		/* Capture Item and Price from Home Page */
		priceInHomePage = homePage.captureItemAndPrice(items);
		
		/* Navigate to Cart Page */
		status = homePage.navigateToCartPage();
		validateStatusReportLog(status, "Navigated to Cart Page", "Unable to navigate to Carts Page");
		
		/* Verify the items present in Cart Page match the items added in Home Page */
		cartPage.verifyItemsInCart(items);
		validateStatusReportLog(status, "Verified the Added Items are present in Cart Page", "Items added to Cart are absent in the Cart Page");
		
		/* Capture the prices in Cart Page */
		priceInCartPage = cartPage.captureItemPriceInCartPage(items);

		/* Verify the price of items in Cart matches the price in Home Page */
		status = cartPage.areEqual(priceInHomePage, priceInCartPage);
		validateStatusReportLog(status, "Verified the items and item price in Cart matches price in Home page", "Item and item prices in Cart do not match the price in Home Page");
		
		/* Navigate to Address Page */
		status = cartPage.navigateToAddressPage();
		validateStatusReportLog(status, "Navigated to Address Page", "Unable to navigate to Address Page");
		
		/* Verify that Address fields are Mandatory */
		status = addressPage.validateMandatoryFields();
		validateStatusReportLog(status, "Address fields are Mandatory", "Address Fields are not Mandatory");
		
		/* Enter address details and navigate to Checkout Page */
		status = addressPage.enterAddress();
		validateStatusReportLog(status, "Added Address and Navigated to Checkout Page", "Unable to navigate to Checkout Page");
		
		/* Verify the items and prices in Checkout page matches the details in Home Page */
		status = checkOutPage.validateItemsAndPrice(priceInHomePage);
		validateStatusReportLog(status, "Verified the items and item price in Checkout page matches price in Home page", "Items and Item prices in Checkout page do not match the price in Home Page");
		
		/* Verify the Communication details are present in Checkout Page */
		status = checkOutPage.verifyAddressInCheckoutPage();
		softAssert.assertEquals(status, false);
		softAssertStatusReportLog(status,"Address details present in Checkout Page","Address details not present in Checkout Page");
		
		/* Verify the subTotal and Total price are calculated correctly */
		status = checkOutPage.validateCalculatedPrices();
		validateStatusReportLog(status, "Verified that Total price is calculated correctly", "Total Price presented on the CheckOut Page is incorrect");
		
		/* Navigate to Order Confirmation Page */
		status = checkOutPage.navigateToOrderConfirmPage();
		validateStatusReportLog(status, "Navigated to Order Confirmation Page", "Unable to navigate to Confirmation Page");
		
		/* Verify that order confirmation message is presented */
		status = orderConfirmationPage.validateOrderConfirmation();
		validateStatusReportLog(status, "Verified that order confirmation message is displayed", "Order confirmation message is not displayed");
	
		softAssert.assertAll();
	}
}
