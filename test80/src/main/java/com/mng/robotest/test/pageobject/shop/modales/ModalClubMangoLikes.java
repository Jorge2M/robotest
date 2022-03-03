package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalClubMangoLikes {

	private static final String xpathModal = "//div[@class='superfan-modal']/div[@class='modal-container']";
	private static final String xpathDescubreTusVentajas = xpathModal + "//a";
	private static final String xpathLinkForClose = xpathModal + "//span[@class='modal-close-icon']";
	
	public static boolean isVisible(WebDriver driver) {
		return (state(Visible, By.xpath(xpathModal), driver).check());
	}
	
	public static void clickDescubreTusVentajas(WebDriver driver) {
		click(By.xpath(xpathDescubreTusVentajas), driver).exec();
	}
	
	public static void closeModalIfVisible(WebDriver driver) {
		if (isVisible(driver)) {
			closeModal(driver);
		}
	}
	
	private static void closeModal(WebDriver driver) {
		click(By.xpath(xpathLinkForClose), driver).exec();
	}
}
