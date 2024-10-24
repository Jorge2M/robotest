package com.mng.robotest.testslegacy.pageobject.shop;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PopupFindAddress extends PageBase {

	private static final String XP_INPUT_BUSCADOR = "//input[@id='region_name']";
	private static final String XP_BUTTON_LUPA = "//button[@class='btn_search']";
	private static final String XP_LINK_DIRECC = "//button[@class='link_post']";
	
	public String goToPopupAndWait(String mainWindowHandle, int seconds) { 
		String popupBuscador = switchToAnotherWindow(driver, mainWindowHandle);
		try {
			isIFrameUntil(seconds);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Exception going to Find Address Popup. ", e);
		}
		return popupBuscador;
	}

	public boolean isIFrameUntil(int seconds) {
		return state(PRESENT, "//iframe").wait(seconds).check();
	}

	public boolean isBuscadorClickableUntil(int seconds) {
		return state(CLICKABLE, XP_INPUT_BUSCADOR).wait(seconds).check();
	}

	public void setDataBuscador(String data) {
		getElement(XP_INPUT_BUSCADOR).sendKeys(data);
	}

	public void clickButtonLupa() {
		click(XP_BUTTON_LUPA).exec();
		waitMillis(1000); //sin este wait no funciona en modo headless
	}

	public void clickFirstDirecc() {
		getElement(XP_LINK_DIRECC).click();
	}
	
	public void switchToIFrame() {
		driver.switchTo().frame(0);
	}
}
