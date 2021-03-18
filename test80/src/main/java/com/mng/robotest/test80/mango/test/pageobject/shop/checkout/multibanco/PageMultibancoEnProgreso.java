package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMultibancoEnProgreso {
    
    static String XPathCabeceraEnProgreso = "//h2[text()[contains(.,'Pagamento em progresso')]]";
    static String XPathButtonNextStep = "//input[@id='mainSubmit' and @type='submit']";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(XPathCabeceraEnProgreso), driver)
    			.wait(maxSeconds).check());
    }
    
    public static boolean isButonNextStep(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonNextStep), driver).check());
    }

	public static void clickButtonNextStep(WebDriver driver) {
		click(By.xpath(XPathButtonNextStep), driver).exec();
	}
}
