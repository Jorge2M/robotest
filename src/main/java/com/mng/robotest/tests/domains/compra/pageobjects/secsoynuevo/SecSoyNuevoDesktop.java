package com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSoyNuevoDesktop extends SecSoyNuevo {
	
	private static final String XP_FORM_IDENT = "//div[@class='register' or @id='registerCheckOut']//form"; //desktop y mobil
	private static final String XP_INPUT_EMAIL = XP_FORM_IDENT + "//input[@id[contains(.,'expMail')]]";
	private static final String XP_BOTON_CONTINUE = "//div[@class='register']//div[@class='submit']/input";
	private static final String XP_INPUT_PUBLICIDAD = "//input[@id[contains(.,':publicidad')]]";
	private static final String XP_RADIO_PUBLICIDAD = XP_INPUT_PUBLICIDAD;
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
		clickRadio(action, isActivated, XP_RADIO_PUBLICIDAD);
	}
	
	@Override
	public void setCheckConsentimiento(RadioState action) {
		boolean isActivated = isCheckedConsentimiento();
		clickRadio(action, isActivated, XP_INPUT_CONSENTIMIENTO);		
	}
	
	private boolean isCheckedPubliNewsletter() {
		if (state(INVISIBLE, XP_INPUT_PUBLICIDAD).check()) {
			return false;
		}
		String checked = getElement(XP_INPUT_PUBLICIDAD).getAttribute("checked");
		return checked!=null;
	}
	
	private boolean isCheckedConsentimiento() {
		if (state(INVISIBLE, XP_INPUT_CONSENTIMIENTO).check()) {
			return false;
		}
		String checked = getElement(XP_INPUT_CONSENTIMIENTO).getAttribute("checked");
		return checked!=null;
	}	

}
