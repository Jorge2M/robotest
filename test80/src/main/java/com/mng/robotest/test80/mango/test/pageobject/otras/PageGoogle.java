package com.mng.robotest.test80.mango.test.pageobject.otras;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageGoogle extends WebdrvWrapp {

    private static final String URLacceso = "http://www.google.es";
    static final String XPath_inputText = "//input[@type='text']";
    static final String XPath_linkNoPubli = "//div[@class='g']//a";
    static final String XPath_LinkNoPubliText = XPath_linkNoPubli + "//h3";
    
    public static void accessViaURL(WebDriver driver) {
        driver.get(URLacceso);
    }
    
    public static String getUrlAcceso() {
    	return URLacceso;
    }
    
    public static String getXPath_linkWithText(String textContained) {
        return (XPath_LinkNoPubliText + "[text()[contains(.,'" + textContained + "')]]");    
    }
    
    public static void searchTextAndWait(WebDriver driver, String textToSearch) throws Exception {
        driver.findElement(By.xpath(XPath_inputText)).clear(); 
        driver.findElement(By.xpath(XPath_inputText)).sendKeys(textToSearch); 
        driver.findElement(By.xpath(XPath_inputText)).sendKeys(Keys.RETURN);
        waitForPageLoaded(driver);
    }
    
    public static boolean validaFirstLinkContains(String textToBeContained, WebDriver driver) {
    	WebElement headerText = driver.findElement(By.xpath(XPath_LinkNoPubliText));
    	if (headerText!=null) {
    		String textHeader = headerText.getText();
    		if (textHeader.contains(textToBeContained) || 
    			textHeader.contains(textToBeContained.toLowerCase()) ||
    			textHeader.contains(textToBeContained.toUpperCase())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean validaFirstLinkContainsUntil(String textToBeContained, int maxSecondsToWait, WebDriver driver) {
    	for (int i=0; i<maxSecondsToWait; i++) {
    		if (validaFirstLinkContains(textToBeContained, driver)) {
    			return true;
    		}
    		waitMillis(1000);
    	}
    	return false;
    }
    
    public static void clickFirstLinkNoPubli(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPath_linkNoPubli));
    }

}
