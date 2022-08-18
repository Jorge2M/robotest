package com.mng.robotest.test.pageobject.shop;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PopupFindAddress extends PageBase {

	private static final String XPATH_INPUT_BUSCADOR = "//input[@id='region_name']";
	private static final String XPATH_BUTTON_LUPA = "//button[@class='btn_search']";
	private static final String XPATH_LINK_DIRECC = "//button[@class='link_post']";
	
	public String goToPopupAndWait(String mainWindowHandle, int maxSecondsToWait) throws Exception { 
		String popupBuscador = switchToAnotherWindow(driver, mainWindowHandle);
		try {
			isIFrameUntil(maxSecondsToWait);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Exception going to Find Address Popup. ", e);
		}
		return popupBuscador;
	}

	public boolean isIFrameUntil(int maxSeconds) {
		return state(Present, "//iframe").wait(maxSeconds).check();
	}

	public boolean isBuscadorClickableUntil(int maxSeconds) {
		return state(Clickable, XPATH_INPUT_BUSCADOR).wait(maxSeconds).check();
	}

	public void setDataBuscador(String data) {
		getElement(XPATH_INPUT_BUSCADOR).sendKeys(data);
	}

	public void clickButtonLupa() {
		click(XPATH_BUTTON_LUPA).exec();
	}

	public void clickFirstDirecc() {
		getElement(XPATH_LINK_DIRECC).click();
	}
	
	public void switchToIFrame() {
		driver.switchTo().frame(0);
	}
}
