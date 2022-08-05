package com.mng.robotest.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecSearchMobilOutlet extends PageObjTM implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//form[not(@class)]/input[@class[contains(.,'search-input')]]";
	private static final String XPATH_CANCELAR_LINK = "//div[@class='search-cancel']";
	

	@Override
	public void search(String text) {
		WebElement input = getElementVisible(driver, By.xpath(XPATH_INPUT_BUSCADOR));
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(By.xpath(XPATH_CANCELAR_LINK)).exec();
	}

	public boolean isBuscadorVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_INPUT_BUSCADOR))
				.wait(maxSeconds).check());
	}
}
