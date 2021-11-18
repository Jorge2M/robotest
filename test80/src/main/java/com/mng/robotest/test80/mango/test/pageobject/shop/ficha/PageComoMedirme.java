package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageComoMedirme {

	private final WebDriver driver;
	
	private final static String XPathBloqueGuiaTallas = "//div[@class[contains(.,'guiaTallas')]]";
	//private final static String XPathBloqueTablaEquivalencias = "//div[@class[contains(.,'tablaEquivalencias')]]";
	
	private PageComoMedirme(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageComoMedirme getNew(WebDriver driver) {
		return new PageComoMedirme(driver);
	}
	
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
		return (state(Visible, By.xpath(XPathBloqueGuiaTallas), driver).check());
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
