package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.SeleniumUtils;


/**
 * Clase para operar con la página inicial de la shop de Japón vía API de driver
 * @author jorge.munoz
 *
 */
public class PageIniShopJapon extends SeleniumUtils {

    public static String URL = "japan.mango.com";
    public static String Title = "MANGO - マンゴ公式オンラインストア";
    /**
     * @param driver
     * @return si se trata o no de la página inicial de la shop de Japón
     */
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (
        	titleContainsUntil(driver, Title, maxSecondsToWait) &&
        	driver.getCurrentUrl().contains(URL));
    }
}
