package com.mng.robotest.tests.domains.ficha.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageComoMedirme extends PageBase {

	private static final String XPATH_BLOQUE_GUIA_TALLAS = "//div[@class[contains(.,'guiaTallas')]]";
	
	public boolean goToPageInNewTabCheckAndClose() {
		String windowFatherHandle = driver.getWindowHandle();
		if (!goToPageInNewTab(windowFatherHandle)) {
			return false;
		}
		if (!isPage()) {
			return false;
		}
		close(windowFatherHandle);
		return true;
	}
	
	private boolean isPage() {
		return state(Visible, XPATH_BLOQUE_GUIA_TALLAS).check();
	}
	
	private void close(String windowFatherHandle) {
		driver.close();
		driver.switchTo().window(windowFatherHandle);
	}
}
