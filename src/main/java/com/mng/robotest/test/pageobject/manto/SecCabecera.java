package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecCabecera {

	static String XPathLitTienda = "//td/span[@class='txt8BDis']";
	static String XPathButtonSelTienda = "//input[@type='submit' and @value[contains(.,'Seleccionar tienda')]]";
	static String XPathLinkVolverMenu = "//a[text()[contains(.,'volver al menu')]] | //a/img[@src='/images/logo-mango.png']";

	public static String getLitTienda(WebDriver driver) {
		String litTienda = "";
		if (state(Present, By.xpath(XPathLitTienda), driver).check()) {
			litTienda = driver.findElement(By.xpath(XPathLitTienda)).getText();	
		}
		return litTienda;
	}

	public static void clickButtonSelTienda(WebDriver driver) {
		click(By.xpath(XPathButtonSelTienda), driver).exec();
	}

	public static void clickLinkVolverMenuAndWait(WebDriver driver, int seconds) {
		click(By.xpath(XPathLinkVolverMenu), driver).waitLink(seconds).exec();
		waitForPageLoaded(driver, seconds);
	}
}
