package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SSecSelTallasFichaNew {
	
	static String XPathCapaTallas = "//form/div[@class='sizes']";
	static String XPathSelectorTallas = XPathCapaTallas + "/div[@class='selector']";
    static String XPathListTallsForSelect = XPathSelectorTallas + "//div[@class[contains(.,'selector-list')]]";
    static String XPathTallaItem = XPathCapaTallas + "//span[(@role='option' or @role='button') and not(@data-available='false')]";
    static String XPathTallaAvailable = XPathTallaItem + "//self::span[@data-available='true' or @class='single-size']";
    static String XPathTallaUnavailable = XPathTallaItem + "//self::span[not(@data-available) and not(@class='single-size')]";
    static String XPathTallaSelected = XPathTallaItem + "//self::span[@class[contains(.,'selector-trigger')] or @class='single-size']";
    static String XPathTallaUnica = XPathTallaItem + "//self::span[@class[contains(.,'single-size')]]";
    
    public static String getXPathTallaByCodigo(int codigoNumericoTalla) {
        return (XPathTallaItem + "//self::span[@data-value='" + codigoNumericoTalla + "']"); 
    }
    
    public static String getXPathTallaAvailable(String talla) {
    	return (XPathTallaAvailable + "//::*[text()[contains(.,'" + talla + "')]]");
    }

    public static boolean isVisibleSelectorTallasUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathSelectorTallas), driver)
    			.wait(maxSeconds).check());
    }
    
    public static String getTallaAlfSelected(WebDriver driver) {
    	if (state(Present, By.xpath(XPathTallaSelected), driver).check()) {
            if (isTallaUnica(driver)) {
               return "unitalla";
            }
            
            String textTalla = driver.findElement(By.xpath(XPathTallaSelected)).getText();
            textTalla = SecDataProduct.removeAlmacenFromTalla(textTalla);
            return textTalla;
        }
        
        return "";
    }
    
    public static String getTallaAlf(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
    	if (state(Present, By.xpath(xpathTalla), driver).check()) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("innerHTML"));
    	}
    	return "";
    }
    
    public static String getTallaCodNum(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
    	if (state(Present, By.xpath(xpathTalla), driver).check()) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("data-value"));
    	}
    	return "";
    }

    public static boolean isTallaUnica(WebDriver driver) {
    	return (state(Present, By.xpath(XPathTallaUnica), driver).check());
    }
    
    public static boolean isTallaSelected(WebDriver driver) {
    	return (state(Present, By.xpath(XPathTallaSelected), driver).check());
    }
    
    public static boolean unfoldListTallasIfNotYet(WebDriver driver) {
    	if (!state(Visible, By.xpath(XPathListTallsForSelect), driver).check()) {
            //En el caso de talla única no existe XPathSelectorTallas
    		if (state(Visible, By.xpath(XPathSelectorTallas), driver).check()) {
                driver.findElement(By.xpath(XPathSelectorTallas)).click();
            } else {
                return true;
            }
            return (isVisibleListTallasForSelectUntil(1, driver));
        }
        
        return true;
    }
    
    public static boolean isVisibleListTallasForSelectUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathListTallsForSelect), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void selectTallaByValue(int codigoNumericoTalla, WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        String xpathTalla = getXPathTallaByCodigo(codigoNumericoTalla);
        if (state(Clickable, By.xpath(xpathTalla), driver).check()) {
            driver.findElement(By.xpath(xpathTalla)).click();
        }
    }
    
    public static void selectTallaByIndex(int posicion, WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        String xpathTallaByPos = "(" + XPathTallaItem + ")[" + posicion + "]";
        if (state(Clickable, By.xpath(xpathTallaByPos), driver).check()) {
            driver.findElement(By.xpath(xpathTallaByPos)).click();
        }
    }
    
    public static void selectFirstTallaAvailable(WebDriver driver) {
        unfoldListTallasIfNotYet(driver);
        if (state(Clickable, By.xpath(XPathTallaAvailable), driver).check()) {
            driver.findElement(By.xpath(XPathTallaAvailable)).click();
        }
    }
    
    public static int getNumOptionsTallasNoDisponibles(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathTallaUnavailable)).size());
    }    
    
    public static boolean isTallaAvailable(String talla, WebDriver driver) {
    	String xpathTalla = getXPathTallaAvailable(talla);
    	return (state(Present, By.xpath(xpathTalla), driver).check());
    }
    
    public static int getNumOptionsTallas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathTallaItem)).size());
    }    
}
