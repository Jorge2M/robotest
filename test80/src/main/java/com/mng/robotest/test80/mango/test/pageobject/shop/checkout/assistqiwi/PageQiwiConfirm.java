package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageQiwiConfirm {

    static String XPathButtonConfirmar = "//input[@name[contains(.,'Submit_Success')]]"; 
    
    /**
     * @return si estamos en la página de confirmación de Qiwi (aparece a veces después de la introducción del teléfono + botón continuar)
     */
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonConfirmar), driver).check());
    }

	public static void clickConfirmar(WebDriver driver) {
		click(By.xpath(XPathButtonConfirmar), driver).exec();
	}
}
