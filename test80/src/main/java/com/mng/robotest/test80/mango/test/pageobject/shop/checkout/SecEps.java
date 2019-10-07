package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class SecEps extends WebdrvWrapp {

    
    static String XPathInputBanco = "//div[@id='eps-bank-selector']";
    static String iniXPathSelectOptionBanco = XPathInputBanco + "//option[text()[contains(.,'";
    
    public static String getXPathSelectOptionBanco(String banco) {
    	return iniXPathSelectOptionBanco + banco + "')]]";
    }

	public static void selectBanco(String nombreBanco, WebDriver driver) {
		String XPathSelectOptionBanco = getXPathSelectOptionBanco(nombreBanco);
		driver.findElement(By.xpath(XPathInputBanco)).click();
		driver.findElement(By.xpath(XPathSelectOptionBanco)).click();
		driver.findElement(By.xpath(XPathInputBanco)).click();
	}

	public static boolean isBancoSeleccionado(String nombreBanco, WebDriver driver) {
		return isElementVisible(driver, By.xpath(getXPathSelectOptionBanco(nombreBanco)));
	}

}