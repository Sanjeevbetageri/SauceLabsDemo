package demo.sauceLabs.pom.factory.abstractFactory;

public class DriverManagerFactoryAbstract {
	
	public static DriverManager_OC getManager(String driverType) {
		switch(driverType) {
		case "CHROME" : return new ChromeDriverManagerAbstract();
		}
		return new ChromeDriverManagerAbstract();
	}
}
