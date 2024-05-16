package com.mng.robotest.tests.domains.compra.payments.visa.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

public class PageD3DLoginOld extends PageBase implements PageD3DLogin {
	
	private static final String XP_INPUT_USER = "//input[@id='username']";
	private static final String XP_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XP_BUTTON_SUBMIT = "//input[@class[contains(.,'button')] and @type='submit']";
	
	@Override
	public boolean isPage(int seconds) {
		return (titleContainsUntil(driver, "3D Authentication", seconds));
	}
	
	@Override
	public void inputCredentials(String user, String password) {
		getElement(XP_INPUT_USER).sendKeys(user);
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}

	@Override
	public boolean isImporteVisible(String importeTotal) {
		var codPais = dataTest.getCodigoPais();
		return ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver);
	}	
		   
	@Override
	public void clickButtonSubmit() {
		click(XP_BUTTON_SUBMIT).type(JAVASCRIPT).exec();
	}
}