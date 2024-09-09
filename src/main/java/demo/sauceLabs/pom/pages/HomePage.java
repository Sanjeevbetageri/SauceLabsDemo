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
	
	//ArrayList<String> itemsToBeAdded = new ArrayList<String>(Arrays.asList("A","B"));
	HashMap<String, String> item_price = new HashMap<String, String>();

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@data-test='shopping-cart-link']")
	private WebElement lnk_Cart;
	
	@FindBy(xpath="//span[@class='shopping_cart_badge']")
	private WebElement lnk_CartWithItems;
	
	@FindBy(xpath="//span[text()='Your Cart']")
	private WebElement lbl_yourCart;
	
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
	
	public HashMap<String, String> capturePrice(String itemName) {
		for(String item: getAsList(itemName)) {
		String locatorItemPrice = "//div[text()='"+item+"']/../../following-sibling::div//div";
		String price = getElementText(locatorItemPrice).replace("$", "");
		item_price.put(item, price);
		}
		return item_price;
	}
	
	public boolean navigateToCart() throws InterruptedException {
		waitAndJsClick(lnk_Cart);
		Thread.sleep(3000);
		return isElementDisplayed(lbl_yourCart);
	}

}
