package com.mng.robotest.tests.domains.compra.payments.d3d.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageD3DLoginNew extends PageBase implements PageD3DLogin {

	private static final String XP_PAGE = "//title[text()[contains(.,'Payment Authentication')]]";
	private static final String XP_IFRAME = "//iframe[@name='threeDSIframe']";
	private static final String XP_INPUT_PASSWORD = "//input[@type='password']";
	private static final String XP_BUTTON_SUBMIT = "//button[@id='buttonSubmit']";
	
	private void goToIframe() {
		state(VISIBLE, XP_IFRAME).wait(2).check();
		driver.switchTo().frame(getElement(XP_IFRAME));
	}
	
	private void leaveIframe() {
		driver.switchTo().defaultContent();
	}	
	
	@Override
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_PAGE).wait(seconds).check();
	}
	
	@Override
	public boolean isImporteVisible(String importeTotal) {
		goToIframe();
		var codPais = dataTest.getCodigoPais();
		var resultado = ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver);
		leaveIframe();
		return resultado;
	}
	
	@Override
	public void inputCredentials(String user, String password) {
		goToIframe();
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
		leaveIframe();
	}
		   
	@Override
	public void clickButtonSubmit() {
		goToIframe();
		click(XP_BUTTON_SUBMIT).type(JAVASCRIPT).exec();
		leaveIframe();
	}
	
}
