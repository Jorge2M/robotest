package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


public class SSecSelTallasFichaNew extends WebdrvWrapp {
	static String XPathCapaTallas = "//form/div[@class='sizes']";
	static String XPathSelectorTallas = XPathCapaTallas + "/div[@class='selector']";
    static String XPathListTallsForSelect = XPathSelectorTallas + "//div[@class[contains(.,'selector-list')]]";
    static String XPathTallaItem = XPathCapaTallas + "//span[(@role='option' or @role='button') and not(@data-available='false')]";
    static String XPathTallaAvailable = XPathTallaItem + "//self::span[@data-available='true' or @class='single-size']";
    static String XPathTallaUnavailable = XPathTallaItem + "//self::span[not(@data-available) and not(@class='single-size')]";
    static String XPathTallaSelected = XPathTallaItem + "//self::span[@class[contains(.,'selector-trigger')] or @class='single-size']";
    static String XPathTallaUnica = XPathTallaItem + "//self::span[@class[contains(.,'single-size')]]";
    
    public static String getXPathTallaByCodigo(String codigoNumericoTalla) {
        return (XPathTallaItem + "//self::span[@data-value='" + codigoNumericoTalla + "']"); 
    }
    
    public static String getXPathTallaAvailable(String talla) {
    	return (XPathTallaAvailable + "//::*[text()[contains(.,'" + talla + "')]]");
    }

    public static boolean isVisibleSelectorTallasUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathSelectorTallas), maxSecondsToWait));
    }
    
    public static String getTallaAlfSelected(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathTallaSelected))) {
            if (isTallaUnica(driver)) {
               return "unitalla";
            }
            
            String textTalla = driver.findElement(By.xpath(XPathTallaSelected)).getText();
            if (textTalla.contains(" [")) {
                return (textTalla.substring(0, textTalla.indexOf(" [")));
            }
            return textTalla;
        }
        
        return "";
    }
    
    public static String getTallaNumSelected(AppEcom app, WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathTallaSelected))) {
            if (isElementPresent(driver, By.xpath(XPathTallaUnica))) {
                return (UtilsTestMango.getCodigoTallaUnica(app));
            }
            return (driver.findElement(By.xpath(XPathTallaSelected)).getAttribute("data-value"));
        }    
        
        return "0";
    }
    
    public static String getTallaAlf(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
    	if (isElementPresent(driver, By.xpath(xpathTalla))) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("innerHTML"));
    	}
    	return "";
    }
    
    public static String getTallaCodNum(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
    	if (isElementPresent(driver, By.xpath(xpathTalla))) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("data-value"));
    	}
    	return "";
    }

    public static boolean isTallaUnica(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTallaUnica)));
    }
    
    public static boolean isTallaSelected(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTallaSelected)));
    }
    
    public static boolean unfoldListTallasIfNotYet(WebDriver driver) {
        if (!isElementVisible(driver, By.xpath(XPathListTallsForSelect))) {
            //En el caso de talla única no existe XPathSelectorTallas
            if (isElementVisible(driver, By.xpath(XPathSelectorTallas))) {
                driver.findElement(By.xpath(XPathSelectorTallas)).click();
            } else {
                return true;
            }
            return (isVisibleListTallasForSelectUntil(1, driver));
        }
        
        return true;
    }
    
    public static boolean isVisibleListTallasForSelectUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathListTallsForSelect), maxSecondsToWait));
    }
    
    public static void selectTallaByValue(String codigoNumericoTalla, WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        String xpathTalla = getXPathTallaByCodigo(codigoNumericoTalla);
        if (isElementClickable(driver, By.xpath(xpathTalla))) {
            driver.findElement(By.xpath(xpathTalla)).click();
        }
    }
    
    public static void selectTallaByIndex(int posicion, WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        String xpathTallaByPos = "(" + XPathTallaItem + ")[" + posicion + "]";
        if (isElementClickable(driver, By.xpath(xpathTallaByPos))) {
            driver.findElement(By.xpath(xpathTallaByPos)).click();
        }
    }
    
    public static void selectFirstTallaAvailable(WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        if (isElementClickable(driver, By.xpath(XPathTallaAvailable))) {
            driver.findElement(By.xpath(XPathTallaAvailable)).click();
        }
    }
    
    public static int getNumOptionsTallasNoDisponibles(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathTallaUnavailable)).size());
    }    
    
    public static boolean isTallaAvailable(String talla, WebDriver driver) {
    	String xpathTalla = getXPathTallaAvailable(talla);
    	return (isElementPresent(driver, By.xpath(xpathTalla)));
    }
    
    public static int getNumOptionsTallas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathTallaItem)).size());
    }    
}
