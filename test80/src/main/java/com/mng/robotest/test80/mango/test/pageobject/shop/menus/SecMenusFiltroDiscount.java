package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecMenusFiltroDiscount extends PageObjTM {
	
    public enum TypeMenuDiscount {UpTo50, UpTo60, Between50y60, off60, From60, From70}
    static String XPathDivMenus = "//nav[@id='descuentoFilter']";
    
    private SecMenusFiltroDiscount(WebDriver driver) {
    	super(driver);
    }

    public static SecMenusFiltroDiscount getNew(WebDriver driver) {
    	return (new SecMenusFiltroDiscount(driver));
    }

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
    
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathDivMenus)).check());
    }
    
    public boolean isVisibleMenu(String typeMenu) {
    	return (isVisibleMenu(TypeMenuDiscount.valueOf(typeMenu)));
    }
    
    public boolean isVisibleMenu(TypeMenuDiscount typeMenu) {
        String xpathMenu = getXPathMenu(typeMenu);
        return (state(Visible, By.xpath(xpathMenu)).check());
    }
    
    public int getNumberOfVisibleMenus() {
    	int numberMenusVisibles = 0;
    	for (TypeMenuDiscount typeMenu : TypeMenuDiscount.values()) {
    		if (isVisibleMenu(typeMenu)) {
    			numberMenusVisibles+=1;
    		}
    	}
    	
    	return numberMenusVisibles;
    }
    
    public void click(String typeMenu) throws Exception {
    	click(TypeMenuDiscount.valueOf(typeMenu));
    }
    
    public void click(TypeMenuDiscount typeMenu) throws Exception {
    	String xpathMenu = getXPathMenu(typeMenu);
    	clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }
}
