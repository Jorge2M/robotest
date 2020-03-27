package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalLoyaltyAfterAccess {

	final static String XPathCapaGlobal = "//div[@id='adhesionModal']"; 
	final static String XPathCapaContainer = XPathCapaGlobal + "//div[@class='modal-container']";
	final static String XPathAspaForClose = XPathCapaContainer + "//span[@class='modal-close-icon']";
	
	public static boolean isModalVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathCapaContainer), driver).wait(maxSeconds).check());
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
