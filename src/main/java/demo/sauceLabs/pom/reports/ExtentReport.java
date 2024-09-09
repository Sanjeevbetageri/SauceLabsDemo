package demo.sauceLabs.pom.reports;

import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	
	private ExtentReport() {
		
	}
	
	static String Report_Path = System.getProperty("user.dir")+"/ExtentReports/";
	
	private static ExtentReports extent;
	
	public static void initReports() {
		if(Objects.isNull(extent)) {
			extent = new ExtentReports();
			ExtentSparkReporter spark = new ExtentSparkReporter(Report_Path);
			extent.attachReporter(spark);
			spark.config().setTheme(Theme.STANDARD);
		}
	}
	
	public static void createTest(String testCaseName) {
		ExtentManager.setExtentTest(extent.createTest(testCaseName));
	}
	
	public static void flushReports() {
		if(Objects.nonNull(extent)) {
			extent.flush();
		}
		ExtentManager.unload();
	}

}
