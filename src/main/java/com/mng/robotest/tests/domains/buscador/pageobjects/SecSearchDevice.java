package com.mng.robotest.tests.domains.buscador.pageobjects;

import org.openqa.selenium.Keys;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchDevice extends PageBase implements SecSearch {
	
	private static final String XP_INPUT_BUSCADOR = "//*[@data-testid='header.search.input']"; 
	private static final String XP_CANCELAR_LINK = "//div[@class[contains(.,'search-cancel')]]";
	
	@Override
	public void search(String text) {
		state(Visible, XP_INPUT_BUSCADOR).wait(2).check();
		var input = getElement(XP_INPUT_BUSCADOR);
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(XP_CANCELAR_LINK).exec();
	}
}
