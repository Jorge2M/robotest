package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class PageKoCardAdyenMobil extends WebdrvWrapp {

    private static String XPathIconKoreanCreditCard = "//input[@name='brandName']";
    
    public static boolean isPage(WebDriver driver) {
        return (isIconVisible(driver));
    }

	public static boolean isIconVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathIconKoreanCreditCard));
	}

	public static void clickIconForContinue(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathIconKoreanCreditCard));
	}
}
