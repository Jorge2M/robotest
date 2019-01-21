package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageKoreanCreditCard extends WebdrvWrapp {

    private static String XPathSubmitButton = "//input[@class[contains(.,'paySubmit')]]";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains("E-payment"));
    }

	public static boolean isButtonSubmitVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathSubmitButton));
	}
	
	public static void clickButtonSubmit(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));
	}
}
