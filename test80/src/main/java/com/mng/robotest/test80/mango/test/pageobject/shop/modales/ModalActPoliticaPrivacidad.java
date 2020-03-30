package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalActPoliticaPrivacidad {

	static String XPathModal = "//div[@class='modal-info-gdpr']";
	static String XPathButtonOk = XPathModal + "//input[@type='submit']";
	
	public static boolean isVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathModal), driver).check());
	}
	
	public static void clickOk(WebDriver driver) {
		click(By.xpath(XPathButtonOk), driver).exec();
	}
	
	public static void clickOkIfVisible(WebDriver driver) {
		if (isVisible(driver)) {
			clickOk(driver);
		}
	}
}
