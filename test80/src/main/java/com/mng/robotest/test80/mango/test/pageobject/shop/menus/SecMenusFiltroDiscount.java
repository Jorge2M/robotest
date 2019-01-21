package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecMenusFiltroDiscount extends WebdrvWrapp {
    public enum TypeMenuDiscount {UpTo50, UpTo60, Between50y60, off60, From60, From70}
    static String XPathDivMenus = "//nav[@id='descuentoFilter']";
    
    private static String getXPathMenu(TypeMenuDiscount typeMenu) {
        String valuesParamTemporada1 = "";
        switch (typeMenu) {
        case UpTo50:
        	valuesParamTemporada1 = "1/49";
            break;
        case UpTo60:
        	valuesParamTemporada1 = "1/59";
            break;
        case Between50y60:
        	valuesParamTemporada1 = "50/59";
        	break;
        case off60:
        	valuesParamTemporada1 = "60/69";
            break;            
        case From60:
        	valuesParamTemporada1 = "60/90";
            break;
        case From70:
        default:
        	valuesParamTemporada1 = "70/90";
            break;
        }        
        
        return (XPathDivMenus + "//li[@data-filter-value='" + valuesParamTemporada1 + "']");
    }
    
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathDivMenus)));
    }
    
    public static boolean isVisibleMenu(String typeMenu, WebDriver driver) {
    	return (isVisibleMenu(TypeMenuDiscount.valueOf(typeMenu), driver));
    }
    
    public static boolean isVisibleMenu(TypeMenuDiscount typeMenu, WebDriver driver) {
        String xpathMenu = getXPathMenu(typeMenu);
        return (isElementVisible(driver, By.xpath(xpathMenu)));
    }
    
    public static int getNumberOfVisibleMenus(WebDriver driver) {
    	int numberMenusVisibles = 0;
    	for (TypeMenuDiscount typeMenu : TypeMenuDiscount.values()) {
    		if (isVisibleMenu(typeMenu, driver))
    			numberMenusVisibles+=1;
    	}
    	
    	return numberMenusVisibles;
    }
    
    public static void click(String typeMenu, WebDriver driver) throws Exception {
    	click(TypeMenuDiscount.valueOf(typeMenu), driver);
    }
    
    public static void click(TypeMenuDiscount typeMenu, WebDriver driver) throws Exception {
    	String xpathMenu = getXPathMenu(typeMenu);
    	clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }
}
