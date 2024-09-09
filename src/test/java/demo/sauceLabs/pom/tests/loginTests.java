package demo.sauceLabs.pom.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import demo.sauceLabs.pom.base.BaseTest;
import demo.sauceLabs.pom.pages.LoginPage;

public class loginTests extends BaseTest{
	
	@Test
	public void verifyUserLoggedIn() {
		Boolean status;
		
		LoginPage loginPage = new LoginPage(getDriver());
		status = loginPage.loginToApplication();
		Assert.assertEquals(status, true);
	}

}
