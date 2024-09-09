package demo.sauceLabs.pom.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import demo.sauceLabs.pom.reports.ExtentReport;

public class ListenerClass implements ITestListener, ISuiteListener {

	@Override
	public void onStart(ISuite suite) {
		ExtentReport.initReports();
	}
	
	@Override
	public void onFinish(ISuite suite) {
		ExtentReport.flushReports();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		ExtentReport.createTest(result.getMethod().getDescription());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		String logText = result.getMethod().getDescription()+" is Passed.";
	}
	
	
}
