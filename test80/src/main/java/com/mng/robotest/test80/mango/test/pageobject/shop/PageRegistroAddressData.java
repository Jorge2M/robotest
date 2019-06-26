package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


/**
 * Clase para operar con la página de registro en la que se introducen los datos de la dirección del usuario
 * @author jorge.munoz
 *
 */
public class PageRegistroAddressData extends WebdrvWrapp {

    static String XPathTitleAddressSteps = "//div[@class[contains(.,'addressData')]]";
    static String XPathDesplegablePaises = "//select[@id[contains(.,'pais')]]";
    static String XPathButtonFinalizar = "//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
    static String XPathErrorInputDireccion = "//div[@id[contains(.,'cfDir1ErrorLabel')]]";    
  
    public static String getXPath_optionPaisSelected(String codigoPais) {
        return ("//select[@id[contains(.,'pais')]]/option[@selected='selected' and @value='" + codigoPais + "']");
    }
    
    /**
     * @return si se trata de la página en cuestión
     */
    public static boolean isPageUntil(WebDriver driver, int secondsWait) {
        return (isElementPresentUntil(driver, By.xpath(XPathTitleAddressSteps), secondsWait));
    }
    
    public static boolean existsDesplegablePaises(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathDesplegablePaises)));
    }
    
    public static boolean isOptionPaisSelected(WebDriver driver, String codigoPais) {
        String xpathOptionPaisSelected = getXPath_optionPaisSelected(codigoPais);
        return (isElementPresent(driver, By.xpath(xpathOptionPaisSelected)));
    }
    
    public static void clickButtonFinalizar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonFinalizar));
    }
    
    public static boolean isVisibleErrorInputDireccion(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathErrorInputDireccion)));
    }
}
