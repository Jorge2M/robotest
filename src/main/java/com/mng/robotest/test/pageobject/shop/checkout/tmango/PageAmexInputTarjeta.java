package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAmexInputTarjeta extends PageBase {

	private static final String XPATH_PAGE_REDSYS = "//title[text()='Redsys']";
	private static final String XPATH_INPUT_NUM_TARJ = "//input[@id[contains(.,'inputCard')]]";
	private static final String XPATH_INPUT_MES_CAD = "//input[@id[contains(.,'cad1')] and @maxlength=2]";
	private static final String XPATH_INPUT_ANY_CAD = "//input[@id[contains(.,'cad2')] and @maxlength=2]";
	private static final String XPATH_INPUT_CVC = "//input[@id[contains(.,'codseg')] and @maxlength=4]";
	private static final String XPATH_PAGAR_BUTTON = "//button[@id[contains(.,'divImgAceptar')]]";
	
	public boolean isPasarelaRedSysUntil(int maxSeconds) {
		return state(Present, XPATH_PAGE_REDSYS).wait(maxSeconds).check();
	}
	
	public void inputDataTarjeta(String numTarj, String mesCad, String anyCad, String Cvc) {
		getElement(XPATH_INPUT_NUM_TARJ).sendKeys(numTarj);
		getElement(XPATH_INPUT_MES_CAD).sendKeys(mesCad);
		getElement(XPATH_INPUT_ANY_CAD).sendKeys(anyCad);
		getElement(XPATH_INPUT_CVC).sendKeys(Cvc);
	}

	public boolean isPresentNumTarj() {
		return state(Present, XPATH_INPUT_NUM_TARJ).check();
	}
	
	public boolean isPresentInputMesCad() {
		return state(Present, XPATH_INPUT_MES_CAD).check();
	}
	
	public boolean isPresentInputAnyCad() {
		return state(Present, XPATH_INPUT_ANY_CAD).check();
	}
	
	public boolean isPresentInputCvc() {
		return state(Present, XPATH_INPUT_CVC).check();
	}

	public boolean isPresentPagarButton() {
		return state(Present, XPATH_PAGAR_BUTTON).check();
	}

	public void clickPagarButton() {
		click(XPATH_PAGAR_BUTTON).exec();
	}
}
