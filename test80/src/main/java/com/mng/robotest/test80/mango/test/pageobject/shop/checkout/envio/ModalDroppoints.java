package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class ModalDroppoints extends WebdrvWrapp {
    
    public static SecSelectDPoint secSelectDPoint;
    public static SecConfirmDatos secConfirmDatos;
    
    static String XPathPanelGeneralDesktop = "//span[@id[contains(.,'panelDroppointsGeneral')]]";
    static String XPathPanelGeneralMovil = "//span[@id[contains(.,'panelDroppointsMenuMobile')]]";
    static String XPathMsgCargando = "//div[@class='loading-panel']";
    static String XPathErrorMessage = "//div[@class='errorNotFound']";
    static String XPathCpInputBox = "//div[@class='list__container']//input[@class[contains(.,searchBoxInput)]]";
    
    public static String getXPathPanelGeneral(Channel channel) {
        switch (channel) {
        case desktop:
            return XPathPanelGeneralDesktop;
        default:
        case movil_web:
            return XPathPanelGeneralMovil;
        }
    }
    
    public static boolean isVisible(Channel channel, WebDriver driver) {
        return isVisibleUntil(0, channel, driver);
    }
    
    public static boolean isVisibleUntil(int maxSecondsToWait, Channel channel, WebDriver driver) {
        String xpathPanelGeneral = getXPathPanelGeneral(channel);
        return (isElementVisibleUntil(driver, By.xpath(xpathPanelGeneral), maxSecondsToWait));
    }
    
    public static boolean isInvisibleCargandoMsgUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathMsgCargando), maxSecondsToWait));
    }
    

	public static boolean isErrorMessageVisibleUntil(WebDriver driver) {
		return isElementVisibleUntil(driver, By.xpath(XPathErrorMessage), 2);
	}

	public static void searchAgainByUserCp(String cp, WebDriver driver) {
		driver.findElement(By.xpath(XPathCpInputBox)).clear();
		driver.findElement(By.xpath(XPathCpInputBox)).sendKeys(cp);
		driver.findElement(By.xpath(XPathCpInputBox)).sendKeys(Keys.ENTER);
	}
}