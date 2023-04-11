package com.mng.robotest.domains.compra.pageobjects.secsoynuevo;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

//TODO eliminarla cuando se haya activado en PRO el nuevo flujo de login -> checkout (11-abril-2023)
public class SecSoyNuevoDeviceOld extends SecSoyNuevo {
	
	private static final String XPATH_FORM_IDENT = "//div[@class='register' or @id='registerCheckOut']//form"; //desktop y mobil
	private static final String XPATH_INPUT_EMAIL = XPATH_FORM_IDENT + "//input[@id[contains(.,'expMail')]]";
	private static final String XPATH_BOTON_CONTINUE = "//div[@id='registerCheckOut']//div[@class='submit']/input";
	private static final String XPATH_INPUT_PUBLICIDAD = "//input[@id[contains(.,'publicidadActiva')]]";
	private static final String XPATH_RADIO_PUBLICIDAD = "//div[@class[contains(.,'subscribe__checkbox')]]";
	private static final String XPATH_INPUT_CONSENTIMIENTO = "//input[@data-component-id='privacidad']";
	
	@Override
	String getXPathBotonContinue() {
		return XPATH_BOTON_CONTINUE;
	}
	
	@Override	
	String getXPathFormIdent() {
		return XPATH_FORM_IDENT;
	}
	
	@Override
	String getXPathInputEmail() {
		return XPATH_INPUT_EMAIL;
	}
	
	@Override
	boolean isSection() {
		return state(Visible, XPATH_BOTON_CONTINUE).check();
	}
	
	@Override
	boolean isInputWithText(String text) {
		return getElement(XPATH_INPUT_EMAIL).getAttribute("value").compareTo(text)==0;
	}
	
	@Override	
	public void setCheckPubliNewsletter(RadioState action) {
		boolean isActivated = isCheckedPubliNewsletter();
		clickRadio(action, isActivated, XPATH_RADIO_PUBLICIDAD);
	}
	
	@Override	
	public void setCheckConsentimiento(RadioState action) {
		boolean isActivated = isCheckedConsentimiento();
		clickRadio(action, isActivated, XPATH_INPUT_CONSENTIMIENTO);		
	}
	
	private boolean isCheckedPubliNewsletter() {
		if (state(Invisible, XPATH_INPUT_PUBLICIDAD).check()) {
			return false;
		}
		String checked = getElement(XPATH_INPUT_PUBLICIDAD).getAttribute("checked");
		return checked!=null;
	}
	
	private boolean isCheckedConsentimiento() {
		if (state(Invisible, XPATH_INPUT_CONSENTIMIENTO).check()) {
			return false;
		}
		String checked = getElement(XPATH_INPUT_CONSENTIMIENTO).getAttribute("checked");
		return checked!=null;
	}	

}
