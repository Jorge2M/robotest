package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecSearchDesktopShop extends PageObjTM implements SecSearch {
	
	private final AppEcom app;
	
	private final static String XPathInputBuscadorOutlet = "//input[@class[contains(.,'search-input')]]";
	private final static String XPathInputBuscadorShop	 = "//input[@data-testid='header-search-input']";
	private final static String XPathCloseAspa = "//span[@class[contains(.,'icon-outline-close')]]";

	private SecSearchDesktopShop(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}

	public static SecSearchDesktopShop getNew(AppEcom app, WebDriver driver) {
		return (new SecSearchDesktopShop(app, driver));
	}
	
	private String getXPathInputBuscador() {
		if (app==AppEcom.outlet) {
			return XPathInputBuscadorOutlet;
		}
		return XPathInputBuscadorShop;
	}

	@Override
	public void search(String referencia) {
		String xpath = getXPathInputBuscador();
		state(Visible, By.xpath(xpath)).wait(2).check();
		setTextAndReturn(referencia);
	}

	@Override
	public void close() {
		click(By.xpath(XPathCloseAspa)).exec();
	}

	private void setTextAndReturn(String referencia) {
		String xpath = getXPathInputBuscador();
		WebElement input = getElementVisible(driver, By.xpath(xpath));
		sendKeysWithRetry(5, input, referencia); 
//		try {
//			input.clear();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		input.sendKeys(referencia);
		input.sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
}
