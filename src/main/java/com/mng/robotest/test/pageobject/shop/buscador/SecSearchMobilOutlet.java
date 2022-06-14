package com.mng.robotest.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchMobilOutlet extends PageObjTM implements SecSearch {
	
	private static final String XPathInputBuscador = "//form[not(@class)]/input[@class[contains(.,'search-input')]]";
	private static final String XPathCancelarLink = "//div[@class='search-cancel']";
	
	private SecSearchMobilOutlet(WebDriver driver) {
		super(driver);
	}
	
	public static SecSearchMobilOutlet getNew(WebDriver driver) {
		return (new SecSearchMobilOutlet(driver));
	}
	
	@Override
	public void search(String text) {
		WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
		input.clear();
		input.sendKeys(text);
		//sendKeysWithRetry(5, input, text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(By.xpath(XPathCancelarLink)).exec();
	}

	public boolean isBuscadorVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathInputBuscador))
				.wait(maxSeconds).check());
	}
}
