package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class PageKoCardAdyen extends WebdrvWrapp {

    private final static String XPathIconKoreanCreditCard = "//input[@name='brandName']";
    private final static String XPathGreenButton = "//input[@name='pay']";
    
    public static boolean isPage(WebDriver driver) {
        return (isIconVisible(driver));
    }

	public static boolean isIconVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathIconKoreanCreditCard));
	}

	public static void clickForContinue(Channel channel, WebDriver driver) throws Exception {
		switch (channel) {
		case movil_web:
			clickIcon(driver);
			break;
		case desktop:
			clickGreenButton(driver);
		}
	}
	
	private static void clickIcon(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathIconKoreanCreditCard));
	}
	
	private static void clickGreenButton(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathGreenButton));
	}
}
