package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.data.CodigoIdioma;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageSelectIdiomaVOTF extends WebdrvWrapp {

    private static final String XPathSelectIdioma = "//select[@name[contains(.,'country')]]";
    private static final String XPathButtonAceptar = "//span[@class[contains(.,'button submit')]]";
    
    public static void selectIdioma(CodigoIdioma codigoIdioma, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectIdioma))).selectByValue(codigoIdioma.toString());
    }

    public static void clickButtonAceptar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonAceptar));
    	
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleButtonAceptar(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathButtonAceptar));
        }
    }
    
    public static boolean isVisibleButtonAceptar(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathButtonAceptar)));
    }
}
