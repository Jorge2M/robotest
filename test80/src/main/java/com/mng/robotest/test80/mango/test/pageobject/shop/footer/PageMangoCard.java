package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMangoCard extends PageObjTM implements PageFromFooter {
	
	static String XPathGoMangoCardButton = "//span[@class='menu-link-button']";
	static String XPathGoMangoCardButtonMobile = "//a[@id='formLink']";
    static String XPathLinkSolMangoCardPage1 = "//button[@class[contains(.,'form-submit')]]";
     
    static String XPathNameField = "//input[@id='datNombre']";
    static String XPathFirstSurnameField = "//input[@id='datApellido1']";
    static String XPathSecondSurnameField = "//input[@id='datApellido2']";
    static String XPathMobileField = "//input[@id='datTelMovil']";
    static String XPathMailField = "//input[@id='datEmail']";
	
	final String XPathForIdPage = "//h2[@class='section-title']";
	
	public PageMangoCard(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Nueva Mango Card";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
	
	/**
     * Selecciona el botón "Solicitar Tarjeta Mango" de la página 1
     */
    public void clickOnWantMangoCardNow(Channel channel) throws Exception {
    	if (channel==Channel.movil_web) {
    		clickAndWaitLoad(driver, By.xpath(XPathGoMangoCardButtonMobile));
    	} else {
    		clickAndWaitLoad(driver, By.xpath(XPathGoMangoCardButton));
    	}
    }
    
    public void clickToGoSecondMangoCardPage() throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathLinkSolMangoCardPage1));
    }

    public boolean isPresentNameField() throws Exception {
    	return (state(Present, By.xpath(XPathNameField)).check());
    }
    
    public boolean isPresentFirstSurnameField() throws Exception {
    	return (state(Present, By.xpath(XPathFirstSurnameField)).check());
    }
    
    public boolean isPresentSecondSurnameField() throws Exception {
    	return (state(Present, By.xpath(XPathSecondSurnameField)).check());
    }
    
    public boolean isPresentMobileField() throws Exception {
    	return (state(Present, By.xpath(XPathMobileField)).check());
    }
    
    public boolean isPresentMailField() throws Exception {
    	return (state(Present, By.xpath(XPathMailField)).check());
    }
    
    public boolean isPresentButtonSolMangoCardNow() throws Exception {
    	return (state(Present, By.xpath(XPathLinkSolMangoCardPage1)).check());
    }
}
