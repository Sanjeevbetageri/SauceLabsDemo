package demo.sauceLabs.pom.pages;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;

public class CartPage extends BasePage{

	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[text()='Checkout']")
	private WebElement btn_checkOut;
	
	@FindBy(xpath = "//span[text()='Checkout: Your Information']")
	private WebElement lbl_AddressPage;
	
	public boolean verifyItemsInCart(String items) {
		ArrayList<Boolean> status = new ArrayList<Boolean>();
		for(String item: getAsList(items)) {
			if(isElementDisplayed("//div[text()='"+item+"']")) 
				status.add(true);
		}
		return !status.contains(false);
	}

	public HashMap<String, String> captureItemPrice(String items) {
		HashMap<String, String> pricesInCartPage = new HashMap<String, String>();
		for(String item:getAsList(items)) {
			if(isElementDisplayed("//div[text()='"+item+"']")) {
				getElementText("//div[text()='"+item+"']/../following-sibling::div[@class='item_pricebar']");
				pricesInCartPage.put(item, getElementText("//div[text()='"+item+"']/../following-sibling::div[@class='item_pricebar']//div").replace("$", ""));
			}
		}
		return pricesInCartPage;
	}
	
	public boolean navigateToAddressPage() {
		btn_checkOut.click();
		return isElementDisplayed(lbl_AddressPage);
	}
}
