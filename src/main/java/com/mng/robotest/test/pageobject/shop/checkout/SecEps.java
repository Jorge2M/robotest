package com.mng.robotest.test.pageobject.shop.checkout;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecEps extends PageObjTM {

	private final static String XPathInputBanco = "//div[@id='eps-bank-selector']";
	private final static String iniXPathSelectOptionBanco = XPathInputBanco + "//option[text()[contains(.,'";
	
	public SecEps(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathSelectOptionBanco(String banco) {
		return iniXPathSelectOptionBanco + banco + "')]]";
	}

	public void selectBanco(String nombreBanco) {
		String XPathSelectOptionBanco = getXPathSelectOptionBanco(nombreBanco);
		driver.findElement(By.xpath(XPathInputBanco)).click();
		driver.findElement(By.xpath(XPathSelectOptionBanco)).click();
		driver.findElement(By.xpath(XPathInputBanco)).click();
	}

	public boolean isBancoSeleccionado(String nombreBanco) {
		String xpath = getXPathSelectOptionBanco(nombreBanco);
		return (state(Visible, By.xpath(xpath), driver).check());
	}

}