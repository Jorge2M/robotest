package com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSoyNuevoMobile extends SecSoyNuevo {
	
	private static final String XP_FORM_IDENT = "//form[@id='guestContinueForm']";
	private static final String XP_INPUT_EMAIL = XP_FORM_IDENT + "//input[@data-testid='checkout.guestContinue.emailInput']";
	private static final String XP_BOTON_CONTINUE = "//button[@data-testid='checkout.guestContinue.continueOrderButton.continueOrder']";
	private static final String XP_INPUT_PUBLICIDAD = "//input[@data-testid='checkout.guestContinue.subscribeNewsletterCheckbox']";
	
	private static final String XP_INPUT_CONSENTIMIENTO = "//input[@data-component-id='privacidad']";
	
	@Override
	String getXPathBotonContinue() {
		return XP_BOTON_CONTINUE;
	}
	
	@Override	
	String getXPathFormIdent() {
		return XP_FORM_IDENT;
	}
	
	@Override
	String getXPathInputEmail() {
		return XP_INPUT_EMAIL;
	}
	
	@Override
	boolean isSection(int seconds) {
		return state(VISIBLE, XP_BOTON_CONTINUE).wait(seconds).check();
	}
	
	@Override
	boolean isInputWithText(String text) {
		return getElement(XP_INPUT_EMAIL).getAttribute("value").compareTo(text)==0;
	}

	@Override
	public void setCheckPubliNewsletter(RadioState action) {
		boolean isActivated = isCheckedPubliNewsletter();
		clickRadio(action, isActivated, XP_INPUT_PUBLICIDAD);
	}
	
	@Override
	public void setCheckConsentimiento(RadioState action) {
		boolean isActivated = isCheckedConsentimiento();
		clickRadio(action, isActivated, XP_INPUT_CONSENTIMIENTO);		
	}	
	
	private boolean isCheckedPubliNewsletter() {
		if (!state(PRESENT, XP_INPUT_PUBLICIDAD).check()) {
			return false;
		}
		return getElement(XP_INPUT_PUBLICIDAD).isSelected();
	}
	
	private boolean isCheckedConsentimiento() {
		if (!state(PRESENT, XP_INPUT_CONSENTIMIENTO).check()) {
			return false;
		}
		return getElement(XP_INPUT_CONSENTIMIENTO).isSelected();
	}
	
}
