package com.mng.robotest.domains.compra.payments.sofort.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageSofort4th extends PageBase {
	
	private static final String XPATH_SUBIMT_BUTTON = "//form//button[@class[contains(.,'primary')]]";
	private static final String XPATH_INPUT_USER = "//input[@id[contains(.,'LOGINNAMEUSERID')]]";
	private static final String XPATH_INPUT_PASS = "//input[@id[contains(.,'USERPIN')] and @type='password']";
	private static final String XPATH_FORM_SEL_CTA = "//form[@action[contains(.,'select_account')]]";
	private static final String XPATH_INPUT_RADIO_CTAS = "//input[@id[contains(.,'account')] and @type='radio']";
	private static final String XPATH_INPUT_TAN = "//input[(@id[contains(.,'BackendFormTAN')] or @id[contains(.,'BackendFormTan')]) and @type='text']";
	
	public boolean isPage() {
		if (driver.getTitle().toLowerCase().contains("sofort") && 
			state(Visible, XPATH_INPUT_USER).check()) {
			return true;
		}
		return false;
	}
	
	public void inputUserPass(String user, String password) {
		getElement(XPATH_INPUT_USER).sendKeys(user);
		getElement(XPATH_INPUT_PASS).sendKeys(password);		
	}

	public void clickSubmitButton() {
		click(XPATH_SUBIMT_BUTTON).exec();
	}

	public boolean isVisibleFormSelCta() {
		return state(Visible, XPATH_FORM_SEL_CTA).check();
	}
	
	public void selectRadioCta(int posCta) {
		getElements(XPATH_INPUT_RADIO_CTAS).get(posCta-1).click();
	}
	
	public boolean isVisibleInputTAN() {
		return state(Visible, XPATH_INPUT_TAN).check();
	}
	
	public void inputTAN(String TAN) {
		getElement(XPATH_INPUT_TAN).sendKeys(TAN);	
	}		
}
