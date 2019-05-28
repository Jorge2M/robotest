package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class ModalLoyaltyAfterAccess extends WebdrvWrapp {

	final static String XPathCapaGlobal = "//div[@id='mngLoyalty']"; 
	final static String XPathCapaContainer = XPathCapaGlobal + "//div[@class='modal-container']";
	final static String XPathAspaForClose = XPathCapaContainer + "//span[@class='modal-close-icon']";
	
	public static boolean isModalVisibleUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathCapaContainer), maxSecondsToWait));
	}
	
	public static void closeModal(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathAspaForClose), TypeOfClick.javascript);
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
