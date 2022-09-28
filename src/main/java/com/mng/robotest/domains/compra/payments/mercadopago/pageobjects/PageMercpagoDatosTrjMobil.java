package com.mng.robotest.domains.compra.payments.mercadopago.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjMobil extends PageMercpagoDatosTrj {

	private static String XPATH_WRAPPER_VISA = "//div[@class='ui-card__wrapper']";
	private static String XPATH_WRAPPER_VISA_ACTIVE = XPATH_WRAPPER_VISA + "//div[@class[contains(.,'ui-card__brand-debvisa')]]";
	private static String XPATH_NEXT_BUTTON = "//button[@id[contains(.,'next')]]";
	private static String XPATH_BACK_BUTTON = "//button[@id[contains(.,'back')]]";
	private static String XPATH_BUTTON_NEXT_PAY = "//button[@id='submit' and @class[contains(.,'submit-arrow')]]";
	
	@Override
	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_INPUT_CVC).wait(seconds).check();
	}
	
	@Override
	public void sendCaducidadTarj(String fechaVencimiento) {
		int i=0;
		while (!state(Clickable, XPATH_INPUT_FEC_CADUCIDAD).wait(1).check() && i<3) {
			clickNextButton();
			i+=1;
		}
		getElement(XPATH_INPUT_FEC_CADUCIDAD).sendKeys(fechaVencimiento);
	}
	
	@Override
	public void sendCvc(String securityCode) {
		int i=0;
		while (!state(Clickable, XPATH_INPUT_CVC).wait(1).check() && i<3) {
			clickNextButton();
			i+=1;
		}
		getElement(XPATH_INPUT_CVC).sendKeys(securityCode);		
	}
	
	public boolean isActiveWrapperVisaUntil(int seconds) {
		return state(Visible, XPATH_WRAPPER_VISA_ACTIVE).wait(seconds).check();
	}
	
	public void clickNextButton() {
		getElement(XPATH_NEXT_BUTTON).click();
	}
	
	public void clickBackButton() {
		getElement(XPATH_BACK_BUTTON).click();
	}	
	
	public boolean isClickableButtonNextPayUntil(int seconds) {
		return state(Clickable, XPATH_BUTTON_NEXT_PAY).wait(seconds).check();
	}

	public void clickButtonForPay() {
		String xpathElem = XPATH_BUTTON_NEXT_PAY + " | " + XPATH_BOTON_PAGAR;
		click(xpathElem).exec();
	}
}
