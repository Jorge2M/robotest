package com.mng.robotest.domains.buscador.pageobjects;

import org.openqa.selenium.Keys;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchDevice extends PageBase implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//*[" + 
			"@data-testid='header.search.input' or " + 
			"@data-testid='header.searchBar.search.button']";
	
	private static final String XPATH_CANCELAR_LINK = "//div[@class[contains(.,'search-cancel')]]";
	
	@Override
	public void search(String text) {
		state(Visible, XPATH_INPUT_BUSCADOR).wait(2).check();
		var input = getElement(XPATH_INPUT_BUSCADOR);
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(XPATH_CANCELAR_LINK).exec();
	}
}
