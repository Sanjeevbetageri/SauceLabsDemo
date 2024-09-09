package demo.sauceLabs.pom.base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import demo.sauceLabs.pom.factory.abstractFactory.DriverManager;
import demo.sauceLabs.pom.factory.abstractFactory.DriverManagerFactoryAbstract;
import demo.sauceLabs.pom.reports.ExtentLogger;

public class BaseTest {

	protected WebDriver getDriver() {
		return DriverManager.getDriver();
	}
	
	private void setDriver(WebDriver driver) {
		DriverManager.setDriver(driver);
	}
	
	@Parameters( {"browser"})
	@BeforeTest
	public void startDriverInit(@Optional String browser) {
		browser = System.getProperty("browser", browser);
		if(browser == null)
			browser = "CHROME";
	}
	
	@Parameters({"browser"})
	@BeforeMethod
	public void startDriver(@Optional String browser) {
		browser = setBrowserValue(browser);
		setDriver(DriverManagerFactoryAbstract.getManager(browser).createDriver());
		BasePage basePage = new BasePage(getDriver());
		//basePage.load(driver.);
		basePage.load("https://www.saucedemo.com/");
	}
	
	private String setBrowserValue(String browser) {
		if(browser == null) {
			browser = "CHROME";
		}
		browser = System.getProperty("browser", browser);
		return browser;
	}
	
	@AfterMethod
	public void tearDown(@Optional String browser) {
		getDriver().quit();
	}
	
	public void takeScreenshotOnTestFailure(String browser, ITestResult result) throws IOException {
		browser = setBrowserValue(browser);
		switch(result.getStatus()) {
		case ITestResult.FAILURE : 
			File destFile = new File("Screenshots" + File.separator + browser + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName()+".png");
			takeScreenshot(destFile);
			break;
		case ITestResult.SUCCESS :
			break;
		case ITestResult.SKIP :
			break;
		}
	}
	
	public void takeScreenshot(File destFile) throws IOException {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, destFile);
	}
	
	@AfterSuite
	public void afterSuite(@Optional String browser) {
		killBrowser();
	}
	
	public void killBrowser() {
		try {
			Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
		}
		catch(Exception e) {
			
		}
	}
	
	public Boolean validateStatusReportLog(Boolean status, String passMessage, String failMessage) {
		if(status) {
			ExtentLogger.pass(passMessage);
		} else {
			ExtentLogger.fail(failMessage, true);
			Assert.assertTrue(status, failMessage);
		}
		return status;
	}
}
