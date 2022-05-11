package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalNewsLetterAfterAccess {

	final static String XPathCapaGlobal = "//div[@id='listenerModal']"; 
	final static String XPathCapaContainer = XPathCapaGlobal + "//div[@id='modalNewsletterSubscription']";
	final static String XPathAspaForClose = XPathCapaContainer + "//button[@id='modalNewsletterSubscriptionClose']";
	
	public static boolean isModalVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathCapaContainer), driver).wait(maxSeconds).check());
	}
	
	public static void closeModal(WebDriver driver) {
		click(By.xpath(XPathAspaForClose), driver).type(javascript).exec();
	}
	
	public static void closeModalIfVisible(WebDriver driver) {
		closeModalIfVisibleUntil(0, driver);
	}
	
	public static void closeModalIfVisibleUntil(int maxSecondsToWait, WebDriver driver) {
		if (isModalVisibleUntil(maxSecondsToWait, driver)) {
			closeModal(driver);
		}
	}
}
