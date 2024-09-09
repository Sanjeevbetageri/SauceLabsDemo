package demo.sauceLabs.pom.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import demo.sauceLabs.pom.factory.abstractFactory.DriverManager;

public class ScreenshotUtils {
	
	private ScreenshotUtils() {
		
	}

	public static String getBase64Image() {
		return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64)	;
	}
}
