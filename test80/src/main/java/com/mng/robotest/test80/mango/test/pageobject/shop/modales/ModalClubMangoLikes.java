package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class ModalClubMangoLikes {

	private static final String xpathModal = "//div[@class='superfan-modal']/div[@class='modal-container']";
	private static final String xpathDescubreTusVentajas = xpathModal + "//a";
	private static final String xpathLinkForClose = xpathModal + "//span[@class='modal-close-icon']";
	
	public static boolean isVisible(WebDriver driver) {
		return WebdrvWrapp.isElementVisible(driver, By.xpath(xpathModal));
	}
	
	public static void clickDescubreTusVentajas(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpathDescubreTusVentajas));
	}
	
	public static void closeModalIfVisible(WebDriver driver) throws Exception {
		if (isVisible(driver)) {
			closeModal(driver);
		}
	}
	
	private static void closeModal(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpathLinkForClose));
	}
}
