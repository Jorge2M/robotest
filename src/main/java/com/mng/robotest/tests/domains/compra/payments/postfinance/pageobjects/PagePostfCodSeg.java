package com.mng.robotest.tests.domains.compra.payments.postfinance.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePostfCodSeg extends PageBase {

	private static final String XP_ACEPTAR_BUTTON = "//form/input[@id='btn_Accept']";
	private static final String XP_INPUT_COD_SEG = "//input[@id='postfinanceCardId']";
	private static final String XP_BUTTON_WEITER = "//button[@class='efinance-button' and text()[contains(.,'Weiter')]]";
	
	/**
	 * @return si se trata de la pasarela de test
	 * Para CI y PRE la pasarela de pago es diferente a la de PRO
	 *   PRE/CI-Postfinance Card-> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
	 *   PRO-PostFinance Card ->   https://epayment.postfinance.ch/pfcd/authentication/arh/cardIdForm
	 *   PRE-Postfinance -> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
	 *   PRO-PostFinance -> https://epayment.postfinance.ch/pfef/authentication/v2
	 */ 
	public boolean isPasarelaTest() {
		return getCurrentUrl().contains("test");
	}
	
	/**
	 * @return si se trata de la pasarela de Postfinance correcta de test (hay de 2 tipos "e-finance" y "Card")
	 */
	public boolean isPasarelaPostfinanceTest(String metodoPago) {
		boolean isPasarela = false;
		if (metodoPago.toUpperCase().contains("E-FINANCE") && driver.getTitle().contains("e-finance")) {
			isPasarela = true;
		}
		if (metodoPago.toUpperCase().contains("CARD") && driver.getTitle().contains("Card")) {
			isPasarela = true;
		}
		return isPasarela;
	}
	
	public boolean isPasarelaPostfinanceProUntil(int seconds) {
		return titleContainsUntil(driver, "E-Payment", seconds);
	}
	
	public boolean isPresentButtonAceptar() {
		return state(PRESENT, XP_ACEPTAR_BUTTON).check();
	}

	public void clickAceptarButton() {
		click(XP_ACEPTAR_BUTTON).exec();
	}
	
	public boolean isPresentInputCodSeg() {
		return state(PRESENT, XP_INPUT_COD_SEG).check();
	}
	
	public boolean isPresentButtonWeiter() {
		return state(PRESENT, XP_BUTTON_WEITER).check();
	}
	
	public void inputCodigoSeguridad(String codigoSeg) {
		getElement(XP_INPUT_COD_SEG).sendKeys(codigoSeg);
	}

}