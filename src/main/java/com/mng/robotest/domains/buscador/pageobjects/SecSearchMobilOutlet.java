package com.mng.robotest.domains.buscador.pageobjects;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecSearchMobilOutlet extends PageBase implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//form[not(@class)]/input[@class[contains(.,'search-input')]]";
	private static final String XPATH_CANCELAR_LINK = "//div[@class='search-cancel']";
	

	@Override
	public void search(String text) {
		WebElement input = getElementVisible(XPATH_INPUT_BUSCADOR);
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(XPATH_CANCELAR_LINK).exec();
	}

	public boolean isBuscadorVisibleUntil(int maxSeconds) {
		return (state(Visible, XPATH_INPUT_BUSCADOR).wait(maxSeconds).check());
	}
}
