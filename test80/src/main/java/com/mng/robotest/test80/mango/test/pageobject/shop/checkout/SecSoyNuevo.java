package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecSoyNuevo extends WebdrvWrapp {
    public enum ActionNewsL {activate, deactivate}

    static String XPathFormIdent = "//form[@id='SVLoginCheck:FRegLogChk']";
    static String XPathInputEmail = "//input[@id[contains(.,'expMail')]]";
    static String XPathBotonContinueMobil = "//div[@id='registerCheckOut']//div[@class='submit']/a";
    static String XPathBotonContinueDesktop = "//div[@class='register']//div[@class='submit']/input";
    private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    
    public static String getXPath_checkPubliNewsletter(Channel channel, boolean active) {
        String sufix = "";
        if (channel==Channel.movil_web) {
            if (active)
                sufix = " on";
                
            return ("//div[@class[contains(.,'subscribe__checkbox" + sufix + "')]]");
        }
        
        if (active)
            sufix = "active";
            
        return ("//div[@id='publicidad']//div[@class[contains(.,'checkbox__image')] and @class[contains(.,'" + sufix + "')]]");
    }
    
    public static String getXPath_BotonContinue(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathBotonContinueMobil;
        
        return XPathBotonContinueDesktop;
    }
    
    public static boolean isFormIdentUntil(WebDriver driver, int secondsWait) { 
        return (isElementPresentUntil(driver, By.xpath(XPathFormIdent), secondsWait));
    }
    
    public static boolean isCheckedPubliNewsletter(WebDriver driver, Channel channel) {
        String xpathCheckActive = getXPath_checkPubliNewsletter(channel, true/*active*/);
        if (isElementPresent(driver, By.xpath(xpathCheckActive)))
            return true;
         
        return false;
    }
    
    /**
     * Marca/desmarca el check de la publicidad (Newsletter)
     */
    public static void setCheckPubliNewsletter(WebDriver driver, ActionNewsL action, Channel channel) {
        boolean isActivated = isCheckedPubliNewsletter(driver, channel);
        switch (action) {
        case activate:
            if (!isActivated) {
                String xpathCheck = getXPath_checkPubliNewsletter(channel, false/*active*/);
                driver.findElement(By.xpath(xpathCheck)).click();
            }
            break;
        case deactivate:
            if (isActivated) {
                String xpathCheck = getXPath_checkPubliNewsletter(channel, true/*active*/);
                driver.findElement(By.xpath(xpathCheck)).click();
            }
            break;
        default:
            break;
        }
    }
    
    public static void inputEmail(String email, WebDriver driver) {
        sendKeysWithRetry(3, email, By.xpath(XPathInputEmail), driver);
    }
    
    public static void clickContinue(Channel channel, WebDriver driver) throws Exception {
        String xpathButton = getXPath_BotonContinue(channel);
        clickAndWaitLoad(driver, By.xpath(xpathButton), TypeOfClick.javascript);
        //driver.findElement(By.xpath(xpathButton)).click();
    }   
    
	public static boolean isTextoRGPDVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathTextRGPD));
	}

	public static boolean isTextoLegalRGPDVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathLegalRGPD));
	}
}
