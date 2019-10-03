package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageEpsSimulador extends WebdrvWrapp {

	public enum TypeDelay {
		OneMinutes(1), 
		FiveMinutes(5), 
		FifteenMinutes(15), 
		SixtyMinutes(60);
		
		public int minutes;
		private TypeDelay(int minutes) {
			this.minutes = minutes;
		}
	};
	
    final static String XPathLogoEps = "//img[@class='paymentMethodLogo' and @src[contains(.,'eps.png')]]";
    final static String XPathContinueButton = "//button[@value='pendingauthorised']";
    final static String XPathSelectDelayAuthorised = "//select[@name='delaySelector']";
    static String getXPathOptionDelayAuthorised(TypeDelay typeDelay) {
    	return (XPathSelectDelayAuthorised + "//option[text()[contains(.,'" + typeDelay.minutes + " minute')]]");
    }
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathLogoEps)));
    }    
    
    public static void selectDelayAuthorised(TypeDelay typeDelay, WebDriver driver) {
    	String xpathOption = getXPathOptionDelayAuthorised(typeDelay);
    	driver.findElement(By.xpath(xpathOption)).click();
    }
    
    public static void clickButtonContinue(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
