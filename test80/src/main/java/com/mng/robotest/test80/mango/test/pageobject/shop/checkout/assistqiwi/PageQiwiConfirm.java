package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageQiwiConfirm extends WebdrvWrapp {

    static String XPathButtonConfirmar = "//input[@name[contains(.,'Submit_Success')]]"; 
    
    /**
     * @return si estamos en la página de confirmación de Qiwi (aparece a veces después de la introducción del teléfono + botón continuar)
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonConfirmar)));
    }
    
    public static void clickConfirmar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfirmar));
    }
}
