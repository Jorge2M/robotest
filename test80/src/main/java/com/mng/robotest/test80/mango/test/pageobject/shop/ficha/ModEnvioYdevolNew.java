package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModEnvioYdevolNew {

	static String XPathWrapper = "//div[@class='handling-modal-wrapper']";
	static String XPathAspaForClose = XPathWrapper + "//span[@class[contains(.,'modal-close')]]";

	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathWrapper), driver)
				.wait(maxSeconds).check());
	}

	public static void clickAspaForClose(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathAspaForClose));
	}
}
