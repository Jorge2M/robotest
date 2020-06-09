package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecSearchDesktopShop extends PageObjTM implements SecSearch {
	
	private final static String XPathInputBuscador = "//input[@class[contains(.,'search-input')]]";
	private final static String XPathCloseAspa = "//span[@class[contains(.,'icon-outline-close')]]";

	private SecSearchDesktopShop(WebDriver driver) {
		super(driver);
	}

	public static SecSearchDesktopShop getNew(WebDriver driver) {
		return (new SecSearchDesktopShop(driver));
	}

	@Override
	public void search(String referencia) {
		state(Visible, By.xpath(XPathInputBuscador)).wait(2).check();
		setTextAndReturn(referencia);
	}

	@Override
	public void close() {
		click(By.xpath(XPathCloseAspa)).exec();
	}

	private void setTextAndReturn(String referencia) {
		WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
		//sendKeysWithRetry(5, input, referencia); 
		input.clear();
		input.sendKeys(referencia);
		input.sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
}
