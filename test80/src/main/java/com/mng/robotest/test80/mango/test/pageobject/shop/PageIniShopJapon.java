package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


/**
 * Clase para operar con la página inicial de la shop de Japón vía API de driver
 * @author jorge.munoz
 *
 */
public class PageIniShopJapon extends WebdrvWrapp {

    public static String URL = "japan.mango.com";
    public static String Title = "MANGO - マンゴ公式オンラインストア";
    /**
     * @param driver
     * @return si se trata o no de la página inicial de la shop de Japón
     */
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        //Comprobamos la URL y el título
        if (titleContainsUntil(driver, Title, maxSecondsToWait) &&
        	driver.getCurrentUrl().contains(URL))
            return true;
        
        return false;
    }
}
