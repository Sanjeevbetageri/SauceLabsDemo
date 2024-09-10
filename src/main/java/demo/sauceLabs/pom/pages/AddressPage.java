package demo.sauceLabs.pom.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;
import demo.sauceLabs.pom.utils.ConfigLoader;

public class AddressPage extends BasePage{
	
	public AddressPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/* Locators */
	
	@FindBy(xpath = "//input[@name='firstName']")
	private WebElement txtBox_firstName;
	
	@FindBy(xpath = "//input[@name='lastName']")
	private WebElement txtBox_lastName;
	
	@FindBy(xpath = "//input[@name='postalCode']")
	private WebElement txtBox_Zip;
	
	@FindBy(xpath = "//input[@name='continue']")
	private WebElement btn_continue;
	
	@FindBy(xpath = "//span[text()='Checkout: Overview']")
	private WebElement lbl_checkOutOverView;
	
	@FindBy(xpath = "//button[@class='error-button']")
	private WebElement btn_Error;
	
	@FindBy(xpath = "//*[@data-icon='times-circle']")
	private List<WebElement> icon_errors;
	
	/* This method checks validates the communication details are mandatory */
	public boolean validateMandatoryFields() {
		waitAndJsClick(btn_continue);
		waitFor(2);
		return isElementDisplayed(btn_Error) && icon_errors.size()==3;
	}
	
	/* This method enters the address details and navigates to Checkout Page. Also verifies if user lands on Checkout Page */
	public boolean enterAddress() {
		txtBox_firstName.sendKeys(ConfigLoader.getConfig().getFirstName());
		txtBox_lastName.sendKeys(ConfigLoader.getConfig().getLastName());
		txtBox_Zip.sendKeys(ConfigLoader.getConfig().getZIP());
		waitFor(2);
		waitAndJsClick(btn_continue);
		waitFor(2);
		return isElementDisplayed(lbl_checkOutOverView);
	}

}
