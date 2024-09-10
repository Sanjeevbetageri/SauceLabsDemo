package demo.sauceLabs.pom.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;

public class HomePage extends BasePage{
	
	HashMap<String, String> item_price = new HashMap<String, String>();

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	/* Locators */
	
	@FindBy(xpath="//a[@data-test='shopping-cart-link']")
	private WebElement lnk_Cart;
	
	@FindBy(xpath="//span[@class='shopping_cart_badge']")
	private WebElement lnk_CartWithItems;
	
	@FindBy(xpath="//span[text()='Your Cart']")
	private WebElement lbl_yourCart;
	
	/* This method adds all the configured items to the cart. Returns true if all the items are added and Cart icon is updated.
	 * This will fail if any product is not present in the page */
	public boolean addItemsToCart(String itemsToBeAdded) {
		for(String item: getAsList(itemsToBeAdded)) {
			if(isElementDisplayed("//div[text()='"+item+"']/../../following-sibling::div//button")) {
				waitAndJsClick("//div[text()='"+item+"']/../../following-sibling::div//button");
			}
			else {
				System.out.println("Item "+item+" is not present in the HomePage");
			}
		}
		return isElementDisplayed(lnk_CartWithItems);
	}
	
	/* This method captures the prices of items configured in a HashMap */
	public HashMap<String, String> captureItemAndPrice(String itemName) {
		for(String item: getAsList(itemName)) {
		String locatorItemPrice = "//div[text()='"+item+"']/../../following-sibling::div//div";
		String price = getElementText(locatorItemPrice).replace("$", "");
		item_price.put(item, price);
		}
		return item_price;
	}
	
	/* This method will navigate to Cart Page and verifies if user lands on Cart Page */
	public boolean navigateToCartPage() {
		waitAndJsClick(lnk_Cart);
		waitFor(2);
		return isElementDisplayed(lbl_yourCart);
	}

}
