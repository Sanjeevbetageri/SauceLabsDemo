package demo.sauceLabs.pom.reports;

import com.aventstack.extentreports.MediaEntityBuilder;

import demo.sauceLabs.pom.utils.ConfigLoader;
import demo.sauceLabs.pom.utils.ScreenshotUtils;

public class ExtentLogger {

	private ExtentLogger() {
		
	}
	
	public static void pass(String Message) {
		ExtentManager.getExtentTest().pass(Message);
	}
	
	public static void fail(String Message) {
		ExtentManager.getExtentTest().fail(Message);
	}
	
	public static void skip(String Message) {
		ExtentManager.getExtentTest().skip(Message);
	}
	
	public static void fail(String Message, boolean isScreenshotNeeded) {
		if(isScreenshotNeeded) {
			ExtentManager.getExtentTest().fail(Message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
		}
	}
}
