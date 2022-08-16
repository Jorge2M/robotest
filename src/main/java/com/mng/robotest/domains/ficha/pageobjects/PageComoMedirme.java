package com.mng.robotest.domains.ficha.pageobjects;



import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageComoMedirme extends PageBase {

	private static final String XPATH_BLOQUE_GUIA_TALLAS = "//div[@class[contains(.,'guiaTallas')]]";
	
	public boolean goToPageInNewTabCheckAndClose() throws Exception {
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
	
	private boolean goToPageInNewTab(String windowFatherHandle) throws Exception {
		String newPageHandle = switchToAnotherWindow(driver, windowFatherHandle);
		if (newPageHandle.compareTo(windowFatherHandle)==0) {
			return false;
		}
		waitForPageLoaded(driver, 10);
		return true;
	}
	
	private void close(String windowFatherHandle) {
		driver.close();
		driver.switchTo().window(windowFatherHandle);
	}
}
