package com.mng.robotest.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecConfirmDatos extends PageBase {

	private static final String XPATH_DIV_GENERAL_DESKTOP = "//div[@class[contains(.,'fixedConfirm')]]";
	private static final String XPATH_DIV_GENERAL_MOVIL = "//div[@class[contains(.,'dp-confirm-page')]]";
	private static final String XPATH_CONFIRM_DATOS_BUTTON = "//span[@id[contains(.,'confirmButton')]]";
	private static final String XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND = "//input[@placeholder[contains(.,'Post Number ID')]]";
	
	private String getXPathDivGeneral() {
		switch (channel) {
		case desktop:
		case tablet:
			return XPATH_DIV_GENERAL_DESKTOP;
		case mobile:
		default:
			return XPATH_DIV_GENERAL_MOVIL;
		}
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		String xpathDivGeneral = getXPathDivGeneral();
		return (state(Visible, By.xpath(xpathDivGeneral)).wait(maxSeconds).check());
	}
	
	public boolean isVisibleInputPostNumberIdDeutschland() {
		return (state(Visible, By.xpath(XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND)).check());
	}
	
	public void sendDataInputPostNumberIdDeutschland(String data) throws Exception {
		sendKeysWithRetry(data, By.xpath(XPATH_INPUT_POST_NUMBER_ID_DEUTSCHLAND), 2, driver);
	}

	public void clickConfirmarDatosButtonAndWait(int maxSeconds) {
		click(By.xpath(XPATH_CONFIRM_DATOS_BUTTON)).waitLoadPage(maxSeconds).exec();
	}
}
