package demo.sauceLabs.pom.tests;

import java.util.ArrayList;
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

public class HomePageTests extends BaseTest{

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
		ArrayList<String> Items = new ArrayList<>(Arrays.asList("Sauce Labs Bolt T-Shirt","Test.allTheThings() T-Shirt (Red)"));
		String items = ConfigLoader.getConfig().getItems();
		
		status = loginPage.loginToApplication();
		Assert.assertEquals(status, true);
		validateStatusReportLog(status, "Logged in to App successfully", "Unable to login");
		
		status = homePage.addItemsToCart(items);
		Assert.assertEquals(status, true);
		validateStatusReportLog(status, "Items Added to Cart", "Unable to add Items to the Cart");
		
		priceInHomePage = homePage.capturePrice(items);
		System.out.println("Home "+priceInHomePage);
		
		status = homePage.navigateToCart();
		validateStatusReportLog(status, "Navigated to Cart Page", "Unable to navigate to Carts Page");
		
		cartPage.verifyItemsInCart(items);
		validateStatusReportLog(status, "Verified the Added Items are present in Cart Page", "Items added to Cart are absent in the Cart Page");
		
		priceInCartPage = cartPage.captureItemPrice(items);
		
		System.out.println("Cart "+priceInCartPage);

		status = cartPage.areEqual(priceInHomePage, priceInCartPage);
		validateStatusReportLog(status, "Verified the item price in Cart matches price in Home page", "Item prices in Cart do not match the price in Home Page");
		
		status = cartPage.navigateToAddressPage();
		validateStatusReportLog(status, "Navigated to Address Page", "Unable to navigate to Address Page");
		
		status = addressPage.enterAddress();
		validateStatusReportLog(status, "Added Address and Navigated to Checkout Page", "Unable to navigate to Checkout Page");
		
		status = checkOutPage.validatePrice(priceInHomePage);
		validateStatusReportLog(status, "Verified the item price in Checkout page matches price in Home page", "Item prices in Checkout page do not match the price in Home Page");
		
		status = checkOutPage.validateSubTotal();
		validateStatusReportLog(status, "Verified that Total price is calculated correctly", "Total Price presented on the CheckOut Page is incorrect");
		
		status = checkOutPage.navigateToOrderConfirmPage();
		validateStatusReportLog(status, "Navigated to Order Confirmation Page", "Unable to navigate to Confirmation Page");
		
		status = orderConfirmationPage.validateOrderConfirmation();
		validateStatusReportLog(status, "Verified that order confirmation message is displayed", "Order confirmation message is not displayed");
	}
}
