package com.mng.sapfiori.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageLogin extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String IdInputUser = "USERNAME_FIELD-inner"; 
	private final static String IdInputPassword = "PASSWORD_FIELD-inner"; 
	private final static String IdButtonAccessSystem = "LOGIN_LINK";
	
	private PageLogin(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageLogin getNew(WebDriver driver) {
		return new PageLogin(driver);
	}
	
	public void goTo(String url) {
		driver.get(url);
	}

	public void inputCredentials(String login, String password) throws Exception {
		driver.findElement(By.id(IdInputUser)).sendKeys(login);
		driver.findElement(By.id(IdInputPassword)).sendKeys(login);
		WebdrvWrapp.clickAndWaitLoad(driver, By.id(IdButtonAccessSystem));
	}
}
