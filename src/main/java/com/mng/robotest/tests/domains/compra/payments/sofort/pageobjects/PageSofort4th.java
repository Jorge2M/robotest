package com.mng.robotest.tests.domains.compra.payments.sofort.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageSofort4th extends PageBase {
	
	private static final String XP_SUBIMT_BUTTON = "//form//button[@class[contains(.,'primary')]]";
	private static final String XP_INPUT_USER = "//input[@id[contains(.,'LOGINNAMEUSERID')]]";
	private static final String XP_INPUT_PASS = "//input[@id[contains(.,'USERPIN')] and @type='password']";
	private static final String XP_FORM_SEL_CTA = "//form[@action[contains(.,'select_account')]]";
	private static final String XP_INPUT_RADIO_CTAS = "//input[@id[contains(.,'account')] and @type='radio']";
	private static final String XP_INPUT_TAN = "//input[(@id[contains(.,'BackendFormTAN')] or @id[contains(.,'BackendFormTan')]) and @type='text']";
	
	public boolean isPage() {
		return (driver.getTitle().toLowerCase().contains("sofort") && 
				state(VISIBLE, XP_INPUT_USER).check());
	}
	
	public void inputUserPass(String user, String password) {
		getElement(XP_INPUT_USER).sendKeys(user);
		getElement(XP_INPUT_PASS).sendKeys(password);		
	}

	public void clickSubmitButton() {
		click(XP_SUBIMT_BUTTON).exec();
	}

	public boolean isVisibleFormSelCta() {
		return state(VISIBLE, XP_FORM_SEL_CTA).check();
	}
	
	public void selectRadioCta(int posCta) {
		getElements(XP_INPUT_RADIO_CTAS).get(posCta-1).click();
	}
	
	public boolean isVisibleInputTAN() {
		return state(VISIBLE, XP_INPUT_TAN).check();
	}
	
	public void inputTAN(String tan) {
		getElement(XP_INPUT_TAN).sendKeys(tan);	
	}		
}
