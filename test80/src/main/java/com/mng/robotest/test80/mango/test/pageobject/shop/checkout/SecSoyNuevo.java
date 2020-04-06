package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSoyNuevo {
	
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
            if (active) {
                sufix = " on";
            }
            return ("//div[@class[contains(.,'subscribe__checkbox" + sufix + "')]]");
        }
        
        if (active) {
            sufix = "active";
        }
        return ("//div[@id='publicidad']//div[@class[contains(.,'checkbox__image')] and @class[contains(.,'" + sufix + "')]]");
    }
    
    public static String getXPath_BotonContinue(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathBotonContinueMobil;
        }
        return XPathBotonContinueDesktop;
    }

	public static boolean isFormIdentUntil(WebDriver driver, int maxSeconds) { 
		return (state(Present, By.xpath(XPathFormIdent), driver).wait(maxSeconds).check());
	}

	public static boolean isCheckedPubliNewsletter(WebDriver driver, Channel channel) {
		String xpathCheckActive = getXPath_checkPubliNewsletter(channel, true/*active*/);
		return (state(Present, By.xpath(xpathCheckActive), driver).check());
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
		sendKeysWithRetry(email, By.xpath(XPathInputEmail), 3, driver);
	}

	public static void clickContinue(Channel channel, WebDriver driver) {
		String xpathButton = getXPath_BotonContinue(channel);
		click(By.xpath(xpathButton), driver).type(TypeClick.javascript).exec();
	}

	public static boolean isTextoRGPDVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTextRGPD), driver).check());
	}

	public static boolean isTextoLegalRGPDVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathLegalRGPD), driver).check());
	}
}
