package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageMangoCard extends WebdrvWrapp implements PageFromFooter {
	
	static String XPathGoMangoCardButton = "//span[@class='menu-link-button']";
	static String XPathGoMangoCardButtonMobile = "//a[@id='formLink']";
    static String XPathLinkSolMangoCardPage1 = "//button[@class[contains(.,'form-submit')]]";
     
    static String XPathNameField = "//input[@id='datNombre']";
    static String XPathFirstSurnameField = "//input[@id='datApellido1']";
    static String XPathSecondSurnameField = "//input[@id='datApellido2']";
    static String XPathMobileField = "//input[@id='datTelMovil']";
    static String XPathMailField = "//input[@id='datEmail']";
	
	final String XPathForIdPage = "//h2[@class='section-title']";
	
	@Override
	public String getName() {
		return "Nueva Mango Card";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathForIdPage)));
	}
	
	/**
     * Selecciona el botón "Solicitar Tarjeta Mango" de la página 1
     */
    public static void clickOnWantMangoCardNow(WebDriver driver, Channel channel) throws Exception {
    	if (channel==Channel.movil_web) {
    		clickAndWaitLoad(driver, By.xpath(XPathGoMangoCardButtonMobile));
    	} else {
    		clickAndWaitLoad(driver, By.xpath(XPathGoMangoCardButton));
    	}
    }
    
    public static void clickToGoSecondMangoCardPage(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathLinkSolMangoCardPage1));
    }

    public static boolean isPresentNameField(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathNameField)));
    }
    
    public static boolean isPresentFirstSurnameField(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathFirstSurnameField)));
    }
    
    public static boolean isPresentSecondSurnameField(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathSecondSurnameField)));
    }
    
    public static boolean isPresentMobileField(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathMobileField)));
    }
    
    public static boolean isPresentMailField(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathMailField)));
    }
    
    public static boolean isPresentButtonSolMangoCardNow(WebDriver driver) throws Exception {
    	return  (isElementPresent(driver, By.xpath(XPathLinkSolMangoCardPage1)));
    }
}
