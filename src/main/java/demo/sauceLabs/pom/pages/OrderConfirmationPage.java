package demo.sauceLabs.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;

public class OrderConfirmationPage extends BasePage {

	public OrderConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//h2[text()='Thank you for your order!']")
	private WebElement lbl_confirmMessage;
	
	public boolean validateOrderConfirmation() {
		return isElementDisplayed(lbl_confirmMessage);
	}

}
