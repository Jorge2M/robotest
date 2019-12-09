package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecSearchDesktopShop extends WebdrvWrapp implements SecSearch {
	
	private final WebDriver driver;
	
	private final static String XPathInputBuscador = "//input[@class[contains(.,'search-input')]]";
	private final static String XPathCloseAspa = "//span[@class[contains(.,'icon-outline-close')]]";

	private SecSearchDesktopShop(WebDriver driver) {
		this.driver = driver;
	}

	public static SecSearchDesktopShop getNew(WebDriver driver) {
		return (new SecSearchDesktopShop(driver));
	}

	@Override
	public void search(String referencia) throws Exception {
		//new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
		isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), 2);
		setTextAndReturn(referencia);
	}

	@Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCloseAspa));
	}

	private void setTextAndReturn(String referencia) throws Exception {
		WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
		//sendKeysWithRetry(5, input, referencia); 
		input.clear();
		input.sendKeys(referencia);
		input.sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
}
