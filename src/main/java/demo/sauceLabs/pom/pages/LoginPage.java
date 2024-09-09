package demo.sauceLabs.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import demo.sauceLabs.pom.base.BasePage;
import demo.sauceLabs.pom.utils.ConfigLoader;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	//ConfigLoader configLoader = new ConfigLoader(null);
	
	
	@FindBy(xpath = "//input[@placeholder='Username']")
	private WebElement txtBox_userName;
	
	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement txtBox_password;
	
	@FindBy(xpath = "//input[@value='Login']")
	private WebElement btn_login;
	
	@FindBy(xpath = "//span[text()='Products']")
	private WebElement lbl_products;
	
	
	
	public boolean waitForLoginPageToLoad() {
		return waitForElementDisplayed(txtBox_userName);
	}

	public boolean loginToApplication() {
		waitAndSend(txtBox_userName, ConfigLoader.getConfig().getUserName());
		waitAndSend(txtBox_password, ConfigLoader.getConfig().getPassword());
		waitAndJsClick(btn_login);
		return waitForElementDisplayed(lbl_products);
	}
}
