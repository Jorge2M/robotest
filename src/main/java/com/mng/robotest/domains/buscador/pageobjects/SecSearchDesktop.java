package com.mng.robotest.domains.buscador.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;


public class SecSearchDesktop extends PageBase implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//input[@data-testid='header.search.input']";
	private static final String XPATH_CLOSE_ASPA = "//span[@class[contains(.,'icon-outline-close')]]";

	@Override
	public void search(String referencia) {
		state(Visible, XPATH_INPUT_BUSCADOR).wait(2).check();
		setTextAndReturn(referencia);
	}

	@Override
	public void close() {
		click(XPATH_CLOSE_ASPA).exec();
	}

	private void setTextAndReturn(String referencia) {
		WebElement input = getElementVisible(driver, By.xpath(XPATH_INPUT_BUSCADOR));
		sendKeysWithRetry(5, input, referencia); 
		input.sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
}
