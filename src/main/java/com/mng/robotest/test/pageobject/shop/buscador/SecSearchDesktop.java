package com.mng.robotest.test.pageobject.shop.buscador;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecSearchDesktop extends PageObjTM implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//input[@data-testid='header.search.input']";
	private static final String XPATH_CLOSE_ASPA = "//span[@class[contains(.,'icon-outline-close')]]";

	private SecSearchDesktop(WebDriver driver) {
		super(driver);
	}

	public static SecSearchDesktop getNew(WebDriver driver) {
		return (new SecSearchDesktop(driver));
	}

	@Override
	public void search(String referencia) {
		state(Visible, By.xpath(XPATH_INPUT_BUSCADOR)).wait(2).check();
		setTextAndReturn(referencia);
	}

	@Override
	public void close() {
		click(By.xpath(XPATH_CLOSE_ASPA)).exec();
	}

	private void setTextAndReturn(String referencia) {
		WebElement input = getElementVisible(driver, By.xpath(XPATH_INPUT_BUSCADOR));
		sendKeysWithRetry(5, input, referencia); 
		input.sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
}
