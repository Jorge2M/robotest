package com.mng.sapfiori.test.testcase.webobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageLogin extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String IdInputUser = "USERNAME_FIELD-inner"; 
	private final static String IdInputPassword = "PASSWORD_FIELD-inner"; 
	private final static String IdButtonAccessSystem = "LOGIN_LINK";
	private final static String IdSelectIdioma = "LANGUAGE_SELECT";
	
	private PageLogin(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageLogin getNew(WebDriver driver) {
		return new PageLogin(driver);
	}
	
	public void goTo(String url) {
		driver.get(url);
	}

	public void selectIdioma(String codeIdioma) {
		new Select(driver.findElement(By.id(IdSelectIdioma))).selectByValue(codeIdioma);
	}
	
	public void inputCredentials(String login, String password) {
		driver.findElement(By.id(IdInputUser)).sendKeys(login);
		driver.findElement(By.id(IdInputPassword)).sendKeys(password);
	}
	
	public PageInitial clickAccederAlSistema() throws Exception {
		clickAndWaitLoad(driver, By.id(IdButtonAccessSystem));
		return PageInitial.getNew(driver);
	}
}
