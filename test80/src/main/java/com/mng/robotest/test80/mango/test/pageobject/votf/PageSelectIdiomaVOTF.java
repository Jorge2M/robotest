package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.data.CodIdioma;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSelectIdiomaVOTF {

    private static final String XPathSelectIdioma = "//select[@name[contains(.,'country')]]";
    private static final String XPathButtonAceptar = "//span[@class[contains(.,'button submit')]]";
    
    public static void selectIdioma(CodIdioma codigoIdioma, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectIdioma))).selectByValue(codigoIdioma.toString());
    }

    public static void clickButtonAceptar(WebDriver driver) {
    	click(By.xpath(XPathButtonAceptar), driver).exec();
    	
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleButtonAceptar(driver)) {
        	click(By.xpath(XPathButtonAceptar), driver).exec();
        }
    }
    
    public static boolean isVisibleButtonAceptar(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathButtonAceptar), driver).check());
    }
}
