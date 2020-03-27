package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMercpago1rst {

	static String XPathInputNumTarjeta = "//input[@id='cardNumber']";
    static String XPathLinkRegistro = "//a[@href[contains(.,'changeGuestMail')]]";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputNumTarjeta), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void clickLinkRegistro(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkRegistro));
    }
}
