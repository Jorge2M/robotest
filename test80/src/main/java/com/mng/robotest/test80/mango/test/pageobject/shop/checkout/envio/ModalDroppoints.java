package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDroppoints {
	
	public static SecSelectDPoint secSelectDPoint;
	public static SecConfirmDatos secConfirmDatos;
	
	static String XPathPanelGeneralDesktop = "//span[@id[contains(.,'panelDroppointsGeneral')]]";
	static String XPathPanelGeneralMovil = "//span[@id[contains(.,'panelDroppointsMenuMobile')]]";
	static String XPathMsgCargando = "//div[@class='loading-panel']";
	static String XPathErrorMessage = "//div[@class='errorNotFound']";
	static String XPathCpInputBox = "//div[@class='list__container']//input[@class[contains(.,searchBoxInput)]]";
	
	public static String getXPathPanelGeneral(Channel channel) {
		switch (channel) {
		case desktop:
			return XPathPanelGeneralDesktop;
		default:
		case mobile:
			return XPathPanelGeneralMovil;
		}
	}
	
	public static boolean isVisible(Channel channel, WebDriver driver) {
		return isVisibleUntil(0, channel, driver);
	}
	
	public static boolean isVisibleUntil(int maxSeconds, Channel channel, WebDriver driver) {
		String xpathPanelGeneral = getXPathPanelGeneral(channel);
		return (state(Visible, By.xpath(xpathPanelGeneral), driver).wait(maxSeconds).check());
	}
	
	public static boolean isInvisibleCargandoMsgUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathMsgCargando), driver).wait(maxSeconds).check());
	}
	

	public static boolean isErrorMessageVisibleUntil(WebDriver driver) {
		return (state(Visible, By.xpath(XPathErrorMessage), driver)
				.wait(2).check());
	}

	public static void searchAgainByUserCp(String cp, WebDriver driver) {
		driver.findElement(By.xpath(XPathCpInputBox)).clear();
		driver.findElement(By.xpath(XPathCpInputBox)).sendKeys(cp);
		driver.findElement(By.xpath(XPathCpInputBox)).sendKeys(Keys.ENTER);
	}
}