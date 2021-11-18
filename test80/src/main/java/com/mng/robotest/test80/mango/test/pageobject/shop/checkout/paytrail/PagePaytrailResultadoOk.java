package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;


public class PagePaytrailResultadoOk {
	
	static String aceptadoEnFinlandes = "hyväksytty";
	static String volverAlServicioDelVendedorEnFinlandes = "Palaa myyjän palveluun";
	
	public static String getXPathVolverAMangoButton() {
		return "//input[@class='button' and @value[contains(.,'" + volverAlServicioDelVendedorEnFinlandes + "')]]";
	}
	
	public static boolean isPage(WebDriver driver) {
		return (driver.getTitle().toLowerCase().contains(aceptadoEnFinlandes));
	}

	public static void clickVolverAMangoButton(WebDriver driver) {
		String xpathButton = getXPathVolverAMangoButton();
		click(By.xpath(xpathButton), driver).exec();
	}
}
