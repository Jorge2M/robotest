package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


/**
 * Clase para operar con la página "Resultado de una búsqueda KO" a través de la API de driver
 * @author jorge.munoz
 *
 */
public class PageErrorBusqueda extends WebdrvWrapp {

    /**
     * @param textoBuscado
     * @return el xpath correspondiente a la cabecera de resultado de una búsqueda concreta
     */
    public static String getXPath_cabeceraBusquedaProd(String textoBuscado) {
        return ("//td/i/span[text()[contains(.,'" + textoBuscado + "')] or text()[contains(.,'" + textoBuscado.toLowerCase() + "')]]");
    }

    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath("//*[text()[contains(.,'Tu búsqueda...')]]")));
    }
    
    /**
     * @param driver
     * @param textoBuscado
     * @return si aparece la cabecera de resultado para el texto buscado concreto
     */
    public static boolean isCabeceraResBusqueda(WebDriver driver, String textoBuscado) {
        String xpathCabe = getXPath_cabeceraBusquedaProd(textoBuscado);
        return (isElementPresentUntil(driver, By.xpath(xpathCabe), 1));
    }
}
