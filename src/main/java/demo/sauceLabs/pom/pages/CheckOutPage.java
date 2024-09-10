package demo.sauceLabs.pom.pages;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;

public class CheckOutPage extends BasePage{

	public CheckOutPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	/* Locators */
	@FindBy(xpath = "//div[@class='inventory_item_name']")
	private List<WebElement> lbl_item;
	
	@FindBy(xpath = "//div[@class='item_pricebar']")
	private List<WebElement> lbl_price;
	
	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	private WebElement lbl_subTotalPrice;
	
	@FindBy(xpath = "//div[@class='summary_tax_label']")
	private WebElement lbl_Tax;
	
	@FindBy(xpath = "//div[@class='summary_total_label']")
	private WebElement lbl_TotalPrice;
	
	@FindBy(xpath = "//button[text()='Finish']")
	private WebElement btn_Finish;
	
	
	HashMap<String, String> priceInCheckOutPage = new HashMap<String, String>();
	
	/* This method captures the items and their price present on Checkout Page */
	public HashMap<String, String> captureItemsAndPrices() {
		for(int i = 0; i < lbl_item.size(); i++) {
			priceInCheckOutPage.put(getElementText(lbl_item.get(i)),getElementText(lbl_price.get(i)).replace("$", ""));
		}
		return priceInCheckOutPage;
	}
	
	/* This method checks the items and prices presented in Checkout page against Home Page values */
	public boolean validateItemsAndPrice(HashMap<String, String> priceInHomePage) {
		return areEqual(priceInHomePage, captureItemsAndPrices());
	}
	
	/* Validate various price components */
	public boolean validateCalculatedPrices() {
		float subTotal = 0;
		float Tax;
		float Total;
		for(Map.Entry<String, String> it: priceInCheckOutPage.entrySet()) {
			subTotal = subTotal+Float.valueOf(it.getValue());
		}
		System.out.println("Is SubTotal equal "+Float.valueOf(retainDecimalValue(lbl_subTotalPrice.getText().trim())).equals(subTotal));
		Tax = Float.valueOf(retainDecimalValue(lbl_Tax.getText().trim()));
		Total = Float.valueOf(retainDecimalValue(lbl_TotalPrice.getText().trim()));
		return Total == subTotal + Tax ;
	}
	
	/* Navigate to Order Confirmation Page and validate if user lands on the Page */
	public boolean navigateToOrderConfirmPage() {
		btn_Finish.click();
		waitFor(2);
		return isElementDisplayed("//span[text()='Checkout: Complete!']");
	}
	
	/* Verify the Communication details are present in Checkout Page */
	public boolean verifyAddressInCheckoutPage() {
		return isElementDisplayed("//*[contains(text(),'Sanjeev')]");
	}

}
