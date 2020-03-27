package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAssistQiwi1rst {

    public enum pasarelasAssist { visa, webmoney, Яндекс, qiwikошелек, qiwimts, qiwimegafon }
    
    static String XPathIconoMobil = "//img[@src[contains(.,'mobile/lmob.png')]]";
    static String XPathIconoDesktop = "//img[@src[contains(.,'assist_logo.png')]]";
    static String XPathIconoPasarelasMobil = "//form//div[@class='btt']"; 
    static String XPathIconoPasarelasDesktop = "//form/div[@class='tip']";
    
    public static String getXPATH_icono(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathIconoMobil;
        }
        return XPathIconoDesktop;
    }
        
    public static String getXPATH_iconPasarelas(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathIconoPasarelasMobil;
        }
        return XPathIconoPasarelasDesktop;
    }
    
    /**
     * @return el input correspondiente a un icono concreto
     */
    public static String getXPATH_iconPasarela(Channel channel, pasarelasAssist pasarela) {
        String valueInput = "";
        switch (pasarela) {
        case visa:
            valueInput = "100:1";
            break;
        case webmoney:
            valueInput = "30:2";
            break;
        case Яндекс:
            valueInput = "32:2";
            break;
        case qiwikошелек:
            valueInput = "36:2";
            break;
        case qiwimts:
            valueInput = "40:2";
            break;
        case qiwimegafon:
            valueInput = "41:2";
            break;
        default:
            break;
        }
        
        return (getXPATH_iconPasarelas(channel) + "/input[@value='" + valueInput + "']");
    }
    
    
    public static boolean isPresentIconoAssist(WebDriver driver, Channel channel) {
    	String xpath = getXPATH_icono(channel);
    	return (state(Present, By.xpath(xpath), driver).check());
    }
    
    public static boolean isPresentIconPasarelas(WebDriver driver, Channel channel) {
    	String xpath = getXPATH_iconPasarelas(channel);
    	return (state(Present, By.xpath(xpath), driver).check());
    }
    
    public static void clickIconPasarela(WebDriver driver, Channel channel, pasarelasAssist pasarela) throws Exception {
        
        String xpath = getXPATH_iconPasarela(channel, pasarela);
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
}
