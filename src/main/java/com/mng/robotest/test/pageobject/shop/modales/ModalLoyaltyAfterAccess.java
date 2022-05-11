package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalLoyaltyAfterAccess {

	final static String XPathCapaGlobal = "//div[@id='adhesionModal']"; 
	final static String XPathCapaContainer = XPathCapaGlobal + "//div[@class='modal-container']";
	final static String XPathAspaForClose = XPathCapaContainer + "//span[@class='modal-close-icon']";
	
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
