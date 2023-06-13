package com.mng.robotest.domains.compra.pageobjects.envio;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecConfirmDatos extends PageBase {

	private static final String XPATH_DIV_GENERAL_DESKTOP = "//div[@class[contains(.,'fixedConfirm')]]";
	private static final String XPATH_DIV_GENERAL_MOVIL = "//div[@class[contains(.,'dp-confirm-page')]]";
	private static final String XPATH_CONFIRM_DATOS_BUTTON = "//span[@id[contains(.,'confirmButton')]]";
	private static final String XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND = "//input[@placeholder[contains(.,'Post Number ID')]]";
	
	private String getXPathDivGeneral() {
		if (channel==Channel.desktop || channel==Channel.tablet) {
			return XPATH_DIV_GENERAL_DESKTOP;
		}
		return XPATH_DIV_GENERAL_MOVIL;
	}
	
	public boolean isVisibleUntil(int seconds) {
		String xpathDivGeneral = getXPathDivGeneral();
		return state(Visible, xpathDivGeneral).wait(seconds).check();
	}
	
	public boolean isVisibleInputPostNumberIdDeutschland() {
		return state(Visible, XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND).check();
	}
	
	public void sendDataInputPostNumberIdDeutschland(String data) {
		sendKeysWithRetry(data, By.xpath(XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND), 2, driver);
	}

	public void clickConfirmarDatosButtonAndWait(int seconds) {
		click(XPATH_CONFIRM_DATOS_BUTTON).waitLoadPage(seconds).exec();
	}
}
