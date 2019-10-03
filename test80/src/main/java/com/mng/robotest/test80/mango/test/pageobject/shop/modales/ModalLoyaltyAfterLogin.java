package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class ModalLoyaltyAfterLogin extends WebdrvWrapp {

	final static String XPathCapaContainer = "//div[@class[contains(.,'modal-content')]]";
	final static String XPathIrDeShoppingLink = XPathCapaContainer + "//a[@class[contains(.,'loyalty-irdeshopping')]]";
	
	public static boolean isModalVisibleUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathIrDeShoppingLink), maxSecondsToWait));
	}
	
	public static void closeModal(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathIrDeShoppingLink));
	}
	
	public static void closeModalIfVisible(WebDriver driver) throws Exception {
		closeModalIfVisibleUntil(0, driver);
	}
	
	public static void closeModalIfVisibleUntil(int maxSecondsToWait, WebDriver driver) throws Exception {
		if (isModalVisibleUntil(maxSecondsToWait, driver)) {
			closeModal(driver);
		}
	}
}
