package com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAmexInputTarjeta extends PageBase {

	private static final String XP_PAGE_REDSYS = "//title[text()='Redsys']";
	private static final String XP_INPUT_NUM_TARJ = "//input[@id[contains(.,'inputCard')]]";
	private static final String XP_INPUT_MES_CAD = "//input[@id[contains(.,'cad1')] and @maxlength=2]";
	private static final String XP_INPUT_ANY_CAD = "//input[@id[contains(.,'cad2')] and @maxlength=2]";
	private static final String XP_INPUT_CVC = "//input[@id[contains(.,'codseg')] and @maxlength=4]";
	private static final String XP_PAGAR_BUTTON = "//button[@id[contains(.,'divImgAceptar')]]";
	
	public boolean isPasarelaRedSysUntil(int seconds) {
		return state(PRESENT, XP_PAGE_REDSYS).wait(seconds).check();
	}
	
	public void inputDataTarjeta(String numTarj, String mesCad, String anyCad, String cvc) {
		getElement(XP_INPUT_NUM_TARJ).sendKeys(numTarj);
		getElement(XP_INPUT_MES_CAD).sendKeys(mesCad);
		getElement(XP_INPUT_ANY_CAD).sendKeys(anyCad);
		getElement(XP_INPUT_CVC).sendKeys(cvc);
	}

	public boolean isPresentNumTarj() {
		return state(PRESENT, XP_INPUT_NUM_TARJ).check();
	}
	
	public boolean isPresentInputMesCad() {
		return state(PRESENT, XP_INPUT_MES_CAD).check();
	}
	
	public boolean isPresentInputAnyCad() {
		return state(PRESENT, XP_INPUT_ANY_CAD).check();
	}
	
	public boolean isPresentInputCvc() {
		return state(PRESENT, XP_INPUT_CVC).check();
	}

	public boolean isPresentPagarButton() {
		return state(PRESENT, XP_PAGAR_BUTTON).check();
	}

	public void clickPagarButton() {
		click(XP_PAGAR_BUTTON).exec();
	}
}
