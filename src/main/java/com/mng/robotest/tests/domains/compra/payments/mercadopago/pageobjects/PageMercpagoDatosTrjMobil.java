package com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjMobil extends PageMercpagoDatosTrj {

	private static final String XP_WRAPPER_VISA = "//div[@class='ui-card__wrapper']";
	private static final String XP_WRAPPER_VISA_ACTIVE = XP_WRAPPER_VISA + "//div[@class[contains(.,'ui-card__brand-debvisa')]]";
	private static final String XP_NEXT_BUTTON = "//button[@id[contains(.,'next')]]";
	private static final String XP_BACK_BUTTON = "//button[@id[contains(.,'back')]]";
	private static final String XP_BUTTON_NEXT_PAY = "//button[@id='submit' and @class[contains(.,'submit-arrow')]]";
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_INPUT_CVC).wait(seconds).check();
	}
	
	@Override
	public void sendCaducidadTarj(String fechaVencimiento) {
		int i=0;
		while (!state(CLICKABLE, XP_INPUT_FEC_CADUCIDAD).wait(1).check() && i<3) {
			clickNextButton();
			i+=1;
		}
		getElement(XP_INPUT_FEC_CADUCIDAD).sendKeys(fechaVencimiento);
	}
	
	@Override
	public void sendCvc(String securityCode) {
		int i=0;
		while (!state(CLICKABLE, XP_INPUT_CVC).wait(1).check() && i<3) {
			clickNextButton();
			i+=1;
		}
		getElement(XP_INPUT_CVC).sendKeys(securityCode);		
	}
	
	public boolean isActiveWrapperVisaUntil(int seconds) {
		return state(VISIBLE, XP_WRAPPER_VISA_ACTIVE).wait(seconds).check();
	}
	
	public void clickNextButton() {
		getElement(XP_NEXT_BUTTON).click();
	}
	
	public void clickBackButton() {
		getElement(XP_BACK_BUTTON).click();
	}	
	
	public boolean isClickableButtonNextPayUntil(int seconds) {
		return state(CLICKABLE, XP_BUTTON_NEXT_PAY).wait(seconds).check();
	}

	public void clickButtonForPay() {
		String xpathElem = XP_BUTTON_NEXT_PAY + " | " + XP_BOTON_PAGAR;
		click(xpathElem).exec();
	}
}
