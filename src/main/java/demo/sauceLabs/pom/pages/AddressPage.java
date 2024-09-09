package demo.sauceLabs.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;

public class AddressPage extends BasePage{
	
	public AddressPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

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
	
	public boolean enterAddress() {
		txtBox_firstName.sendKeys("Sanjeev");
		txtBox_lastName.sendKeys("Betageri");
		txtBox_Zip.sendKeys("560035");
		waitAndJsClick(btn_continue);
		return isElementDisplayed(lbl_checkOutOverView);
	}

}
