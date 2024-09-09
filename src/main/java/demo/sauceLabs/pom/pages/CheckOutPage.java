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
	
	public HashMap<String, String> validateItems() {
		for(int i = 0; i < lbl_item.size(); i++) {
			priceInCheckOutPage.put(getElementText(lbl_item.get(i)),getElementText(lbl_price.get(i)).replace("$", ""));
		}
		return priceInCheckOutPage;
	}
	
	public boolean validatePrice(HashMap<String, String> priceInHomePage) {
		return areEqual(priceInHomePage, validateItems());
	}
	
	public boolean validateTotalPrice() {
		System.out.println(lbl_subTotalPrice.getText());
		System.out.println(lbl_Tax.getText());
		System.out.println(lbl_TotalPrice.getText());
		return true;
	}
	
	public boolean validateSubTotal() {
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
	
	public boolean navigateToOrderConfirmPage() {
		btn_Finish.click();
		return isElementDisplayed("//span[text()='Checkout: Complete!']");
	}

}
