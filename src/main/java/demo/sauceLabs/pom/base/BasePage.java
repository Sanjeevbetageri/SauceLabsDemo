package demo.sauceLabs.pom.base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.model.SystemEnvInfo;

import demo.sauceLabs.pom.reports.ExtentLogger;
import demo.sauceLabs.pom.utils.ConfigLoader;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void load(String url) {
		driver.get(ConfigLoader.getConfig().getApplicationUrl());
	}

	public Boolean waitForElementDisplayed(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch (Exception e) {
			System.out.println("waitForElementDisplayed Failed with " + e.getMessage());
			return false;
		}
	}

	public Boolean waitForElementDisplayed(String element) {
		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element))).isDisplayed();
		} catch (Exception e) {
			System.out.println("waitForElementDisplayed Failed with " + e.getMessage());
			return false;
		}
	}

	public WebElement findElement(String locator, String type) {
		WebElement element;
		switch (type) {
		case "XPATH":
			element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		default:
			element = driver.findElement(By.xpath(locator));
		}
		return element;
	}
	
	public List<WebElement> findElements(String locator, String type) {
		List <WebElement> element;
		switch (type) {
		case "XPATH":
			element = driver.findElements(By.xpath(locator));
		case "ID":
			element = driver.findElements(By.id(locator));
		default:
			element = driver.findElements(By.xpath(locator));
		}
		return element;
	}
	
	public void waitAndSend(WebElement element, String value) {
		waitForElementDisplayed(element);
		element.sendKeys(value);
	}
	
	public void waitAndSend(String locator, String value) {
		WebElement element = driver.findElement(By.xpath(value));
		element.sendKeys(value);
	}
	
	public void waitAndClick(WebElement element) {
		waitForElementDisplayed(element);
		element.click();
	}
	
	public void waitAndClick(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}
	
	public void waitAndJsClick(WebElement element) {
		waitForElementDisplayed(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
	}
	
	public void waitAndJsClick(String locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", findElement(locator, "XPATH"));
	}

	public boolean isElementDisplayed(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean isElementDisplayed(String locator) {
		List<WebElement> elements = findElements(locator, "XPATH");
		return !elements.isEmpty();
	}
	
	public String getElementText(WebElement element) {
		waitForElementDisplayed(element);
		return element.getText();
	}
	
	public String getElementText(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}
	
	public boolean areEqual(Map<String, String> first, Map<String, String> second) {
	    if (first.size() != second.size()) {
	        return false;
	    }

	    return first.entrySet().stream()
	      .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
	}
	
	public String retainDecimalValue(String value) {
		return value.replaceAll("[^\\d.]", "");
	}
	
	public ArrayList<String> getAsList(String items){
		String[] arr = items.split(",");
		ArrayList<String> Items = new ArrayList<String>();
		for(String item: arr) {
			Items.add(item);
		}
		return Items;
	}
	
	public void waitFor(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch(Exception e) {
			System.out.println("waitFor() failed with "+ e);
		}
	}
	
}
