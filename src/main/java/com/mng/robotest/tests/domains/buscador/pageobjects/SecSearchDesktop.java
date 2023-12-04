package com.mng.robotest.tests.domains.buscador.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecSearchDesktop extends PageBase implements SecSearch {
	
	private static final String XP_INPUT_BUSCADOR = "//input[@data-testid='header.search.input']";
	private static final String XP_CLOSE_ASPA = "//span[@class[contains(.,'icon-outline-close')]]";

	@Override
	public void search(String referencia) {
		state(VISIBLE, XP_INPUT_BUSCADOR).wait(2).check();
		setTextAndReturn(referencia);
	}

	@Override
	public void close() {
		click(XP_CLOSE_ASPA).exec();
	}

	private void setTextAndReturn(String referencia) {
		var input = getElementVisible(driver, By.xpath(XP_INPUT_BUSCADOR));
		sendKeysWithRetry(5, input, referencia); 
		input.sendKeys(Keys.RETURN);
		waitLoadPage();
	}
}
