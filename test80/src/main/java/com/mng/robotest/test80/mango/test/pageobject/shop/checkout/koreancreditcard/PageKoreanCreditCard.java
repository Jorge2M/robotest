package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class PageKoreanCreditCard extends WebdrvWrapp {

    private static String XPathSubmitButton = "//input[@class[contains(.,'paySubmit')]]";
    private static String XPathKoreanCreditCard = "//input[@name='brandName']";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains("E-payment"));
    }

	public static boolean isButtonSubmitVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathSubmitButton));
	}
	
	public static void clickButtonSubmit(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));
	}

	public static void clickButtonCreditCard(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathKoreanCreditCard));
	}
}
