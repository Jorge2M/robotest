package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


/**
 * Page1: página inicial de solicitud de la tarjeta (la que contiene el botón de "Solicitar Tarjeta Mango"
 * Modal: de aviso de trámite de la solicitud
 * Page2: página con los campos de introducción de datos
 * @author jorge.munoz
 *
 */
public class PageInputDataSolMangoCard extends WebdrvWrapp {
	
    static String XPathbotonContinuarModal = "//div//button[text()[contains(.,'Continuar')]]";
    static String XPathTextDatosPersonalesPage2 = "//div[@id='datospersonales']";
    static String XPathIsPage2 = "//div/h2[text()[contains(.,'Solicitud de tu MANGO Card')]]";
    static String XPathTextDatosBancariosPage2 = "//div/span[text()[contains(.,'Datos bancarios')]]";
    static String XPathDatosContactoPage2 = "//div/span[text()[contains(.,'Datos de contacto')]]";
    static String XPathDatosSocioeconomicosPage2 = "//div/span[text()[contains(.,'Datos socioeconómicos')]]";
    static String XPathModalidadPagoPage2 = "//div/span[text()[contains(.,'Modalidad de pago')]]";
    static String XPathButtonContinuarPage2 = "//button[@id='in-continuar']";
    
    /*
     * Funciones para la validación y interactuación con la segunda página de solicitud de tarjeta mango
     */
   
    public static boolean isPresentBotonContinuarModalUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathbotonContinuarModal), maxSecondsToWait));
    }
    
    public static void clickBotonCerrarModal(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathbotonContinuarModal));
    }
    
    public static boolean isPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathIsPage2)));
    }
    
    /**
     * Posicionarse en el iframe central de la página 2 con los datos para la solicitud de la tarjeta mango
     * @param driver
     */
    
    public static void gotoiFramePage2(WebDriver driver) {
        driver.switchTo().frame("ifcentral");
    }
    
    public static boolean isPresentDatosPersonalesPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTextDatosPersonalesPage2)));
    }
    
    public static boolean isPresentDatosBancariosPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTextDatosBancariosPage2)));
    }
    
    public static boolean isPresentDatosContactoPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathDatosContactoPage2)));
    }
        
    public static boolean isPresentDatosSocioeconomicosPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathDatosSocioeconomicosPage2)));
    }

    public static boolean isPresentModalidadpagoPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathModalidadPagoPage2)));
    }
    
    public static boolean isPresentButtonContinuarPage2(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonContinuarPage2)));
    }
    
}
