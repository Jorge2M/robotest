package com.mng.robotest.test.pageobject.shop.checkout.eps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageEpsSimulador {

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
		return (state(Visible, By.xpath(XPathLogoEps), driver).check());
	}
	
	public static void selectDelayAuthorised(TypeDelay typeDelay, WebDriver driver) {
		String xpathOption = getXPathOptionDelayAuthorised(typeDelay);
		driver.findElement(By.xpath(xpathOption)).click();
	}

	public static void clickButtonContinue(WebDriver driver) {
		click(By.xpath(XPathContinueButton), driver).exec();
	}
}
