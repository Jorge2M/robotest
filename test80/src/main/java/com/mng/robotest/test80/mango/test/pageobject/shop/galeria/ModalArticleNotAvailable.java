package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalArticleNotAvailable {

    public enum StateModal {visible, notvisible}    
    static String XPathModal = "//div[@id='bocataAvisame']";
    static String XPathAspaForClose = XPathModal + "//div[@class[contains(.,'botonCerrarAvisame')]]";
    static String XPathRPGDText = XPathModal + "//p[@class='gdpr-text gdpr-data-protection']";
    
    public static boolean inStateUntil(StateModal stateModal, int maxSecondsToWait, WebDriver driver) throws Exception {
        switch (stateModal) {
        case visible:
            return isVisibleUntil(maxSecondsToWait, driver);
        default:
        case notvisible:
            return isNotVisibleUntil(maxSecondsToWait, driver);
        }
    }
    
    public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathModal), driver).wait(maxSeconds).check());
    }
    
    public static boolean isNotVisibleUntil(int maxSeconds, WebDriver driver) {
    	return (state(Invisible, By.xpath(XPathModal), driver)
    			.wait(maxSeconds).check());
//    	By byAvisame = By.xpath(XPathModal);
//    	for (int i=0; i<maxSecondsToWait; i++) {
//    		if (isElementPresent(driver, byAvisame)) {
//    			if (!driver.findElement(byAvisame).isDisplayed()) {
//    				return true;
//    			}
//    		}
//    		Thread.sleep(1000);
//    	}
//    	return false;
    }

	public static boolean isVisibleRPGD(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathModal), driver).wait(maxSeconds).check());
	}

	public static void clickAspaForClose(WebDriver driver) {
		driver.findElement(By.xpath(XPathAspaForClose)).click();
	}
}
