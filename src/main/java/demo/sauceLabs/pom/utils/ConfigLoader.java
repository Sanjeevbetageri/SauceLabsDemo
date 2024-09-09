package demo.sauceLabs.pom.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class ConfigLoader {
	
	public Properties properties;
	public static ConfigLoader configLoader;
	private final String propertyFilePath= System.getProperty("user.dir")+"/src/test/resources/Config.properties";

	/*
	 * public ConfigLoader(){ BufferedReader reader; try {
	 * System.out.println(propertyFilePath); reader = new BufferedReader(new
	 * FileReader(propertyFilePath)); properties = new Properties(); try {
	 * properties.load(reader); reader.close(); } catch (IOException e) {
	 * e.printStackTrace(); } } catch (FileNotFoundException e) {
	 * e.printStackTrace(); throw new
	 * RuntimeException("Configuration.properties not found at " +
	 * propertyFilePath); } }
	 */
	
	ConfigLoader(){
		properties = getConfigPropertyFile(propertyFilePath);
	}
	
	public ConfigLoader(String configFile) {
		properties = getConfigPropertyFile(configFile);
	}
	
	public Properties getConfigPropertyFile(String configFile) {
		return PropertyUtils.propertyLoader(configFile);
	}
	
	public static ConfigLoader getConfig() {
		if(configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}
	
	public String getPropertyValue(String propertyKey) {
		String prop = properties.getProperty(propertyKey);
		if(prop != null) {
			return prop.trim();
		} else {
			throw new RuntimeException("Property "+propertyKey+" is not specified in Config.properties file");
		}
	}
	
	public String getUserName(){
		return getPropertyValue("UserName");		
	}
	
	public String getPassword() {		
		return getPropertyValue("Password");	
	}
	
	public String getApplicationUrl() {
		return getPropertyValue("URL");
	}
	
	public String getItems(){
		return getPropertyValue("Items");		
	}
}