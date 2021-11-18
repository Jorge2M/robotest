package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecConfirmDatos {

	static String XPathDivGeneralDesktop = "//div[@class[contains(.,'fixedConfirm')]]";
	static String XPathDivGeneralMovil = "//div[@class[contains(.,'dp-confirm-page')]]";
	static String XPathConfirmDatosButton = "//span[@id[contains(.,'confirmButton')]]";
	static String XPathInputPostNumberIdDeutschland = "//input[@placeholder[contains(.,'Post Number ID')]]";
	
	public static String getXPathDivGeneral(Channel channel) {
		switch (channel) {
		case desktop:
		case tablet:
			return XPathDivGeneralDesktop;
		case mobile:
		default:
			return XPathDivGeneralMovil;
		}
	}
	
	public static boolean isVisibleUntil(int maxSeconds, Channel channel, WebDriver driver) {
		String xpathDivGeneral = getXPathDivGeneral(channel);
		return (state(Visible, By.xpath(xpathDivGeneral), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleInputPostNumberIdDeutschland(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputPostNumberIdDeutschland), driver).check());
	}
	
	public static void sendDataInputPostNumberIdDeutschland(String data, WebDriver driver) throws Exception {
		sendKeysWithRetry(data, By.xpath(XPathInputPostNumberIdDeutschland), 2, driver);
	}

	public static void clickConfirmarDatosButtonAndWait(int maxSeconds, WebDriver driver) {
		click(By.xpath(XPathConfirmDatosButton), driver).waitLoadPage(maxSeconds).exec();
	}
}
