package com.mng.sapfiori.access.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;

public class PageLogin extends PageObjTM {
	
	private final static String IdInputUser = "USERNAME_FIELD-inner"; 
	private final static String IdInputPassword = "PASSWORD_FIELD-inner"; 
	private final static String IdButtonAccessSystem = "LOGIN_LINK";
	private final static String IdSelectIdioma = "LANGUAGE_SELECT";
	
	private PageLogin(WebDriver driver) {
		super(driver);
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
	
	public void clickAccederAlSistema() {
		click(By.id(IdButtonAccessSystem)).exec();
	}
}
