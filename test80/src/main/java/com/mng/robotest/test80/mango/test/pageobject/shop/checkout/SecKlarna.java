package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class SecKlarna extends WebdrvWrapp {

    //Parte del error que aparece cuando se introduce un teléfono incorrecto desde Desktop
    public static final String errorTlfDesktop = "The telephone number you submitted is not in the correct format";
    
    //Parte del error que aparece cuando se introduce un teléfono incorrecto desde Móvil
    public static final String errorTlfMovil   = "The mobile phone / cell phone number you submitted is not formatted correctly";
    
    static String XPathButtonSearchAddress = "//*[@id[contains(.,'btnKlarnaConfirmar')]]";
    static String XPathModalDirecciones = "//div[@class[contains(.,'modalDireccionesKlarna')]]";
    static String XPathNombreAddress = "//div[@class[contains(.,'klarnaAddressNombre')]]";
    static String XPathDireccionAddress = "//div[@class[contains(.,'klarnaAddressDireccion')]]";
    static String XPathProvinciaAddress = "//div[@class[contains(.,'klarnaAddressProvincia')]]";
    static String XPathCapaKlarnaMobil = "//div[@class[contains(.,'klarna')] and @class[contains(.,'show')]]";
    static String XPathCapaKlarnaDesktop = "//div[@class[contains(.,'klarnaInput')]]";
    static String XPathButtonConfirmAddressMobil = "//span[@id[contains(.,'formDireccionesKlarna')] and @class[contains(.,'modalConfirmar')]]"; 
    static String XPathButtonConfirmAddressDesktop = "//span[@id[contains(.,'FormularioKlarna')] and @class[contains(.,'modalConfirmar')]]";
    
    public static String getXPath_capaKlarna(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathCapaKlarnaMobil;
        }
        return XPathCapaKlarnaDesktop; 
    }
    
    public static String getXPath_inputNumPersonal(Channel channel) {
        String xpathCapaKlarna = getXPath_capaKlarna(channel);
        if (channel==Channel.movil_web) {
            return (xpathCapaKlarna + "//input[@id[contains(.,'number-card')]]");
        }
        return (xpathCapaKlarna + "//input[@id[contains(.,'personalno')] and @class[contains(.,'personalno-input')]]");
    }
    
    public static String getXPATH_buttonConfirmAddress(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathButtonConfirmAddressMobil; 
        }
        return XPathButtonConfirmAddressDesktop;
    }
    
    public static boolean isVisibleUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(getXPath_capaKlarna(channel)), maxSecondsToWait));
    }
    
    /**
     * Espera un determinado número de segundos a que esté disponible el input y posteriormente introduce el número personal
     */
    public static void waitAndinputNumPersonal(WebDriver driver, int secondsWait, String numPerKlarna, Channel channel) {
        String xpathInput = getXPath_inputNumPersonal(channel);
        new WebDriverWait(driver, secondsWait).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathInput)));
        driver.findElement(By.xpath(xpathInput)).sendKeys(numPerKlarna);
    }
    
    /**
     * @return si un determinado mensaje es el correspondiente al mensaje de error por teléfono introducido con formato incorrecto
     */
    public static boolean isErrorTlfn(String mensajeError, Channel channel) {
        boolean isError = false;
        if (channel==Channel.movil_web) {
            if (mensajeError.contains(errorTlfMovil)) {
                isError = true;
            }
        } else {
            if (mensajeError.contains(errorTlfDesktop)) {
                isError = true;
            }
        }
        
        return isError;
    }
    
    /**
     * Selección del botón "Search Address" (se trata de un botón que aparece en algunos tipos de Klarna como p.e. el de Sweden)
     */
    public static void clickSearchAddress(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSearchAddress));
    }
    
    /**
     * @return indicador de si existe o no el modal de la confirmación de la dirección (aparece sólo en algunos tipos de Klarna como p.e. el de Sweden)
     */
    public static boolean isModalDireccionesVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathModalDirecciones), maxSecondsToWait));
    }
    
    public static boolean isModalDireccionesInvisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathModalDirecciones), maxSecondsToWait));
    }    
    
    public static String getTextNombreAddress(WebDriver driver) {
        return driver.findElement(By.xpath(XPathNombreAddress)).getText();
    }
    
    public static String getTextDireccionAddress(WebDriver driver) {
        return driver.findElement(By.xpath(XPathDireccionAddress)).getText();
    }
    
    public static String getTextProvinciaAddress(WebDriver driver) {
        return driver.findElement(By.xpath(XPathProvinciaAddress)).getText();
    }
    
    public static void clickConfirmAddress(WebDriver driver, Channel channel) throws Exception {
        clickAndWaitLoad(driver, By.xpath(getXPATH_buttonConfirmAddress(channel)));
    }
}
