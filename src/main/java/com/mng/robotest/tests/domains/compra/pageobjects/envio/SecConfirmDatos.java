package com.mng.robotest.tests.domains.compra.pageobjects.envio;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecConfirmDatos extends PageBase {

	private static final String XP_DIV_GENERAL_DESKTOP = "//div[@class[contains(.,'fixedConfirm')]]";
	private static final String XP_DIV_GENERAL_MOVIL = "//div[@class[contains(.,'dp-confirm-page')]]";
	private static final String XP_CONFIRM_DATOS_BUTTON = "//span[@id[contains(.,'confirmButton')]]";
	private static final String XP_INPUT_POST_NUMBER_ID_DEUTSCHLAND = "//input[@placeholder[contains(.,'Post Number ID')]]";
	
	private String getXPathDivGeneral() {
		if (isDesktop() || isTablet()) {
			return XP_DIV_GENERAL_DESKTOP;
		}
		return XP_DIV_GENERAL_MOVIL;
	}
	
	public boolean isVisibleUntil(int seconds) {
		String xpathDivGeneral = getXPathDivGeneral();
		return state(VISIBLE, xpathDivGeneral).wait(seconds).check();
	}
	
	public boolean isVisibleInputPostNumberIdDeutschland() {
		return state(VISIBLE, XP_INPUT_POST_NUMBER_ID_DEUTSCHLAND).check();
	}
	
	public void sendDataInputPostNumberIdDeutschland(String data) {
		sendKeysWithRetry(data, By.xpath(XP_INPUT_POST_NUMBER_ID_DEUTSCHLAND), 2, driver);
	}

	public void clickConfirmarDatosButtonAndWait(int seconds) {
		click(XP_CONFIRM_DATOS_BUTTON).waitLoadPage(seconds).exec();
	}
}
