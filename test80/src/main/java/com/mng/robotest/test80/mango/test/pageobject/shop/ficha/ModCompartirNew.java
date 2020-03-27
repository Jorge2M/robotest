package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal para compartir el enlace que aparece al seleccionar el link "Compartir" de la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModCompartirNew {

    //El valor es el que figura en el data-ga del ancor
    public enum IconSocial {
        whatsapp(false), 
        pinterest(false), 
        facebook(false), 
        twitter(false), 
        telegram(false), 
        mail(false), 
        weibo(true), 
        wechat(true);
        
        private final boolean isSpecificChina;
     
        IconSocial(boolean isSpecificChina) {
            this.isSpecificChina = isSpecificChina;
        }
        
        public boolean isSpecificChina() {
            return this.isSpecificChina;
        }        
    }
    
    static String XPathWrapper = "//div[@id='productSocial']";
    static String XPathLinkGaleria = "//span[@class='social-garment-link']";
    static String XPathIcon = "//a[@class='icon']";
    
    public static String getXPathIcon(IconSocial icon) {
        return (XPathIcon + "//self::*[@data-ga='" + icon + "']");
    }
    
    public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathWrapper), driver)
    			.wait(maxSeconds).check());
    }
    
    public static boolean isVisibleIcon(IconSocial icon, WebDriver driver) {
        String xpathIcon = getXPathIcon(icon);
        return (state(Visible, By.xpath(xpathIcon), driver).check());
    }
}
