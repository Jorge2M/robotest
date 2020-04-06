package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalLoyaltyAfterLogin {

	final static String XPathCapaContainer = "//div[@class[contains(.,'modal-content')]]";
	final static String XPathIrDeShoppingLink = XPathCapaContainer + "//a[@class[contains(.,'loyalty-irdeshopping')]]";
	
	public static boolean isModalVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathIrDeShoppingLink), driver)
				.wait(maxSeconds).check());
	}
	
	public static void closeModal(WebDriver driver) {
		click(By.xpath(XPathIrDeShoppingLink), driver).exec();
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
