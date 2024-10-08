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
	
	/* Locators */
	
	@FindBy(xpath = "//h2[text()='Thank you for your order!']")
	private WebElement lbl_confirmMessage;
	
	/* This method verifies whether order confirmation message is presented to the user */
	public boolean validateOrderConfirmation() {
		return isElementDisplayed(lbl_confirmMessage);
	}

}
