package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

/**
 * Modal para compartir el enlace que aparece al seleccionar el link "Compartir" de la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModCompartirNew extends WebdrvWrapp {

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
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathWrapper), maxSecondsToWait));
    }
    
    public static boolean isVisibleIcon(IconSocial icon, WebDriver driver) {
        String xpathIcon = getXPathIcon(icon);
        return (isElementVisible(driver, By.xpath(xpathIcon)));
    }
}
