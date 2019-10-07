package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModNoStock extends WebdrvWrapp {

    static String XPathModalNoStock = "//div[@class='modalNoStock show']";
    
    public static boolean isModalNoStockVisibleFichaNew(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathModalNoStock), maxSecondsToWait));
	}
    
}
